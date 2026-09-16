package Utils;

import Exceptions.FramworkException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class CsvReader {
    private  CsvReader() {
        // static utility
    }
    public static List<String[]> readRows(String classpathResource) {
        List<String[]> rows = new ArrayList<>();
        try (InputStream input = CsvReader.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (input == null) {
                throw new FramworkException("CSV resource not found on classpath: " + classpathResource);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line;
                boolean isHeader = true;
                while ((line = reader.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }
                    if (line.isBlank()) {
                        continue;
                    }
                    rows.add(line.split(",", -1));
                }
            }
        } catch (IOException e) {
            throw new FramworkException("Failed to read CSV resource: " + classpathResource, e);
        }
        return rows;
    }
}
