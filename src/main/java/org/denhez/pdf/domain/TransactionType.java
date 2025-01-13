package org.denhez.pdf.domain;

import java.util.Arrays;

public enum TransactionType {
    VIREMENT("Virement "),
    TRANSACTION_BY_CARD("Transaction "),
    INTEREST_PAYMENT("Paiement "),
    COMMERCIAL("Commerce"),
    BONUS("Bonus"),
    NOT_FOUND("Undefined");

    private String pdfLabel;

    TransactionType(String pdfLabel) {
        this.pdfLabel = pdfLabel;
    }

    public String getPdfLabel() {
        return pdfLabel;
    }

    public static TransactionType from(final String pdfLabel) {
        return Arrays.stream(TransactionType.values()).filter(type -> type.getPdfLabel().equals(pdfLabel)).findFirst().orElse(TransactionType.NOT_FOUND);
    }
}
