package org.denhez.pdf.tool.exporter;

import org.denhez.pdf.domain.report.Transaction;
import org.denhez.pdf.domain.report.TransactionInfo;
import org.denhez.pdf.domain.statement.Avoir;
import org.denhez.pdf.domain.statement.Bonus;
import org.denhez.pdf.domain.statement.ExecutionOrdre;
import org.denhez.pdf.domain.statement.Interets;
import org.denhez.pdf.domain.statement.Virement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Classe responsable de l'export des transactions au format CSV
 */
public class CsvExporter {

    private static final String CSV_SEPARATOR = ";";
    private static final String CSV_HEADER = "Date;Type;Description;Montant;Sens";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Exporte une liste de transactions au format CSV
     * @param transactions la liste des transactions à exporter
     * @param outputPath le chemin du fichier CSV de sortie
     * @throws IOException en cas d'erreur d'écriture
     */
    public void export(List<Transaction> transactions, String outputPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Écrire l'en-tête
            writer.write(CSV_HEADER);
            writer.newLine();

            // Écrire chaque transaction
            for (Transaction transaction : transactions) {
                String csvLine = transactionToCsvLine(transaction);
                writer.write(csvLine);
                writer.newLine();
            }
        }
    }

    /**
     * Convertit une transaction en ligne CSV
     */
    private String transactionToCsvLine(Transaction transaction) {
        TransactionInfo info = transaction.getTransactionInfo();

        if (info == null) {
            return "";
        }

        // Date
        String date = info.operationDate() != null ? DATE_FORMAT.format(info.operationDate()) : "";

        // Type de transaction
        String type = getTransactionType(transaction);

        // Description
        String description = escapeCSV(info.description() != null ? info.description() : "");

        // Montant
        String montant = info.amount() != null ? info.amount().toString().replace(".", ",") : "0";

        // Sens (DEBIT ou CREDIT)
        String sens = info.transactionType() != null ? info.transactionType().name() : "";

        return String.join(CSV_SEPARATOR, date, type, description, montant, sens);
    }

    /**
     * Détermine le type de transaction
     */
    private String getTransactionType(Transaction transaction) {
        if (transaction instanceof Avoir) {
            return "Avoir";
        } else if (transaction instanceof Bonus) {
            return "Bonus";
        } else if (transaction instanceof Interets) {
            return "Intérêts";
        } else if (transaction instanceof ExecutionOrdre) {
            return "Exécution d'ordre";
        } else if (transaction instanceof Virement) {
            return "Virement";
        }
        return "Inconnu";
    }

    /**
     * Échappe les caractères spéciaux pour le CSV (guillemets, points-virgules)
     */
    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }

        // Si la valeur contient des guillemets, points-virgules ou retours à la ligne
        if (value.contains("\"") || value.contains(CSV_SEPARATOR) || value.contains("\n")) {
            // Doubler les guillemets et entourer de guillemets
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }
}

