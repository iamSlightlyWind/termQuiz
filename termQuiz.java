import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class termQuiz {

    static String currentFile = "";
    static ArrayList<QnA> question = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        currentFile = System.getProperty("user.dir");

        System.out.println("---- iamSlightlywind/selfQuiz ----\n");
        System.out.println("Select question library:");

        File current = new File(currentFile);

        while (!current.isFile()) {
            chooseFile();
            current = new File(currentFile);
        }

        readFile();

        for (QnA qna : question) {
            System.out.println(qna.question + " ");
            System.out.print(qna.answer + "\n\n");
        }
    }

    public static void chooseFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + currentFile);

        List<String> files = getFileList(currentFile);
        System.out.println("0. ../");

        for (int i = 1; i < files.size(); i++) {
            System.out.println(i + ". " + files.get(i));
        }

        System.out.print("Change: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice > 0 && choice < files.size()) {
            currentFile += "/" + files.get(choice);
        } else if (choice == 0) {
            currentFile = currentFile.substring(0, currentFile.lastIndexOf("/"));
        }
    }

    public static List<String> getFileList(String directory) {
        List<String> results = new ArrayList<String>();

        File[] files = new File(directory).listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                results.add(file.getName() + "/");
            } else {
                results.add(file.getName());
            }
        }

        return results;
    }

    public static void readFile() throws FileNotFoundException {
        File file = new File(currentFile);
        Scanner reader = new Scanner(file);

        while (reader.hasNextLine()) {
            String data = reader.nextLine();

            if (data.equals("") || data.charAt(0) == '#')
                continue;

            question.add(new QnA(data));
        }

        reader.close();
    }
}