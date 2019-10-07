package exception;

public class CommandInvalidException extends WordUpException {
    private String command;

    public CommandInvalidException(String falseCommand) {
        super("     ☹ OOPS: I don't understand what you have entered: ");
        this.command = falseCommand;
    }

    @Override
    public void showError() {
        System.out.println(this.getMessage() + command);
        System.out.println("     Please check help for more information on what command you can use.");
    }
}
