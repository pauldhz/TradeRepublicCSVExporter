package org.denhez.pdf.domain.report;

import org.denhez.pdf.domain.report.categorization.Categorization;
import org.denhez.pdf.domain.vo.PositiveAmount;

import java.util.Date;

public record TransactionInfo(
        PositiveAmount amount,
        TransactionType transactionType,
        String description,
        Date operationDate,
        Categorization categorization) {

    public TransactionInfo withCategorization(Categorization categorization) {
        return new TransactionInfo(amount, transactionType, description, operationDate, categorization);
    }
}
