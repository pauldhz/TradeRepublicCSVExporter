package org.denhez.pdf.reader;

import java.io.IOException;

public interface PdfReader {

    String read(String path) throws IOException;
}
