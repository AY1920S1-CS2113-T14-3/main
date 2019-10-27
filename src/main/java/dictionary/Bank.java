package dictionary;

import exception.NoWordFoundException;
import exception.WordAlreadyExistException;

import java.util.ArrayList;
import java.util.HashSet;

public class Bank {
    private WordBank wordBank;
    private TagBank tagBank;

    public Bank() {
        wordBank = new WordBank();
        tagBank = new TagBank();
    }

    public WordBank getWordBank() {
        return wordBank;
    }

    public TagBank getTagBank() {
        return tagBank;
    }

    public int getWordBankSize() {
        return wordBank.getSize();
    }

    public int getTagBankSize() {
        return tagBank.getSize();
    }

    public void addWord(Word word) throws WordAlreadyExistException {
        wordBank.addWord(word);
        tagBank.addWordToAllTags(word);
    }

    /**
     * Adds a list of tags to a word in WordBank and adds the word to all tags.
     * @param wordDescription word to be added tag
     * @param tags list of tags to add
     * @return all tags of the word after adding to show to user
     * @throws NoWordFoundException if the word doesn't exist in the WordBank
     */
    public HashSet<String> addWordToSomeTags(String wordDescription, ArrayList<String> tags) throws NoWordFoundException {
        HashSet<String> tagsOfWord = wordBank.addWordToSomeTags(wordDescription, tags);
        tagBank.addWordToSomeTags(wordDescription, tags);
        return tagsOfWord;
    }

    /**
     * Deletes a word from the WordBank and from all tags in TagBank.
     * @param deletedWord word to be deleted
     * @return object Word containing the word to be deleted and its meaning
     * @throws NoWordFoundException if the word doesn't exist in the bank
     */
    public Word getAndDelete(String deletedWord) throws NoWordFoundException {
        Word word = wordBank.getAndDelete(deletedWord);
        tagBank.deleteWordAllTags(word);
        return word;
    }


    public void deleteTags(String deletedWord, ArrayList<String> tags,
                           ArrayList<String> deletedTags, ArrayList<String> nullTags) {
        wordBank.deleteTags(deletedWord, tags, deletedTags, nullTags);
        tagBank.deleteWordSomeTags(deletedTags, deletedWord);
    }

    public Word getAndEditMeaning(String editedWord, String newMeaning) throws NoWordFoundException {
        return wordBank.getAndEditMeaning(editedWord, newMeaning);
    }

    public ArrayList<String> searchWordWithBegin(String begin) throws NoWordFoundException {
        return wordBank.searchWordWithBegin(begin);
    }

    public String searchForMeaning(String searchTerm) throws NoWordFoundException {
        return wordBank.searchForMeaning(searchTerm);
    }

    public void increaseSearchCount(String searchTerm) throws NoWordFoundException {
        wordBank.increaseSearchCount(searchTerm);
    }

    public ArrayList<String> getClosedWords(String searchTerm) {
        return wordBank.getClosedWords(searchTerm);
    }

    public void addTagToWord(String word, String tag) {
        wordBank.addTagToWord(word, tag);
        tagBank.addWordToOneTag(word, tag);
    }
}
