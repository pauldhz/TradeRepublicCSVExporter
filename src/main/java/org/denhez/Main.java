package org.denhez;

import org.denhez.pdf.domain.Transaction;
import org.denhez.pdf.tool.reader.PdfReader;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.denhez.pdf.domain.Transaction.*;


public class Main {
    public static void main(String[] args) throws IOException {

        final String output;
        if(args.length > 0) {
            output = args[0];
        }
        else {
            output = "export.csv";
        }
        PdfReader pdfReader = new PdfReader();

        var path = definePDFInput(new File("."));
        var pdfText = pdfReader.read(path);

        // Normaliser les espaces insécables en espaces normaux
        pdfText = pdfText.replace("\u00A0", " ");

        Iterator<String> pdfIterator = Arrays.asList(pdfText.split("\n")).iterator();
        final List<Transaction> transactions = new ArrayList<>();

        // Stocker les 2 dernières lignes pour récupérer la date
        String lineMinus2 = null;
        String lineMinus1 = null;

        while (pdfIterator.hasNext()) {
            String row = pdfIterator.next();

            // Essayer de parser la date à partir des 2 lignes précédentes
            Date operationDate = parseDate(lineMinus2, lineMinus1);

            parseAvoir(row, operationDate)
                .or(() -> parseBonus(row, operationDate))
                .or(() -> parseInterets(row, operationDate))
                .or(() -> parseExecutionOrdre(row, pdfIterator::next, operationDate))
                .or(() -> parseVirement(row, pdfIterator::next, operationDate))
                .ifPresent(transactions::add);

            // Décaler les lignes pour la prochaine itération
            lineMinus2 = lineMinus1;
            lineMinus1 = row;
        }

        System.out.println(transactions.size());

    }

    /**
     * Parse la date à partir de 2 lignes : "01 oct." et "2025"
     * @param dayMonth la ligne avec le jour et le mois (ex: "01 oct.")
     * @param year la ligne avec l'année (ex: "2025")
     * @return la Date parsée, ou null si le parsing échoue
     */
    private static Date parseDate(String dayMonth, String year) {
        if (dayMonth == null || year == null) {
            return null;
        }

        try {
            // Nettoyer et normaliser
            dayMonth = dayMonth.trim();
            year = year.trim();

            // Vérifier que year est bien une année (4 chiffres)
            if (!year.matches("\\d{4}")) {
                return null;
            }

            // Construire la chaîne de date complète
            String dateStr = dayMonth + " " + year;

            // Parser avec le format français (ex: "01 oct. 2025")
            // Le point après le mois dans le texte est géré par setLenient(true)
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.FRENCH);
            sdf.setLenient(true); // Plus tolérant pour gérer les variations (point après mois, etc.)
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            // En cas d'échec, afficher le détail pour déboguer
            System.err.println("Erreur de parsing pour la date: '" + dayMonth + "' + '" + year + "' - " + e.getMessage());
            return null;
        }
    }

    private static String definePDFInput(File directory) {
        List<File> pdfFiles = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".pdf")) {
                    pdfFiles.add(file);
                }
            }
        }

        if (pdfFiles.isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier PDF trouvé dans le répertoire courant.");
        }
        if(pdfFiles.size() == 1) {
            return pdfFiles.getFirst().getAbsolutePath();
        }
        else {
            System.out.println("Plusieurs fichiers PDF trouvés :");
            for (int i = 0; i < pdfFiles.size(); i++) {
                System.out.println((i + 1) + ". " + pdfFiles.get(i).getName());
            }
            Scanner scanner = new Scanner(System.in);
            int choix = -1;
            while (choix < 1 || choix > pdfFiles.size()) {
                System.out.print("Sélectionner le numéro du PDF : ");
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                } else {
                    System.out.println("Veuillez entrer un nombre entre 1 et 9");
                    scanner.next();  // Nettoyer l'entrée incorrecte
                }
            }
            return pdfFiles.get(choix - 1).getAbsolutePath();
        }
    }
}