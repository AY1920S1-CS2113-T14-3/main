package exception;

public class DukeException extends Exception {

    protected ErrorType error;
    protected String errorType;

    public enum ErrorType {
        LIST_EMPTY,
        COMMAND_EMPTY,
        COMMAND_INVALID,
        INDEX_MISSING,
        INDEX_EXCEEDED,
        SEARCHPHRASE_MISSING,
        FORMAT_TODO,
        FORMAT_DEADLINE,
        FORMAT_EVENT,
        FORMAT_RECURRING,
        FORMAT_RECURRING_DATE,
        FORMAT_VIEW,
        INVALID_DATE_TIME,
        FORMAT_FIXED,
    }

    public DukeException(ErrorType e) {
        error = e;
    }

    //if incomplete fields for task commands
    public void showError() {
        switch(error) {
            case LIST_EMPTY: System.out.println("     ☹ OOPS: Task list is empty. Please input a task before\n             viewing list."); break;
            case COMMAND_EMPTY: System.out.println("     ☹ OOPS: Command cannot be empty, please input a command."); break;
            case COMMAND_INVALID: System.out.println("     ☹ OOPS: I don't understand what you have entered."); break;
            case INDEX_MISSING: System.out.println("     ☹ OOPS: Missing task index. Please specify the index of the\n             task."); break;
            case INDEX_EXCEEDED: System.out.println("     ☹ OOPS: Task not found. Index is out of range or the list\n             is empty."); break;
            case SEARCHPHRASE_MISSING: System.out.println("     ☹ OOPS: Please specify using keywords the task you would\n             like to search for."); break;
            case FORMAT_TODO: System.out.println("     ☹ OOPS: Expected format: \"todo [description of task]\""); break;
            case FORMAT_DEADLINE: System.out.println("     ☹ OOPS: Expected format: \"deadline [description of task] /by\n             DD/MM/YYYY HHMM\""); break;
            case FORMAT_EVENT: System.out.println("     ☹ OOPS: Expected format: \"event [description of event] /at\n            DD/MM/YYYY HHMM"); break;
            case FORMAT_RECURRING: System.out.println("     ☹ OOPS: Expected format: \"recurring [description of task] /every\""); break;
            case FORMAT_RECURRING_DATE: System.out.println("     ☹ OOPS: Recurring task cannot have a specific date"); break;
            case FORMAT_VIEW: System.out.println("     ☹ OOPS: Expected format: \"view DD/MM/YYYY"); break;
            case INVALID_DATE_TIME: System.out.println("     ☹ OOPS: Your input date and time is not correct"); break;
            case FORMAT_FIXED: System.out.println("     ☹ OOPS: Expected format: \"fixed [description of task] /take\n         HHMM"); break;
        }
    }
}