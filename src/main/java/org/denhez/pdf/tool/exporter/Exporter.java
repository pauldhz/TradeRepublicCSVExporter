package org.denhez.pdf.tool.exporter;

import org.denhez.pdf.domain.report.Transaction;

import java.util.List;

public interface Exporter {

    void export(List<Transaction> transactions, String outputPath) throws Exception;
}
