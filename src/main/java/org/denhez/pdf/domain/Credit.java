package org.denhez.pdf.domain;

public class Credit extends Transaction {
    public Credit() {
        super();
        this.positiveAmount = true;
    }
}
