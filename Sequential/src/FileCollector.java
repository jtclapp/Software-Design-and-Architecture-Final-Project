import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class FileCollector
{
    String[] files;
    Scanner scanner;
    final int numberOfCores = Runtime.getRuntime().availableProcessors();
    final double blockingCoefficient = 0.9;
    final int poolSize = (int)(numberOfCores / (1 - blockingCoefficient));

    public void printInfo()
    {

    }

    public String[] getFiles()
    {
        scanner = new Scanner(System.in);
        System.out.print("Insert File Paths: ");
        String listOfFiles = scanner.nextLine();
        files = listOfFiles.split(",");
        return files;
    }
    public int[] getNumberOfLines() {
        int[] num = new int[files.length];
        final List<Callable<Void>> partitions = new ArrayList<Callable<Void>>();
        for(int i = 0; i < files.length; i++) {
            int finalI = i;
            partitions.add(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    File file = new File(files[finalI]);
                    if(file.exists() == false)
                    {
                        System.out.println(file.getName() + " doesn't exist.");
                    }
                    Scanner reader = new Scanner(file);
                    while(reader.nextLine().isBlank() == false)
                    {
                        num[finalI]++;
                    }
                    return null;
                }
            });
        }
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        final List<Future<Void>> results;
        try {
            results = executorService.invokeAll(partitions,5000, TimeUnit.SECONDS);
            for(int i = 0; i < results.size(); i++)
            {
                results.get(i);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return num;
    }
//    public int getNumberOfCharacters()
//    {
//        int num;
//
//        return num;
//    }
//    public int getNumberOfWords()
//    {
//        int num;
//
//        return num;
//    }
}