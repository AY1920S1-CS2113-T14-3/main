package command;

import dictionary.Bank;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to add a task.
 * Inherits from Command class.
 */
public class AddExampleCommand extends Command {
    String wordDescription;
    String example;

    public AddExampleCommand(String wordDescription, String example) {
        this.wordDescription = wordDescription;
        this.example = example;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {

            bank.addExampleToWord(this.wordDescription, this.example);
            storage.writeExcelFile(bank);
            return ui.showAddExample(this.wordDescription, this.example);

        //storage.writeFile(word.toString(), true);
    }
}
