package org.denhez.pdf.domain;

import org.denhez.pdf.domain.vo.PositiveAmount;

public record TransactionInfo(PositiveAmount amount,TransactionType transactionType) { }
