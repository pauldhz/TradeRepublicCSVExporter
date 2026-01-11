package org.denhez.pdf.domain;

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

