package org.denhez.pdf.domain;

public class Bonus implements Transaction {
    TransactionInfo transactionInfo;

    public Bonus(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }
}

