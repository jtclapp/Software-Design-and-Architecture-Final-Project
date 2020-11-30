import java.io.IOException;

public class TestMain
{
    public static void main(String[] args) throws IOException {
        FileCollector fileCollector = new FileCollector();
        fileCollector.getFiles();
        fileCollector.getNumberOfLines();
        fileCollector.printNumberOfLines();
        fileCollector.getNumberOfCharacters();
        fileCollector.printNumberOfCharacters();
        fileCollector.getNumberOfWords();
        fileCollector.printNumberOfWords();
        fileCollector.getNumberOfUniqueWords();
        fileCollector.printUniqueWords();

    }
}
