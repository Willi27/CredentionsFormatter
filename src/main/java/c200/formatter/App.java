package c200.formatter;

import static java.util.logging.Level.WARNING;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private static final String CREDS_GROUP = "creds";
    private static final String CREDS_REGEXP = "(?<" + CREDS_GROUP + ">(^[\\d\\w].*)(?=[:].[\\d\\w].*$))";
    private static final String CREDS_FILE_PATH = "src/main/resources/";

    public static void main(String[] args) {
        String inputFileName = "credentials.txt";
        String outputFileName = "output-" + inputFileName;
        Path inputFilePath = Paths.get(CREDS_FILE_PATH, inputFileName);
        Path outputFilePath = Paths.get(CREDS_FILE_PATH, outputFileName);

        Pattern pattern = Pattern.compile(CREDS_REGEXP);
        String lineSeparator = System.lineSeparator();
        String result;

        try (Stream<String> lines = Files.lines(inputFilePath)) {
            result = lines.map(String::trim)
                .map(line -> {
                    Matcher matcher = pattern.matcher(line);
                    if (!matcher.find()) {
                        LOGGER.log(WARNING, "Line doesn't contains credentials.");
                        return lineSeparator;
                    } else {
                        return matcher.group(CREDS_GROUP).concat(lineSeparator);
                    }
                })
                .reduce(String::concat)
                .orElseThrow(RuntimeException::new);

            File output = outputFilePath.toFile();
            if (!output.exists()) {
                Files.createFile(outputFilePath);
            }

            Files.write(outputFilePath, result.getBytes());
        } catch (Exception e) {
            LOGGER.log(WARNING, "Failed to parse creds.");
        }
    }
}
