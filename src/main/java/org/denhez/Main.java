package org.denhez;

import org.denhez.pdf.domain.Transaction;
import org.denhez.pdf.domain.TransactionInfo;
import org.denhez.pdf.domain.TransactionType;
import org.denhez.pdf.domain.vo.PositiveAmount;
import org.denhez.pdf.tool.reader.PdfReader;

import java.io.File;
import java.io.IOException;
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
        while (pdfIterator.hasNext()) {
            String row = pdfIterator.next();
            parseAvoir(row)
                .or(() -> parseBonus(row))
                .or(() -> parseInterets(row))
                .map(transactions::add);
        }

        System.out.println(transactions.size());

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