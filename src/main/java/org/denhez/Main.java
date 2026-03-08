package org.denhez;

import org.denhez.pdf.domain.report.Transaction;
import org.denhez.pdf.domain.report.categorization.Categorization;
import org.denhez.pdf.domain.report.categorization.RetrieveCategorization;
import org.denhez.pdf.domain.report.categorization.prediction.RetrievePredictedCategorization;
import org.denhez.pdf.domain.report.configuration.conversion.Converter;
import org.denhez.pdf.domain.report.configuration.conversion.DateConverter;
import org.denhez.pdf.tool.exporter.CsvExporter;
import org.denhez.pdf.tool.exporter.Exporter;
import org.denhez.pdf.tool.reader.PdfReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.denhez.ConfigLoader.PREDICT_URL;
import static org.denhez.pdf.domain.report.Transaction.*;
import static org.denhez.pdf.tool.reader.PdfReader.definePDFInput;


public class Main {
    public static void main(String[] args) throws IOException {

        ConfigLoader configLoader = new ConfigLoader();
        RetrieveCategorization retrieveCategorization =
                new RetrievePredictedCategorization(configLoader.getConfig().get(PREDICT_URL));

        final String output;
        if(args.length > 0) {
            output = args[0];
        }
        else {
            output = "export.csv";
        }

        var pdfText = new PdfReader().read(definePDFInput(new File("."))).normalize();

        Iterator<String> pdfIterator = Arrays.asList(pdfText.split("\n")).iterator();
        final List<Transaction> transactions = new ArrayList<>();

        // Stocker les 2 dernières lignes pour récupérer la date
        String lineMinus2 = null;
        String lineMinus1 = null;

        while (pdfIterator.hasNext()) {
            String row = pdfIterator.next();

            Converter<String, String, Date> converter = new DateConverter();
            Date operationDate = converter.convert(lineMinus2, lineMinus1);

            parseAvoir(row, operationDate)
                .or(() -> parseBonus(row, operationDate))
                .or(() -> parseInterets(row, operationDate))
                .or(() -> parseExecutionOrdre(row, pdfIterator::next, operationDate))
                .or(() -> parseVirement(row, pdfIterator::next, operationDate))
                .map(transaction -> {
                    Categorization categorization = retrieveCategorization.getCategory(transaction.getTransactionInfo());
                    if(categorization.isConfident()) {
                        transaction.setTransactionInfo(transaction.getTransactionInfo().withCategorization(categorization));
                    }
                    return transaction;
                })
                .ifPresent(transactions::add);

            // Décaler les lignes pour la prochaine itération
            lineMinus2 = lineMinus1;
            lineMinus1 = row;
        }

        System.out.println("Nombre de transactions parsées : " + transactions.size());

        if (!transactions.isEmpty()) {
            Exporter exporter = new CsvExporter();
            try {
                exporter.export(transactions, output);
                System.out.println("Export CSV réussi : " + output);
            } catch (Exception e) {
                System.err.println("Erreur lors de l'export CSV : " + e.getMessage());
            }
        } else {
            System.out.println("Aucune transaction à exporter.");
        }

    }
}