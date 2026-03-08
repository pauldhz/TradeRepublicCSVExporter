package org.denhez.pdf.domain.report.categorization;

public record Categorization(String category, String subcategory, double confidence) {

    public boolean isConfident() {
        return confidence > 0.65;
    }
}
