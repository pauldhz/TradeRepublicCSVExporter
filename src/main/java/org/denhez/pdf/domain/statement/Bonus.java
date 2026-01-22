package org.denhez.pdf.domain.statement;

import org.denhez.pdf.domain.report.Transaction;
import org.denhez.pdf.domain.report.TransactionInfo;

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

