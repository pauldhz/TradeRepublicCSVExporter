package org.denhez.pdf.domain.report.categorization.prediction;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PredictionHttpClient {

    private final HttpClient httpClient;
    private final Gson gson;
    private final String predictionServiceUrl;

    public PredictionHttpClient(String predictionServiceUrl) {
        this.predictionServiceUrl = predictionServiceUrl;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }

    public PredictionResponse predict(PredictionRequest request) throws Exception {
        String jsonBody = gson.toJson(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(predictionServiceUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .timeout(Duration.ofSeconds(30))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erreur lors de l'appel au service de prédiction. Status: " + response.statusCode());
        }

        return gson.fromJson(response.body(), PredictionResponse.class);
    }
}

