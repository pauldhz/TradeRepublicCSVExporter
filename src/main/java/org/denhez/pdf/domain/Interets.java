package org.denhez.pdf.domain;

public class Interets implements Transaction {
    TransactionInfo transactionInfo;

    public Interets(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }
}

