package org.denhez.pdf.writer;

import org.denhez.pdf.domain.Credit;
import org.denhez.pdf.domain.Debit;
import org.denhez.pdf.domain.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVMaker {
    protected static final String LINE_BREAK = "\n";

    public void makeCSV(List<Transaction> transactions, String destination, char separator) throws IOException {
        FileWriter myWriter = new FileWriter(destination);
        var header = buildRow(separator, "date", "libellé", "montant", LINE_BREAK);
        myWriter.write(header);
        for (Transaction transaction : transactions) {
            if (transaction instanceof Credit || transaction instanceof Debit) {
                var row = buildRow(separator,
                        transaction.getDate().toString(),
                        transaction.getLabel(),
                        transaction.getAmount().toString(),
                        "\n");
                myWriter.write(row);
            }
        }
        myWriter.close();
        System.out.println("CSV written successfully.");

    }

    private String buildRow(char separator, String... elements) {
        var sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            if (i != elements.length - 1) {
                sb.append(elements[i]).append(separator);
            } else {
                sb.append(elements[i]);
            }
        }
        return sb.toString();
    }
}
