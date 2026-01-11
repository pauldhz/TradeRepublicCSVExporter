package org.denhez.pdf.domain;

import org.denhez.pdf.domain.vo.PositiveAmount;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Transaction {

    int NB_COLUMNS_FOR_ROW = 5;
    int AMOUNT_INDEX = 4;

    static Optional<Transaction> parseAvoir(String row) {
        return parseTransaction(row, "Avoir", TransactionType.DEBIT, Avoir::new);
    }

    static Optional<Transaction> parseBonus(String row) {
        return parseTransaction(row, "Bonus", TransactionType.CREDIT, Bonus::new);
    }

    static Optional<Transaction> parseInterets(String row) {
        return parseTransaction(row, "Intérêts", TransactionType.CREDIT, Interets::new);
    }

    static Optional<Transaction> parseExecutionOrdre(String row, Supplier<String> nextLineSupplier) {
        if (row == null || !row.equals("Exécution ")) {
            return Optional.empty();
        }

        try {
            // Lire la ligne suivante "d'ordre"
            String nextLine = nextLineSupplier.get();
            if (nextLine == null || !nextLine.equals("d'ordre")) {
                return Optional.empty();
            }

            // Lire les lignes de description jusqu'à trouver la ligne avec les montants
            String line;
            while ((line = nextLineSupplier.get()) != null) {
                String[] parts = line.split(" ");

                // On cherche une ligne avec exactement 4 parties: montant, €, solde, €
                if (parts.length == 4 && parts[1].equals("€") && parts[3].equals("€")) {
                    String montantStr = parts[0].replace(",", ".");
                    PositiveAmount amount = PositiveAmount.parse(montantStr);
                    TransactionInfo info = new TransactionInfo(amount, TransactionType.DEBIT);
                    return Optional.of(new ExecutionOrdre(info));
                }
            }

            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    static Optional<Transaction> parseVirement(String row) {
        return null;
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
}
