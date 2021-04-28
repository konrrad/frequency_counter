import config.ConfigProvider;
import config.JSONConfigProvider;
import printer.FileAndConsolePrinter;
import printer.SentencePrinter;
import sentence.Sentence;

import java.io.File;
import java.util.Set;

public class Main {
    public static final String configPath = "src/main/resources/config.json";
    public static final String outputPath = "output.txt";

    public static void main(String[] args) {
        try {
            ConfigProvider configProvider = new JSONConfigProvider(new File(configPath));
            Set<Character> desiredChars = configProvider.getDesiredChars();
            String phrase = configProvider.getOriginalSequence();

            SentencePrinter fileAndConsole = new FileAndConsolePrinter(new File(outputPath));
            fileAndConsole.print(new Sentence(phrase, desiredChars));

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
