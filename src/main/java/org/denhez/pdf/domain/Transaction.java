package org.denhez.pdf.domain;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Transaction {
    protected Date date;
    protected TransactionType type;
    protected BigDecimal amount;
    protected String label;
    protected boolean positiveAmount;

    public Transaction() {}

    public Transaction date(Date date) {
        this.date = date;
        return this;
    }

    public Transaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Transaction label(String label) {
        this.label = label;
        return this;
    }

    public Transaction type(TransactionType type) {
        this.type = type;
        return this;
    }

    public Date getDate() {
        return date;
    }
    public BigDecimal getAmount() {
        return this.positiveAmount ? this.amount : this.amount.negate();
    }
    public String getLabel() {
        return label;
    }

    public String getTypeExportLabel() {
        return type.getExportLabel();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                ", label='" + label + '\'' +
                '}';
    }
}
