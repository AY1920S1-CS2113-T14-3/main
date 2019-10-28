package command;

import dictionary.WordBank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.fail;


public class QuizTest {
    public String filename = "C:\\Users\\user\\gitclones\\main\\src\\test\\WordUpTest.txt";

    /**
     * Create wordup test file.
     * @throws FileNotFoundException if filename is not found
     * @throws UnsupportedEncodingException if encoding is not supported
     */
    @BeforeEach
    public void createWordUpTestFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println("apple: red fruit");
        writer.println("orange: orange fruit");
        writer.println("banana: yellow fruit");
        writer.println("kiwi: green fruit");
        writer.close();
    }

    /**
     * test generateQuiz() in QuizCommand.java
     */
    @Test
    public void generateQuizTest() {
        try {
            Storage storage = new Storage(filename);
            WordBank wordBank = new WordBank(storage.loadFile());
            QuizCommand quizCommand = new QuizCommand();
            String quiz = quizCommand.generateQuiz(wordBank);
            Assertions.assertTrue((quiz.equals("apple: red fruit")) || (quiz.equals("orange: orange fruit"))
                    || (quiz.equals("banana: yellow fruit")) || (quiz.equals("kiwi: green fruit")));
        } catch (Exception e) {
            fail("generateQuiz failed: " + e.getMessage());
        }
    }

    /**
     * Delete wordup test file.
     */
    @AfterEach
    public void deleteWordUpTestFile() {
        File file = new File(filename);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }
}
