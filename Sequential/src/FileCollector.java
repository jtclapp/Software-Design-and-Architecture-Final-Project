import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class FileCollector
{
    String[] files;
    String[] filenames;
    int[] numOfLines;
    int[] numOfWords;
    int[] numOfCharacters;

    Scanner scanner;
    final int numberOfCores = Runtime.getRuntime().availableProcessors();
    final double blockingCoefficient = 0.9;
    final int poolSize = (int)(numberOfCores / (1 - blockingCoefficient));

    public void getFiles()
    {
        scanner = new Scanner(System.in);
        System.out.print("Insert File Paths: ");
        String listOfFiles = scanner.nextLine();
        files = listOfFiles.split(",");
    }
    public void getFileNames()
    {
        filenames = new String[files.length];
        for(int i = 0; i < files.length; i++)
        {
            File file = new File(files[i]);
            filenames[i] = file.getName();
        }
    }
    public void printNumberOfLines()
    {
        getFileNames();
        for(int i = 0; i < filenames.length; i++)
        {
            System.out.println(filenames[i] + " has " + numOfLines[i] + " lines.");
        }
    }
    public void printNumberOfCharacters()
    {
        getFileNames();
        for(int i = 0; i < filenames.length; i++)
        {
            System.out.println(filenames[i] + " has " + numOfCharacters[i] + " characters.");
        }
    }
    public void printNumberOfWords()
    {
        getFileNames();
        for(int i = 0; i < filenames.length; i++)
        {
            System.out.println(filenames[i] + " has " + numOfWords[i] + " words.");
        }
    }
    public void getNumberOfLines() {
        numOfLines = new int[files.length];
        final List<Callable<Void>> partitions = new ArrayList<Callable<Void>>();
        for(int i = 0; i < files.length; i++) {
            int finalI = i;
            partitions.add(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    File file = new File(files[finalI]);
                    Scanner reader = new Scanner(file);
                    while(reader.nextLine().isBlank() == false)
                    {
                        numOfLines[finalI]++;
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
    }
    public void getNumberOfCharacters()
    {
        numOfCharacters = new int[files.length];
        final List<Callable<Void>> partitions = new ArrayList<Callable<Void>>();
        for(int i = 0; i < files.length; i++) {
            int finalI = i;
            partitions.add(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    String file = Files.readString(Path.of(files[finalI]));
                    String[] characters = file.split("");
                    numOfCharacters[finalI] = characters.length;
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
    }
    public void getNumberOfWords()
    {

    }
}