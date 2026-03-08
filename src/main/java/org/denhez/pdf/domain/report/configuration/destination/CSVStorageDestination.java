package org.denhez.pdf.domain.report.configuration.destination;

import org.denhez.pdf.domain.report.Transaction;
import org.denhez.pdf.tool.exporter.CsvExporter;

import java.io.IOException;
import java.util.List;

public class CSVStorageDestination implements StorageDestination {
    private final String outputPath;
    private final CsvExporter exporter;

    public CSVStorageDestination(String outputPath) {
        this.outputPath = outputPath;
        this.exporter = new CsvExporter();
    }

    @Override
    public void store(List<Transaction> transactions) throws IOException {
        exporter.export(transactions, outputPath);
        System.out.println("Export CSV réussi : " + outputPath);
    }
}
