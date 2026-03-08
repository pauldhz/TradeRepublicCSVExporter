package org.denhez.pdf.domain.report.configuration.conversion;

public interface Converter <T,U,R> {
    R convert(T t, U u);
}
