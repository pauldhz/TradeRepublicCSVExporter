package org.denhez.pdf.tool.reader;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;

public class PdfReader {
    public String read(String path) throws IOException {
        try (PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(path)))
        {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(document);
        }
    }
}
