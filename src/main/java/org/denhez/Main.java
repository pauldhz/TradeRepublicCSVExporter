package org.denhez;

import org.denhez.pdf.tool.explorer.Explorer;
import org.denhez.pdf.tool.collector.TransactionCollector;
import org.denhez.pdf.tool.explorer.TextExplorer;
import org.denhez.pdf.tool.reader.PdfReader;
import org.denhez.pdf.writer.CSVMaker;

import java.io.File;
import java.io.IOException;
import java.util.*;


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
        Explorer<String> explorer = new TextExplorer();

        var path = definePDFInput(new File("."));
        var pdfText = pdfReader.read(path);

        Deque<String> pdfAsDeque = new ArrayDeque<>(Arrays.asList(pdfText.split("\n")));
        TransactionCollector transactionCollector = new TransactionCollector(explorer, pdfAsDeque);

        var transactions = transactionCollector.exploreAndCollect();
        CSVMaker csvMaker = new CSVMaker();
        csvMaker.makeCSV(transactions, new File("").getAbsolutePath()+"/"+output, ';');
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