public class TestMain
{
    public static void main(String[] args) throws InterruptedException {
        FileCollector fileCollector = new FileCollector();
        fileCollector.getFiles();
        int[] num = fileCollector.getNumberOfLines();
        for(int i = 0; i < num.length; i++)
        {
            System.out.println("Number of lines:" + num[i]);
        }
    }
}
