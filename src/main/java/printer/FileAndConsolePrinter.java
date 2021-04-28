package printer;

import sentence.Sentence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileAndConsolePrinter extends ConsolePrinter {
    File file;

    public FileAndConsolePrinter(File file) throws FileNotFoundException {
        this.file = file;
    }

    @Override
    public void print(Sentence sentence) {
        super.print(sentence);

        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(stringBuilder.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred when writing to file.");
            e.printStackTrace();
        }

    }
}
