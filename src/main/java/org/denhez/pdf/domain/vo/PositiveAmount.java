package org.denhez.pdf.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object représentant un montant positif pour les transactions.
 */
public class PositiveAmount {

    private final BigDecimal value;

    private PositiveAmount(BigDecimal value) {
        this.value = Validator.of(value)
                .validate(Objects::nonNull, "Le montant ne peut pas être null")
                .validate(bigDecimal -> !bigDecimal.equals(BigDecimal.ZERO), "Le montant doit être positif")
                .get();
    }

    /**
     * Crée un PositiveAmount à partir d'un BigDecimal.
     * @param value la valeur (doit être >= 0)
     * @return un PositiveAmount
     * @throws IllegalArgumentException si la valeur est négative ou null
     */
    public static PositiveAmount of(BigDecimal value) {
        return new PositiveAmount(value);
    }

    /**
     * Crée un PositiveAmount à partir d'un double.
     * @param value la valeur (doit être >= 0)
     * @return un PositiveAmount
     * @throws IllegalArgumentException si la valeur est négative
     */
    public static PositiveAmount of(double value) {
        return new PositiveAmount(BigDecimal.valueOf(value));
    }

    /**
     * Crée un PositiveAmount à partir d'une chaîne de caractères.
     * @param value la valeur sous forme de String
     * @return un PositiveAmount
     * @throws IllegalArgumentException si la valeur est négative ou invalide
     * @throws NumberFormatException si la chaîne n'est pas un nombre valide
     */
    public static PositiveAmount parse(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Le montant ne peut pas être vide");
        }
        return new PositiveAmount(new BigDecimal(value));
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositiveAmount that = (PositiveAmount) o;
        return value.compareTo(that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }
}
