package org.denhez.pdf.domain.statement;

import org.denhez.pdf.domain.report.Transaction;
import org.denhez.pdf.domain.report.TransactionInfo;

public class Interets implements Transaction {
    TransactionInfo transactionInfo;

    public Interets(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }

    @Override
    public void setTransactionInfo(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }
}

