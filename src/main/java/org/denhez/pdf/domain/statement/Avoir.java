package org.denhez.pdf.domain.statement;

import org.denhez.pdf.domain.report.Transaction;
import org.denhez.pdf.domain.report.TransactionInfo;

public class Avoir implements Transaction {
    TransactionInfo transactionInfo;

    public Avoir(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }
}

