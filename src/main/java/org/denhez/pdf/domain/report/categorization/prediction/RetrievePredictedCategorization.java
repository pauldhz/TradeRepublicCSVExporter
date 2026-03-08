package org.denhez.pdf.domain.report.categorization.prediction;

import org.denhez.pdf.domain.report.TransactionInfo;
import org.denhez.pdf.domain.report.categorization.Categorization;
import org.denhez.pdf.domain.report.categorization.RetrieveCategorization;

import java.text.SimpleDateFormat;

public class RetrievePredictedCategorization implements RetrieveCategorization {

    private final PredictionHttpClient predictionHttpClient;
    private final SimpleDateFormat dateFormat;

    public RetrievePredictedCategorization(String predictionServiceUrl) {
        this.predictionHttpClient = new PredictionHttpClient(predictionServiceUrl);
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public Categorization getCategory(TransactionInfo transactionInfo) {
        try {
            String formattedDate = dateFormat.format(transactionInfo.operationDate());
            String type = mapTransactionType(transactionInfo);
            String description = transactionInfo.description();
            double montant = transactionInfo.amount().getValue().doubleValue();
            String sens = transactionInfo.transactionType().name();

            PredictionRequest request = new PredictionRequest(
                formattedDate,
                type,
                description,
                montant,
                sens
            );

            PredictionResponse response = predictionHttpClient.predict(request);
            return new Categorization(response.category(), response.subcategory(), response.confidence());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la prédiction de catégorie", e);
        }
    }

    private String mapTransactionType(TransactionInfo transactionInfo) {
        // Mapper vers "Avoir", "Bonus", "Intérêts", etc.
        // À adapter selon vos besoins
        return "Avoir"; // Valeur par défaut
    }
}
