import java.io.IOException;

public class TestMain
{
    public static void main(String[] args) throws IOException {
        FileCollector fileCollector = new FileCollector();
        fileCollector.getFiles();
        fileCollector.getNumberOfLines();
        fileCollector.getNumberOfCharacters();
        fileCollector.getNumberOfWords();
        fileCollector.getNumberOfUniqueWords();
        fileCollector.printResults();

    }
}
