package org.denhez.pdf.domain;

import org.denhez.pdf.domain.vo.PositiveAmount;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Transaction {

    int NB_COLUMNS_FOR_ROW = 5;
    int AMOUNT_INDEX = 4;

    /**
     * Récupère les informations de la transaction
     * @return les informations de la transaction
     */
    TransactionInfo getTransactionInfo();

    static Optional<Transaction> parseAvoir(String row, Date operationDate) {
        return parseTransaction(row, "Avoir", TransactionType.DEBIT, Avoir::new, operationDate);
    }

    static Optional<Transaction> parseBonus(String row, Date operationDate) {
        return parseTransaction(row, "Bonus", TransactionType.CREDIT, Bonus::new, operationDate);
    }

    static Optional<Transaction> parseInterets(String row, Date operationDate) {
        return parseTransaction(row, "Intérêts", TransactionType.CREDIT, Interets::new, operationDate);
    }

    static Optional<Transaction> parseExecutionOrdre(String row, Supplier<String> nextLineSupplier, Date operationDate) {
        if (row == null || !row.equals("Exécution ")) {
            return Optional.empty();
        }

        try {
            String nextLine = nextLineSupplier.get();
            if (nextLine == null || !nextLine.equals("d'ordre")) {
                return Optional.empty();
            }

            StringBuilder descriptionBuilder = new StringBuilder();
            String line;
            while ((line = nextLineSupplier.get()) != null) {
                String[] parts = line.split(" ");

                if (parts.length == 4 && parts[1].equals("€") && parts[3].equals("€")) {
                    String montantStr = parts[0].replace(",", ".");
                    PositiveAmount amount = PositiveAmount.parse(montantStr);
                    String description = descriptionBuilder.toString().trim();
                    TransactionInfo info = new TransactionInfo(amount, TransactionType.DEBIT, description, operationDate);
                    return Optional.of(new ExecutionOrdre(info));
                }
                else if (parts.length >= 8 && parts[0].equals("Buy") && parts[1].equals("trade")) {
                    String montantStr = parts[8].replace(",", ".");
                    PositiveAmount amount = PositiveAmount.parse(montantStr);
                    String description = descriptionBuilder.toString().trim();
                    TransactionInfo info = new TransactionInfo(amount, TransactionType.DEBIT, description, operationDate);
                    return Optional.of(new ExecutionOrdre(info));
                }

                // Accumuler la description
                if (!descriptionBuilder.isEmpty()) {
                    descriptionBuilder.append(" ");
                }
                descriptionBuilder.append(line);
            }

            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    static Optional<Transaction> parseVirement(String row, Supplier<String> nextLineSupplier, Date operationDate) {
        if (row == null || !row.startsWith("Virement")) {
            return Optional.empty();
        }

        try {
            if (!row.trim().equals("Virement")) {
                return parseTransaction(row, "Virement", TransactionType.CREDIT, Virement::new, operationDate);
            }

            StringBuilder descriptionBuilder = new StringBuilder();
            String line;
            while ((line = nextLineSupplier.get()) != null) {
                line = line.trim();

                if (line.matches("^\\d+,\\d+ € \\d+,\\d+ €$")) {
                    String[] parts = line.split(" ");
                    String montantStr = parts[0].replace(",", ".");
                    PositiveAmount amount = PositiveAmount.parse(montantStr);
                    String description = descriptionBuilder.toString().trim();
                    TransactionInfo info = new TransactionInfo(amount, TransactionType.CREDIT, description, operationDate);
                    return Optional.of(new Virement(info));
                }

                // Accumuler la description
                if (!descriptionBuilder.isEmpty()) {
                    descriptionBuilder.append(" ");
                }
                descriptionBuilder.append(line);
            }

            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Optional<Transaction> parseTransaction(String row, String prefix, TransactionType type, Function<TransactionInfo,Transaction> factory, Date operationDate) {
        if (row == null || !row.startsWith(prefix)) {
            return Optional.empty();
        }

        String[] parts = row.split(" ");
        if (parts.length < NB_COLUMNS_FOR_ROW) {
            return Optional.empty();
        }

        // Extraire le montant
        String montantStr = parts[parts.length - AMOUNT_INDEX].replace(",", ".");

        // Extraire la description (entre le prefix et les montants)
        // Format: PREFIX DESCRIPTION MONTANT € SOLDE €
        // On prend tout entre l'index 1 (après le prefix) et length - 4 (avant MONTANT € SOLDE €)
        StringBuilder descriptionBuilder = new StringBuilder();
        for (int i = 1; i < parts.length - 4; i++) {
            if (i > 1) {
                descriptionBuilder.append(" ");
            }
            descriptionBuilder.append(parts[i]);
        }
        String description = descriptionBuilder.toString();

        try {
            PositiveAmount amount = PositiveAmount.parse(montantStr);
            TransactionInfo info = new TransactionInfo(amount, type, description, operationDate);
            return Optional.of(factory.apply(info));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
