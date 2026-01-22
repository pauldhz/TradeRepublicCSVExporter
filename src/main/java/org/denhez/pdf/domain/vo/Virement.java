package org.denhez.pdf.domain.vo;

import org.denhez.pdf.domain.report.Transaction;
import org.denhez.pdf.domain.report.TransactionInfo;

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

