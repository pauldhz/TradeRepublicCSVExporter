package org.denhez.pdf.domain;

public class Virement implements Transaction {
    TransactionInfo transactionInfo;

    public Virement(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }
}

