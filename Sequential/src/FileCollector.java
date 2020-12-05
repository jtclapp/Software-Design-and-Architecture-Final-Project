        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Scanner;

public class FileCollector
{
    String[] files;
    String[] filenames;
    String[] content;
    List<String>[] words;
    int[] numOfLines;
    int[] numOfWords;
    int[] numOfCharacters;
    HashMap[] numOfUniqueWords;
    Scanner scanner;

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
    public void printNumberOfLines(int i)
    {
        System.out.println(filenames[i] + " has " + numOfLines[i] + " lines.");
    }
    public void printNumberOfCharacters(int i)
    {
        System.out.println(filenames[i] + " has " + numOfCharacters[i] + " characters.");
    }
    public void printNumberOfWords(int i)
    {
        System.out.println(filenames[i] + " has " + numOfWords[i] + " words.");
    }
    public void printUniqueWords(int i) throws IOException {
        System.out.println("Unique words in " + filenames[i] + " are: ");
        if(numOfUniqueWords[i] != null) {
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
        for(int i = 0; i < files.length; i++)
        {
            printNumberOfLines(i);
            printNumberOfCharacters(i);
            printNumberOfWords(i);
            printUniqueWords(i);
            System.out.println();
        }
    }
    public void getNumberOfLines() throws IOException {
        numOfLines = new int[files.length];
        for(int i = 0; i < files.length; i++)
        {
            String file = Files.readString(Path.of(files[i]));
            String[] lines = file.split("\n");
            numOfLines[i] = lines.length;
        }
    }
    public void getNumberOfCharacters() throws FileNotFoundException {
        numOfCharacters = new int[files.length];
        for(int i = 0; i < files.length; i++)
        {
            String[] folder = content[i].split("");
            numOfCharacters[i] = folder.length;
        }
    }
    public void getNumberOfWords() throws FileNotFoundException {
        numOfWords = new int[files.length];
        for(int i = 0; i < files.length; i++)
        {
            String[] words = content[i].split(" ");
            numOfWords[i] = words.length;
        }
    }
    public void getNumberOfUniqueWords() throws FileNotFoundException {
        numOfUniqueWords = new HashMap[files.length];
        words = new List[files.length];

        for(int i = 0; i < files.length; i++)
        {
            HashMap<String, Integer> map = new HashMap<>();
            List<String> holder = new ArrayList<>();
            String[] folder = content[i].replaceAll("[,.?!]"," ").split(" ");

            for(int k = 0; k < folder.length; k++)
            {
                holder.add(folder[k]);
            }
            for (int j = 0; j < holder.size(); j++)
            {
                if (map.get(holder.get(j)) == null)
                {
                    map.put(holder.get(j), 1);
                } else {
                    map.put(holder.get(j), map.get(holder.get(j)) + 1);
                }
            }
            words[i] = holder;
            numOfUniqueWords[i] = map;
        }
    }
    public void readFiles() {
        content = new String[files.length];
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
    }
}