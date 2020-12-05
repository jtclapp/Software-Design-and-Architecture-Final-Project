import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

public class FileCollector {
    String[] files;
    String[] filenames;
    String[] content;
    List<String>[] words;
    int[] numOfLines;
    int[] numOfWords;
    int[] numOfCharacters;
    HashMap[] numOfUniqueWords;
    Scanner scanner;

    final int numberOfCores = Runtime.getRuntime().availableProcessors();
    final double blockingCoefficient = 0.9;
    final int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));

    public void getFiles() {
        scanner = new Scanner(System.in);
        System.out.print("Insert File Paths: ");
        String listOfFiles = scanner.nextLine();
        files = listOfFiles.split(",");
    }

    public void getFileNames() {
        filenames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            File file = new File(files[i]);
            filenames[i] = file.getName();
        }
    }

    public void printNumberOfLines(int i) {
        System.out.println(filenames[i] + " has " + numOfLines[i] + " lines.");
    }

    public void printNumberOfCharacters(int i) {
        System.out.println(filenames[i] + " has " + numOfCharacters[i] + " characters.");
    }

    public void printNumberOfWords(int i) {
        System.out.println(filenames[i] + " has " + numOfWords[i] + " words.");
    }

    public void printUniqueWords(int i) throws IOException {
        System.out.println("Unique words in " + filenames[i] + " are: ");
        if (numOfUniqueWords[i] != null) {
            for (int j = 0; j < words[i].size(); j++) {
                if (numOfUniqueWords[i].get(words[i].get(j)) == Integer.valueOf(1)) {
                    System.out.print(words[i].get(j) + " ");
                }
            }
        }
        System.out.println();
    }

    public void printResults() throws IOException {
        getFileNames();
        for (int i = 0; i < files.length; i++) {
            printNumberOfLines(i);
            printNumberOfCharacters(i);
            printNumberOfWords(i);
            printUniqueWords(i);
            System.out.println();
        }
    }

    public void getNumberOfLines() throws InterruptedException {
        numOfLines = new int[files.length];
        final List<Callable<Void>> partitions = new ArrayList<Callable<Void>>();

        for (int i = 0; i < files.length; i++) {
            int finalI = i;
            partitions.add(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    String file = Files.readString(Path.of(files[finalI]));
                    String[] lines = file.split("\n");
                    numOfLines[finalI] = lines.length;
                    return null;
                }
            });
        }
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        final List<Future<Void>> results = executorService.invokeAll(partitions,5000,TimeUnit.SECONDS);
        for(int e = 0; e < results.size(); e++)
        {
            results.get(e);
        }
    }

    public void getNumberOfCharacters() throws InterruptedException {
        numOfCharacters = new int[files.length];
        final List<Callable<Void>> partitions = new ArrayList<Callable<Void>>();

        for (int i = 0; i < files.length; i++) {
            int finalI = i;
            partitions.add(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    String[] folder = content[finalI].split("");
                    numOfCharacters[finalI] = folder.length;
                    return null;
                }
            });
        }
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        final List<Future<Void>> results = executorService.invokeAll(partitions,5000,TimeUnit.SECONDS);
        for(int e = 0; e < results.size(); e++)
        {
            results.get(e);
        }
    }

    public void getNumberOfWords() throws InterruptedException {
        numOfWords = new int[files.length];
        final List<Callable<Void>> partitions = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            int finalI = i;
            partitions.add(new Callable<Void>() {
                @Override
                public Void call() throws FileNotFoundException {
                    String[] words = content[finalI].split(" ");
                    numOfWords[finalI] = words.length;
                    return null;
                }
            });
        }
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        final List<Future<Void>> results = executorService.invokeAll(partitions,5000,TimeUnit.SECONDS);
        for(int e = 0; e < results.size(); e++)
        {
            results.get(e);
        }
    }

    public void getNumberOfUniqueWords() throws InterruptedException {
        numOfUniqueWords = new HashMap[files.length];
        words = new List[files.length];
        final List<Callable<Void>> partitions = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            int finalI = i;

            partitions.add(new Callable<Void>() {
                @Override
                public Void call() {
                    HashMap<String, Integer> map = new HashMap<>();
                    List<String> holder = new ArrayList<>();

                    String[] folder = content[finalI].replaceAll("[,.?!]"," ").split(" ");
                    for (int k = 0; k < folder.length; k++) {
                        holder.add(folder[k]);
                    }
                    for (int j = 0; j < holder.size(); j++) {
                        if (map.get(holder.get(j)) == null) {
                            map.put(holder.get(j), 1);
                        } else {
                            map.put(holder.get(j), map.get(holder.get(j)) + 1);
                        }
                    }
                    numOfUniqueWords[finalI] = map;
                    words[finalI] = holder;
                    return null;
                }
            });
        }
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        final List<Future<Void>> results = executorService.invokeAll(partitions,5000,TimeUnit.SECONDS);
        for(int e = 0; e < results.size(); e++)
        {
            results.get(e);
        }
    }

    public void readFiles() {
        content = new String[files.length];
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                for (int i = 0; i < files.length; i++) {
                    File file = new File(files[i]);
                    String s = "";
                    try {
                        scanner = new Scanner(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    while (scanner.hasNextLine()) {
                        s += scanner.nextLine();
                        s += " ";
                    }
                    content[i] = s;
                }
//            }
//        }).start();
    }
}
