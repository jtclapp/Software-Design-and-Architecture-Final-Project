import java.io.IOException;

public class TestMain
{
    public static void main(String[] args) throws IOException {
        FileCollector fileCollector = new FileCollector();
        fileCollector.getFiles();

        long startTime = System.currentTimeMillis();

        fileCollector.getNumberOfLines();
        fileCollector.getNumberOfCharacters();
        fileCollector.getNumberOfWords();
        fileCollector.getNumberOfUniqueWords();
        fileCollector.printResults();

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("\n" + "Elapsed time to complete: " + elapsedTime + " milliseconds");

    }
}
