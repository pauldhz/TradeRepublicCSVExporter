package org.denhez;

import org.denhez.pdf.reader.PdfReader;
import org.denhez.pdf.reader.tool.pruner.Pruner;
import org.denhez.pdf.reader.tool.pruner.PrunerCollector;
import org.denhez.pdf.reader.tool.pruner.PrunerEnum;
import org.denhez.pdf.reader.tool.pruner.TextPruner;
import org.denhez.pdf.reader.PdfReaderImpl;
import org.denhez.pdf.util.Pair;
import org.denhez.pdf.writer.CSVMaker;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;


public class Main {
    public static void main(String[] args) throws IOException {
        PdfReader pdfReader = new PdfReaderImpl();
        Pruner<Deque<String>, String> textPruner = new TextPruner();

        var pdfText = pdfReader.read("/Users/pauldenhez/Downloads/Relevé de compte.pdf");

        Deque<Pair<PrunerEnum, String>> commands = new ArrayDeque<>();
        Deque<String> pdfAsDeque = new ArrayDeque<>(Arrays.asList(pdfText.split("\n")));
        PrunerCollector prunerCollector = new PrunerCollector(textPruner, pdfAsDeque);

        var transactions = prunerCollector.pruneAndCollect();
        CSVMaker csvMaker = new CSVMaker();
        csvMaker.makeCSV(transactions, "/Users/pauldenhez/Downloads/relevédecompte.csv", ';');
    }
}