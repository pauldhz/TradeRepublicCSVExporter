package org.denhez;

import org.denhez.pdf.tool.explorer.Explorer;
import org.denhez.pdf.tool.collector.TransactionCollector;
import org.denhez.pdf.tool.explorer.TextExplorer;
import org.denhez.pdf.tool.reader.PdfReader;
import org.denhez.pdf.writer.CSVMaker;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;


public class Main {
    public static void main(String[] args) throws IOException {
        PdfReader pdfReader = new PdfReader();
        Explorer<String> explorer = new TextExplorer();

        var pdfText = pdfReader.read("/Users/pauldenhez/Downloads/Relevé de compte.pdf");

        Deque<String> pdfAsDeque = new ArrayDeque<>(Arrays.asList(pdfText.split("\n")));
        TransactionCollector transactionCollector = new TransactionCollector(explorer, pdfAsDeque);

        var transactions = transactionCollector.exploreAndCollect();
        CSVMaker csvMaker = new CSVMaker();
        csvMaker.makeCSV(transactions, "/Users/pauldenhez/Downloads/relevédecompte.csv", ';');
    }
}