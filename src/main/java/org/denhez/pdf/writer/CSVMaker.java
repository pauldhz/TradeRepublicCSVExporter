package org.denhez.pdf.writer;

import org.denhez.pdf.domain.Credit;
import org.denhez.pdf.domain.Debit;
import org.denhez.pdf.domain.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVMaker {

    public void makeCSV(List<Transaction> transactions, String destination, char separator) {
        try {
            FileWriter myWriter = new FileWriter(destination);
            myWriter.write("date"+separator+"libellé"+separator+"montant" + "\n");
            transactions.forEach(transaction -> {
                try {
                    if(transaction instanceof Credit || transaction instanceof Debit) {
                        myWriter.write(transaction.getDate().toString() + separator + transaction.getLabel() + separator
                                + transaction.getAmount().toString() + "\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing into file.");
        }
    }
}
