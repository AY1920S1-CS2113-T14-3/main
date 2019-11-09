package ui;

import dictionary.Word;
import dictionary.WordCount;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Map;
import java.util.Stack;

import java.util.Date;

/**
 * Represents the object that displays prompts and feedback from the system to the user's commands.
 */
public class Ui {

    /**
     * Greets users when they open the app.
     * @return a greeting string
     */
    public String greet() {
        return ("\n                      |   | _ _ _|   /  \\ _  \n"
                + "                      |/\\|(_)| (_|  \\__/|_) \n"
                + "                                            |   \n\n"
                + "Welcome, what would you like to do today?");
    }

    /**
     * Greets user when they move to quiz scene.
     * @return a greeting string
     */
    public String quizGreet() {
        return ("\n                      |   | _ _ _|   /  \\ _  \n"
                + "                      |/\\|(_)| (_|  \\__/|_) \n"
                + "                                            |   \n"
                + "Let's do some quiz to enhance your word knowledge \n"
                + "Type \"start\" to begin quiz or \"exit_quiz\" to go back");
    }

    public String showDeleted(Word w) {
        return "Noted. I've removed this word:\n" + w.toString();
    }

    public String showAdded(Word w) {
        return "Got it. I've added this word:\n" + w.toString();
    }

    public String showEdited(Word w) {
        return "Got it. I've edited this word:\n" + w.toString();
    }

    /**
     * Shows the tags to be added.
     * @param word word to add tag
     * @param tags list of tags to be added
     * @param tagList hash set represents existed tags of the word
     * @return a string shown when command is completed
     */
    public String showAddTag(String word, ArrayList<String> tags, HashSet<String> tagList) {
        String returnedString = "I have added " + (tags.size() == 1 ? "this tag \"" + tags.get(0) + "\"" : "these tags")
                + " to word \"" + word + "\"" + "\n";
        returnedString += "Here " + (tagList.size() == 1 ? "is the tag " : "are the tags ")
                + "of word \"" + word + "\"" + "\n";
        StringBuilder stringBuilder = new StringBuilder();
        for (String tag : tagList) {
            stringBuilder.append(tag + "\n");
        }
        return returnedString + stringBuilder.toString();
    }

    /**
     * Shows the tags to be added.
     * @param word word to add tag
     * @param synonyms list of tags to be added
     * @param synonymHashSet hash set represents existed tags of the word
     * @return a string shown when command is completed
     */
    public String showAddSynonym(String word, ArrayList<String> synonyms, HashSet<String> synonymHashSet) {
        String returnedString = "I have added "
                + (synonyms.size() == 1 ? "this synonym \""
                + synonyms.get(0) + "\"" : "these synonyms")
                + " to word \"" + word + "\"" + "\n";
        returnedString += "Here " + (synonymHashSet.size() == 1 ? "is the synonym " : "are the synonyms ")
                + "of word \"" + word + "\"" + "\n";
        StringBuilder stringBuilder = new StringBuilder();
        for (String synonym : synonymHashSet) {
            stringBuilder.append(synonym + "\n");
        }
        return returnedString + stringBuilder.toString();
    }

    /**
     * Shows the list of all words in the word bank.
     * @param wordBank to store all words
     * @param order displayOrder to show words (ascending / descending)
     * @return a string shown when command is completed
     */
    public String showList(TreeMap<String, Word> wordBank, String order) {
        String returnedString = "Here are your words:\n";
        if (order.equals("asc") || order.equals("")) {
            for (Map.Entry<String, Word> entry : wordBank.entrySet()) {
                returnedString += entry.getValue() + "\n";
            }
        } else {
            for (String description : wordBank.descendingKeySet()) {
                returnedString += wordBank.get(description) + "\n";
            }
        }
        return returnedString;
    }

