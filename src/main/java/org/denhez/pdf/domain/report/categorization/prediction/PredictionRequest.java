package org.denhez.pdf.domain.report.categorization.prediction;

public record PredictionRequest(
    String date,
    String type,
    String description,
    double montant,
    String sens
) {}

