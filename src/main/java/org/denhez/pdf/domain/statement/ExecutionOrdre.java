package org.denhez.pdf.domain.statement;

import org.denhez.pdf.domain.report.Transaction;
import org.denhez.pdf.domain.report.TransactionInfo;

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

