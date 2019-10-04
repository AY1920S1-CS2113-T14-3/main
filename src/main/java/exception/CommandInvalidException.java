package exception;

public class CommandInvalidException extends DukeException {
    private String command;

    public CommandInvalidException(String falseCommand) {
        super("     ☹ OOPS: I don't understand what you have entered: ");
        this.command = falseCommand;
    }

    @Override
    public void showError() {
        System.out.println(this.getMessage() + command);
    }
}
