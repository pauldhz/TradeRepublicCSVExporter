package org.denhez.pdf.domain.report.categorization;

import org.denhez.pdf.domain.report.TransactionInfo;

public interface RetrieveCategorization {

    Categorization getCategory(TransactionInfo transactionInfo);
}
