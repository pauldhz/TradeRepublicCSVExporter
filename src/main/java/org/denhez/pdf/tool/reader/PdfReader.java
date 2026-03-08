package org.denhez.pdf.tool.reader;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PdfReader {

    private String pdf = "";

    public Extractor read(String path) throws IOException {
        try (PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(path)))
        {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdf = pdfTextStripper.getText(document);
            return () -> pdf;
        }
    }

    public interface Extractor {
        String extract();

        default String normalize () {
            String pdf = extract();
            pdf = pdf.replace("\u00A0", " ");
            pdf = pdf.replace("null", "");
            return pdf;
        }
    }

    public static String definePDFInput(File directory) {
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
