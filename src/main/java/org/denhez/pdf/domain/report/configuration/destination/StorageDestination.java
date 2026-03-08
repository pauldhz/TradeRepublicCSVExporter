package org.denhez.pdf.domain.report.configuration.destination;

import org.denhez.pdf.domain.report.Transaction;

import java.util.List;

public interface StorageDestination {

    void store(List<Transaction> transactions) throws Exception ;
}
