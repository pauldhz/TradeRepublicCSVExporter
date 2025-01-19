package org.denhez.pdf.domain;

import java.util.Arrays;

public enum TransactionType {
    VIREMENT("Virement ", "Virement"),
    TRANSACTION_BY_CARD("Transaction ", "Transaction"),
    INTEREST_PAYMENT("Paiement ", "Paiement"),
    COMMERCIAL("Commerce", "Commerce"),
    BONUS("Bonus", "Bonus"),
    NOT_FOUND("Undefined", "Undefined");

    private final String pdfLabel;

    private final String exportLabel;

    TransactionType(String pdfLabel, String exportLabel) {
        this.pdfLabel = pdfLabel;
        this.exportLabel = exportLabel;
    }

    public String getPdfLabel() {
        return pdfLabel;
    }

    public String getExportLabel() {
        return exportLabel;
    }

    public static TransactionType from(final String pdfLabel) {
        return Arrays.stream(TransactionType.values()).filter(type -> type.getPdfLabel().equals(pdfLabel)).findFirst().orElse(TransactionType.NOT_FOUND);
    }
}
