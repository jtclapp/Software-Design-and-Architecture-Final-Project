import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class FileCollector
{
    String[] files;
    Scanner scanner;
    public FileCollector()
    {
        final int numberOfCores = Runtime.getRuntime().availableProcessors();
        final double blockingCoefficient = 0.9;
        final int poolSize = (int)(numberOfCores / (1 - blockingCoefficient));
    }
    public String[] getFiles()
    {
        scanner = new Scanner(System.in);
        System.out.print("Insert List Of Files: ");
        String listOfFiles = scanner.nextLine();
        files = listOfFiles.split(",");
        return files;
    }
    public int getNumberOfLines()
    {
        int num;
        final List<Callable<Void>> partitions = new ArrayList<Callable<Void>>();
        for(int i = 0; i < files.length; i++) {
            int finalI = i;
            partitions.add(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    return null;
                }
            });
        }
        return num;
    }
    public int getNumberOfCharacters()
    {
        int num;

        return num;
    }
    public int getNumberOfWords()
    {
        int num;

        return num;
    }
}