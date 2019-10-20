package parser;

import dictionary.Word;
import command.Command;
import command.QuizCommand;
import command.BadCommand;
import command.DeleteCommand;
import command.AddTagCommand;
import command.ListCommand;
import command.ExitCommand;
import command.AddCommand;
import command.SearchCommand;
import command.RecentlyAddedCommand;
import command.FrequentlySearchedCommand;
import command.EditCommand;

import exception.WrongQuizFormatException;
import exception.WrongAddFormatException;
import exception.EmptyWordException;
import exception.WrongAddTagFormatException;
import exception.WrongDeleteFormatException;
import exception.WrongListFormatDescription;
import exception.WrongEditFormatException;
import exception.WrongHistoryFormatException;
import exception.ZeroHistoryRequestException;
import exception.CommandInvalidException;
import exception.WordUpException;
import exception.WrongSearchFormatException;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Creates a Command object after extracting information needed.
 */
public class Parser {

    /**
     * Extracts the command specified in the user input and creates the respective command objects.
     *
     * @param input user input from command line
     * @return respective Command class objects
     */
    public static Command parse(String input) {
        try {
            String[] taskInfo = input.split(" ", 2);
            String userCommand = taskInfo[0];
            Command command;
            if (userCommand.equals("exit")) {
                return new ExitCommand();
            } else if (userCommand.equals("help")) {
                // CREATE A HELP COMMAND TO SHOW THE AVAILABLE INSTRUCTION
                return null;
            } else if (userCommand.equals("add")) {
                command = parseAdd(taskInfo);
            } else if (userCommand.equals("delete")) {
                command = parseDelete(taskInfo);
            } else if (userCommand.equals("search")) {
                command = parseSearch(taskInfo);
            } else if (userCommand.equals("list")) {
                command = parseList(taskInfo);
            } else if (userCommand.equals("history")) {
                command = parseHistory(taskInfo);
            } else if (userCommand.equals("freq")) {
                command = parseSearchFrequency(taskInfo);
            } else if (userCommand.equals("edit")) {
                command = parseEdit(taskInfo);
            } else if (userCommand.equals("tag")) {
                command = parseTag(taskInfo);
            } else if (userCommand.equals("quiz")) {
                command = parseQuiz(taskInfo);
            } else {
                try {
                    throw new CommandInvalidException(input);
                } catch (CommandInvalidException e) {
                    command = new BadCommand(e.showError());
                }
            }
            return command;
        } catch (WordUpException e) {
            return new BadCommand(e.showError());
        }
    }

    protected static Command parseAdd(String[] taskInfo) throws WrongAddFormatException, EmptyWordException {
        if (taskInfo.length == 1) {
            throw new WrongAddFormatException();
        }
        String[] wordDetail = taskInfo[1].split("w/");
        if (wordDetail.length != 3) {
            throw new WrongAddFormatException();
        }
        String wordDescription = wordDetail[1].trim();
        if (wordDescription.length() == 0) {
            throw new EmptyWordException();
        }
        String[] meaningAndTag = wordDetail[2].split("t/");
        String meaning = meaningAndTag[0].trim();
        if (meaning.length() == 0) {
            throw new EmptyWordException();
        }
        Word word;
        if (meaningAndTag.length > 1) {
            HashSet<String> tags = new HashSet<>();
            for (int j = 1; j < meaningAndTag.length; ++j) {
                tags.add(meaningAndTag[j]);
            }
            word = new Word(wordDescription, meaning, tags);
        } else {
            word = new Word(wordDescription, meaning);
        }
        return new AddCommand(word);
    }

    protected static Command parseDelete(String[] taskInfo) throws WrongDeleteFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongDeleteFormatException();
        }
        String[] wordAndTags = taskInfo[1].split("t/");
        if (wordAndTags.length == 1) {
            return new DeleteCommand(taskInfo[1].substring(2));
        } else {
            String wordDescription = wordAndTags[0].substring(2).trim();
            ArrayList<String> tags = new ArrayList<>();
            for (int i = 1; i < wordAndTags.length; ++i) {
                tags.add(wordAndTags[i].trim());
            }
            return new DeleteCommand(wordDescription, tags);
        }
    }

    protected static Command parseSearch(String[] taskInfo) throws WrongSearchFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongSearchFormatException();
        }
        return new SearchCommand(taskInfo[1].substring(2).trim());
    }

    protected static Command parseList(String[] taskInfo) throws WrongListFormatDescription {
        String order = "";
        if (taskInfo.length > 1) {
            if (!taskInfo[1].startsWith("o/")) {
                throw new WrongListFormatDescription();
            }
            order = taskInfo[1].substring(2);
            if (!order.equals("asc") && !order.equals("desc")) {
                throw new WrongListFormatDescription();
            }
        }
        return new ListCommand(order);
    }

    protected static Command parseHistory(String[] taskInfo) throws WrongHistoryFormatException, ZeroHistoryRequestException {
        int numberOfWordsToDisplay;
        if (taskInfo.length == 1) {
            throw new WrongHistoryFormatException();
        }
        if (taskInfo[1].equals("0")) {
            throw new ZeroHistoryRequestException();
        }
        try {
            numberOfWordsToDisplay = Integer.parseInt(taskInfo[1]);
        } catch (NumberFormatException nfe) {
            throw new WrongHistoryFormatException();
        }
        return new RecentlyAddedCommand(numberOfWordsToDisplay);
    }

    protected static Command parseSearchFrequency(String[] taskInfo) throws WrongListFormatDescription {
        String order = "";
        if (taskInfo.length > 1) {
            if (!taskInfo[1].startsWith("o/")) {
                throw new WrongListFormatDescription();
            }
            order = taskInfo[1].substring(2);
            if (!order.equals("asc") && !order.equals("desc")) {
                throw new WrongListFormatDescription();
            }
        }
        return new FrequentlySearchedCommand(order);
    }

    protected static Command parseEdit(String[] taskInfo) throws WrongEditFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongEditFormatException();
        }
        String[] wordAndMeanings = taskInfo[1].split("m/");
        if (wordAndMeanings.length != 2) {
            throw new WrongEditFormatException();
        }
        String wordDescription = wordAndMeanings[0].substring(2).trim();
        String meaning = wordAndMeanings[1];
        return new EditCommand(wordDescription, meaning);
    }

    protected static Command parseTag(String[] taskInfo) throws WrongAddTagFormatException{
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongAddTagFormatException();
        }
        String[] wordAndTags = taskInfo[1].split("t/");
        if (wordAndTags.length == 1) {
            throw new WrongAddTagFormatException();
        }
        String wordDescription = wordAndTags[0].substring(2).trim();
        ArrayList<String> tags = new ArrayList<>();
        for (int i = 1; i < wordAndTags.length; ++i) {
            tags.add(wordAndTags[i].trim());
        }
        return new AddTagCommand(wordDescription, tags);
    }

    protected static Command parseQuiz(String[] taskInfo) throws WrongQuizFormatException {
        if (taskInfo.length > 1) {
            throw new WrongQuizFormatException();
        }
        return new QuizCommand();
    }

}