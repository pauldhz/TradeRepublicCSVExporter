package org.denhez.pdf.domain.report.configuration.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter implements Converter<String, String, Date> {

    @Override
    public Date convert(String dayMonth, String year) {
        if (dayMonth == null || year == null) {
            return null;
        }
        try {
            dayMonth = dayMonth.trim();
            year = year.trim();

            if (!year.matches("\\d{4}")) {
                return null;
            }

            String dateStr = dayMonth + " " + year;

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.FRENCH);
            sdf.setLenient(true); // Plus tolérant pour gérer les variations (point après mois, etc.)
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("Erreur de parsing pour la date: '" + dayMonth + "' + '" + year + "' - " + e.getMessage());
            return null;
        }
    }
}
