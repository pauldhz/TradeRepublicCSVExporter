package org.denhez.pdf.domain;

import org.denhez.pdf.domain.vo.PositiveAmount;

import java.util.Date;

public record TransactionInfo(PositiveAmount amount, TransactionType transactionType, String description, Date operationDate) { }
