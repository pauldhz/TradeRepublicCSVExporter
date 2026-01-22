package org.denhez.pdf.domain;

public class ExecutionOrdre implements Transaction {
    TransactionInfo transactionInfo;

    public ExecutionOrdre(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }
}

