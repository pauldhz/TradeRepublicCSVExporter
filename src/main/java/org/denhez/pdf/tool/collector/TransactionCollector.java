package org.denhez.pdf.tool.collector;

import org.denhez.pdf.domain.*;
import org.denhez.pdf.tool.explorer.Explorer;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionCollector {
    final Deque<String> textDeque;
    Explorer<Deque<String>, String> explorer;

    public TransactionCollector(Explorer<Deque<String>, String> explorer, Deque<String> textDeque) {
        this.explorer = explorer;
        this.textDeque = textDeque;
    }

    public List<Transaction> exploreAndCollect() {
        List<Transaction> transactions = new ArrayList<>();
        int count = 0;
        while (!this.textDeque.isEmpty()) {

            count++;
            var date = formatToDate(explorer.explore(this.textDeque, this::isDate, 2, "").getSecond());
            var type = explorer.explore(this.textDeque, 2);
            if (type.getSecond().equals("end")) {
                break;
            }
            Transaction transaction = switch (type.getSecond()) {
                case "Paiement " -> {
                    var labelAmount = explorer.explore(this.textDeque, 2).getSecond();
                    var matcher = extractLabelFromAmount(labelAmount);
                    if (matcher.find()) {
                        var label = matcher.group(1).trim();
                        var amount = matcher.group(2);
                        yield new Credit()
                                .type(TransactionType.from(type.getSecond()))
                                .date(date)
                                .label(label)
                                .amount(new BigDecimal(amount.replace(',', '.')));
                    }
                    yield new Other().label("Failed Credit : " + type + " - " + labelAmount).date(date);
                }

                case "Transaction " -> {
                    var labelAmount = explorer.explore(this.textDeque, 2).getSecond();
                    var matcher = extractLabelFromAmount(labelAmount);
                    if (matcher.find()) {
                        var label = matcher.group(1).trim();
                        var amount = matcher.group(2);

                        yield new Debit()
                                .type(TransactionType.from(type.getSecond()))
                                .date(date)
                                .label(label)
                                .amount(new BigDecimal(amount.replace(',', '.')));
                    }
                    yield new Other().label("Failed transaction : " + labelAmount).date(date);
                }
                case "Commerce" -> {
                    var label = explorer.explore(this.textDeque, 1, 2, "- ").getSecond();
                    var amount = explorer.explore(this.textDeque, 2, x -> x.substring(0, x.length() - 2)).getSecond();
                    yield new Debit()
                            .type(TransactionType.from(type.getSecond()))
                            .date(date)
                            .label(label)
                            .amount(new BigDecimal(amount.replace(',', '.')));
                }
                default -> {
                    var row = type.getSecond().split(" ");
                    var currentType = row[0];
                    var amount = row[row.length - 1].substring(0, row[row.length - 1].length() - 2);
                    var sb = new StringBuilder();
                    for (int i = 1; i < row.length - 2; i++) {
                        sb.append(row[i]);
                    }
                    var label = sb.toString();
                    yield new Credit()
                            .type(TransactionType.from(currentType))
                            .date(date)
                            .label(label)
                            .amount(new BigDecimal(amount.replace(',', '.')));
                }
            };
            if (transaction != null) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public boolean isDate(String dateStr) {
        return this.formatToDate(dateStr) != null;
    }

    public Date formatToDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.FRANCE);
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private Matcher extractLabelFromAmount(String labelAmount) {
        Pattern pattern = Pattern.compile("^(.*?)(\\d{1,3}(?:[.,]\\d{2})?)\\s*[\\u00A0\\u0020]?€");
        return pattern.matcher(labelAmount);
    }
}