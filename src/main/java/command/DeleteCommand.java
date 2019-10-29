package command;

import dictionary.Bank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command from user to delete a task.
 * Inherits from Command class.
 */
public class DeleteCommand extends Command {

    protected String deletedWord;
    protected ArrayList<String> tags;

    public DeleteCommand(String deletedWord) {
        this.deletedWord = deletedWord;
        this.tags = new ArrayList<>();
    }

    public DeleteCommand(String deletedWord, ArrayList<String> tags) {
        this.deletedWord = deletedWord;
        this.tags = tags;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            word = bank.getWordFromWordBank(this.deletedWord);
            if (tags.size() == 0) {                     //delete word
                int initWordBankSize = bank.getWordBankSize();
                int initTagBankSize = bank.getTagBankSize();

                bank.deleteWordFromBank(word);
                storage.updateFile(word.toString() + "\r","");

                storage.writeExcelFile(bank);
                storage.deleteRowsWordBankSheet(bank.getWordBankSize(), initWordBankSize);
                storage.deleteRowsTagBankSheet(bank.getTagBankSize(), initTagBankSize);

                return ui.showDeleted(word);
            } else {                                    //delete tags
                ArrayList<String> nullTags = new ArrayList<>();
                ArrayList<String> deletedTags = new ArrayList<>();
                int initTagBankSize = bank.getTagBankSize();
                bank.deleteTags(deletedWord, tags, deletedTags, nullTags);
                storage.writeTagBankExcelFile(bank.getTagBank());
                storage.deleteRowsTagBankSheet(bank.getTagBankSize(), initTagBankSize);
                String returned = ui.showDeletedTags(deletedWord, deletedTags);
                returned += ui.showNullTags(deletedWord, nullTags);
                return returned;
            }
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}