    /**
     * Shows completion when tags are deleted from words.
     * @param word word to be deleted tags
     * @param deletedTags list containing tags to delete from word
     * @return a string shown when command is completed
     */
    public String showDeletedTags(String word, ArrayList<String> deletedTags) {
        StringBuilder stringBuilder = new StringBuilder();
        if (deletedTags.size() > 0) {
            stringBuilder.append("I have removed " + (deletedTags.size() == 1 ? "this tag " : "these tags ")
                    + "from the word \"" + word + "\"" + "\n");
            for (String tag : deletedTags) {
                stringBuilder.append(tag + "\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Shows non-existing tags of the words that are searched.
     * @param word a string represents a word to be searched for tags
     * @param nullTags list of non-existing tags that are searched
     * @return a string to show all non-existing tags
     */
    public String showNullTags(String word, ArrayList<String> nullTags) {
        StringBuilder stringBuilder = new StringBuilder();
        if (nullTags.size() > 0) {
            stringBuilder.append((nullTags.size() == 1 ? "This tag " : "These tags ")
                    + "doesn't exist in the word \"" + word + "\"" + "\n");
            for (String tag : nullTags) {
                stringBuilder.append(tag + "\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Show search result.
     * @param description word itself
     * @param meaning meaning or word
     * @param example example usage of word
     * @return meaning and example usage, if any.
     */
    public String showSearch(String description, String meaning, String example) {
        if (example.equals(null)) {
            return ("Here is the meaning of " + description + ": " + meaning + "\n");
        } else {
            return ("Here is the meaning of " + description + ": " + meaning + ",\n" +
                    "Here is the example usage:" + example);
        }
    }

    /**
     * Shows a list of words ordered by their search count in ascending or descending displayOrder as specified.
     * @param wordCount a main class object containing the word bank content
     * @param order the displayOrder (asc/desc) in which to display the word list
     * @return a string to show list of words and their search count
     */
    public String showSearchFrequency(WordCount wordCount, String order) {
        TreeMap<Integer, TreeMap<String, Word>> wordCountMap = wordCount.getWordCount(); //get map ordered by word count
        String returnedString = "You have searched for these words ";
        if (order.equals(
                "asc") || order.equals("")) { //list in ascending displayOrder
            returnedString += "least:\n";
            for (Map.Entry<Integer, TreeMap<String, Word>> entry : wordCountMap.entrySet()) {
                returnedString += entry.getKey() + " searches -\n";
                for (Map.Entry<String, Word> word : entry.getValue().entrySet()) {
                    returnedString += word.getKey() + "\n";
                }
            }
        } else { //list in descending displayOrder
            returnedString += "most:\n";
            for (Integer searchCount : wordCountMap.descendingKeySet()) {
                returnedString += searchCount + " searches -\n";
                for (Map.Entry<String, Word> word : wordCountMap.get(searchCount).entrySet()) {
                    returnedString += word.getKey() + "\n";
                }
            }
        }
        return returnedString;
    }

    /**
     * Shows a string to inform the completion when user look for search history.
     * @param wordHistory stack containing the closest searches
     * @param numberOfWordsToDisplay number of closest searched words to display
     * @return a string shown when command is completed
     */
    public String showRecentlyAdded(Stack<Word> wordHistory, int numberOfWordsToDisplay) {
        int numberOfWords;
        String s = "";
        if (numberOfWordsToDisplay > wordHistory.size()) {
            s += "The number of words requested exceeds the number of words in your word bank.\n";
            numberOfWords = wordHistory.size();
        } else {
            numberOfWords = numberOfWordsToDisplay;
        }
        s += ("Here are the last " + numberOfWords + " words you have added:\n");
        for (int i = 0; i < numberOfWords; i++) {
            s += wordHistory.peek() + "\n";
            wordHistory.pop();
        }
        return s;
    }

    /**
     * Shows instructions to guide user to key in required inputs during reminder setup.
     * @param state represents the stage at which the setup process is at
     * @return string containing the instructions to be displayed
     */
    public String showReminderSetup(int state) {
        switch (state) {
        case 1: //request for the list of words user wants to be reminded of
            return "Please enter the list of words.\n" + "Enter an empty line to end input";
        case 2:
            return "Enter next word or an empty line to end input\n";
        case 3: //request the reminder date and time from user
            return "Please enter the date and time of the reminder in the format:"
                    + "dd-MM-yyyy HHmm";
        default:
            return "Invalid state";
        }
    }

    /**
     * Constructs a string with a summary of the reminder details from the user input.
     * @param reminderWordList the ArrayList containing the words to be reminded
     * @param date the string containing the reminder detail summary
     * @return
     */
    public String showReminderSummary(ArrayList<String> reminderWordList, Date date) {
        String s = "Done! You will be reminded on:\n" + date + " to study these words:\n";
        for (String word : reminderWordList) {
            s += word + "\n";
        }
        return s;
    }

    /**

     * Shows a string containing description and format of a specific instruction.
     * @param instruction name of the instruction which user wants to know more
     * @return a string to show manual of specific instruction
     */
    public String showHelp(String instruction) {
        if (instruction.equals("add")) {
            return "Add a word to wordbank.\n"
                    + "Format: add w/WORD m/MEANING [t/TAG]\n"
                    + "The TAG field is optional";
        } else if (instruction.equals("delete")) {
            return "Delete a word or tag from wordbank.\n"
                    + "Format: delete w/WORD_TO_BE_DELETED [t/TAG]";
        } else if (instruction.equals("exit")) {
            return "Exit WordUp.\n"
                    + "Format: exit";
        } else if (instruction.equals("search")) {
            return "Search the meaning of a specific word.\n"
                    + "Format: search w/WORD_TO_BE_SEARCHED";
        } else if (instruction.equals("list")) {
            return "Show the list of words in wordbank.\n"
                    + "Format: list [o/ORDER]\n"
                    + "(ORDER can be \"asc\" for ascending and \"desc\" for descending)";
        } else if (instruction.equals("list_tags")) {
            return "Show the list of all tags in tagbank.\n"
                    + "Format: list_tags\n";
        } else if (instruction.equals("history")) {
            return "View recent search history.\n"
                    + "Format: history {int value}";
        } else if (instruction.equals("freq")) {
            return "Show search frequency of each word.\n"
                    + "Format: freq [o/ORDER]\n"
                    + "(ORDER can be \"asc\" for ascending and \"desc\" for descending)";
        } else if (instruction.equals("edit")) {
            return "Edit the meaning of word.\n"
                    + "Format: edit w/WORD m/MEANING";
        } else if (instruction.equals("tag")) {
            return "Add tags of a specific word.\n"
                    + "Format: tag w/WORD t/TAG...\n"
                    + "For more than one tag, please append \"t/\" before each tag";
        } else if (instruction.equals("addsyn")) {
            return "Add synonyms to a word.\n"
                    + "Format: addsyn w/WORD s/synonyms\n"
                    + "For more than one synonym, you may separate them by a space";
        } else if (instruction.equals("quiz")) {
            return "Take a quiz to test yourself.\n"
                    + "Format: quiz";
        } else if (instruction.equals("schedule")) {
            return "Schedule words to study.\nReminder pop up will be shown at time set.\n"
                    + "Format: schedule";
        } else if (instruction.equals("search_begin")) {
            return "Search for words with their prefix.\n"
                    + "Format: search_begin w/d";
        } else if (instruction.equals("search_tag")) {
            return "Search for all words of a specific tag or all tags of a specific word.\n"
                    + "Format:\n"
                    + "Searching for words of a tag: search_tag t/TAG\n"
                    + "Searching for tags of a word: search_tag w/WORD";
        } else {
            return "Here are the commands for WordUp.\n"
                    + "add, delete, edit, exit, freq, help, history, list, list_tag"
                    + " schedule, search, search_begin, search_tag, tag, addsyn, quiz\n"
                    + "Enter \"help [command]\" for more details.";
        }
    }

    /**
     * Displays quiz to ask user.
     * @param question word to be asked for meaning
     * @param options options available to be chosen
     * @param optionSequence sequence of the options
     * @return a string shown when the command is completed
     */
    public String quizDisplay(String question, String[] options, int optionSequence) {
        String s = ("What is the meaning of " + question + "?\n");
        int index = 1;
        for (int i = optionSequence; i < optionSequence + 4; i++) {
            s += (index + "." + options[i % 4] + "  ");
            index++;
        }
        s += "\n";
        return s;
    }

    /**
     * Shows respond of bot when user input the answer.
     * @param isCorrect is true if user get the correct answer
     * @param answer correct answer
     * @return a string shown when the command is completed
     */
    public String quizResponse(Boolean isCorrect, String answer) {
        if (isCorrect) {
            return ("Yes!! The correct answer is \"" + answer + "\".");
        } else {
            return ("Sorry, The answer is \"" + answer + "\".");
        }
    }

    /**
     * Shows score of the quiz.
     * @param wrongQuiz is the number of wrong answers answered
     * @param countQuiz is number of questions answered
     * @param quizArray are the words where the user had wrong answers
     * @return a string shown
     */
    public String quizIncorrect(Integer wrongQuiz, Integer countQuiz, ArrayList<String> quizArray) {
        if (wrongQuiz == 0) {
            return ("Congratulations! You got "
                    + (countQuiz - wrongQuiz)
                    + "/" + countQuiz
                    + " on this quiz!\n"
                    + "type exit_quiz to exit.");
        } else {
            return ("You got " + (countQuiz - wrongQuiz)
                    + "/"
                    + countQuiz
                    + " on this quiz!\n"
                    + "These are the words you might want to review:\n"
                    + quizArray
                    + "\ntype exit_quiz to exit.");
        }
    }

    /**
     * Shows to user all words that have a specific beginning.
     * @param begin begin substring to be searched
     * @param wordWithBegins list of all words that have that begin substring
     * @return a string shown to user when the command is completed
     */
    public String showSearchBegin(String begin, ArrayList<String> wordWithBegins) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((wordWithBegins.size() == 1) ? "This is a word that has " : "These are words that have ");
        stringBuilder.append("the same meaning as " + begin + "\n");
        for (String s : wordWithBegins) {
            stringBuilder.append(s + "\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Shows to user all words of a specific tag.
     * @param searchTag tag to be searched
     * @param words array of words belong to the tag
     * @return string shown to user
     */
    public String showSearchTag(String searchTag, String[] words) {
        StringBuilder stringBuilder = new StringBuilder("Your tag \"" + searchTag + "\" has " + words.length
                + (words.length == 1 ? " word:\n" : " words:\n"));
        for (int i = 0; i < words.length; i++) {
            stringBuilder.append(words[i] + "\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Shows all tags in TagBank.
     * @param tagList array of all tags in the TagBank
     * @return string shown to user
     */
    public String showAllTags(String[] tagList) {
        StringBuilder stringBuilder = new StringBuilder("Here are all of your tags:\n");
        for (String tag : tagList) {
            stringBuilder.append(tag + "\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Shows all tags belong to a specific word.
     * @param searchTerm word to be searched for tags
     * @param allTags all of tags belong to the word
     * @return string shown to user
     */
    public String showTagsOfWord(String searchTerm, HashSet<String> allTags) {
        StringBuilder stringBuilder = new StringBuilder("Your word \"" + searchTerm + "\" has " + allTags.size()
                + (allTags.size() == 1 ? " tag:\n" : " tags:\n"));
        for (String tag : allTags) {
            stringBuilder.append(tag + "\n");
        }
        return stringBuilder.toString();
    }
}