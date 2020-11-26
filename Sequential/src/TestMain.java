public class TestMain
{
    public static void main(String[] args) throws InterruptedException {
        FileCollector fileCollector = new FileCollector();
        fileCollector.getFiles();
        fileCollector.getNumberOfLines();
        fileCollector.printNumberOfLines();
        fileCollector.getNumberOfCharacters();
        fileCollector.printNumberOfCharacters();
    }
}
