package exception;

public class WrongDeleteFormatException extends WrongFormatException {
    /**
     * Creates an exception if user input the wrong format for delete command.
     */
    public WrongDeleteFormatException() {
        super(" OOPS: Your input format is incorrect" + "\n"
                + "If you want to delete tag(s):\n    Expected format\n    \"delete w/WORD_TO_BE_DELETED [t/TAG]...\"" + "\n"
                + "If you want to delete word:\n    Expected format\n    \"delete w/WORD_TO_BE_DELETED\"\n");
    }
}