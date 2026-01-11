package org.denhez.pdf.domain;

import org.denhez.pdf.domain.vo.PositiveAmount;

import java.util.Optional;

public interface Transaction {

    int NB_COLUMNS_FOR_ROW = 5;
    int AMOUNT_INDEX = 4;

    static Optional<Transaction> parseAvoir(String row) {
        if (row == null || !row.startsWith("Avoir")) {
            return Optional.empty();
        }

        String[] parts = row.split(" ");
        if (parts.length < NB_COLUMNS_FOR_ROW) {
            return Optional.empty();
        }

        String montantStr = parts[parts.length - AMOUNT_INDEX].replace(",", ".");

        try {
            PositiveAmount amount = PositiveAmount.parse(montantStr);
            TransactionInfo info = new TransactionInfo(amount, TransactionType.DEBIT);
            return Optional.of(new Avoir(info));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    static Optional<Transaction> parseBonus(String row) {
        if (row == null || !row.startsWith("Bonus")) {
            return Optional.empty();
        }

        String[] parts = row.split(" ");
        if (parts.length < NB_COLUMNS_FOR_ROW) {
            return Optional.empty();
        }

        String montantStr = parts[parts.length - AMOUNT_INDEX].replace(",", ".");

        try {
            PositiveAmount amount = PositiveAmount.parse(montantStr);
            TransactionInfo info = new TransactionInfo(amount, TransactionType.CREDIT);
            return Optional.of(new Bonus(info));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    static Optional<Transaction> parseInterets(String row) {
        return null;
    }

    static Optional<Transaction> ExecutionOrdre(String row) {
        return null;
    }

    static Optional<Transaction> parseVirement(String row) {
        return null;
    }
}
