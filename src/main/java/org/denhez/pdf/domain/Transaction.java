package org.denhez.pdf.domain;

import org.denhez.pdf.domain.vo.PositiveAmount;

import java.util.Optional;
import java.util.function.Function;

public interface Transaction {

    int NB_COLUMNS_FOR_ROW = 5;
    int AMOUNT_INDEX = 4;

    static Optional<Transaction> parseAvoir(String row) {
        return parseTransaction(row, "Avoir", TransactionType.DEBIT, Avoir::new);
    }

    static Optional<Transaction> parseBonus(String row) {
        return parseTransaction(row, "Bonus", TransactionType.CREDIT, Bonus::new);
    }

    private static Optional<Transaction> parseTransaction(String row, String prefix, TransactionType type, Function<TransactionInfo,Transaction> factory) {
        if (row == null || !row.startsWith(prefix)) {
            return Optional.empty();
        }

        String[] parts = row.split(" ");
        if (parts.length < NB_COLUMNS_FOR_ROW) {
            return Optional.empty();
        }

        String montantStr = parts[parts.length - AMOUNT_INDEX].replace(",", ".");

        try {
            PositiveAmount amount = PositiveAmount.parse(montantStr);
            TransactionInfo info = new TransactionInfo(amount, type);
            return Optional.of(factory.apply(info));
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
