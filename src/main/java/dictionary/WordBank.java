package dictionary;

import java.util.ArrayList;
import java.util.SortedMap;

import exception.NoWordFoundException;
import command.OxfordCall;
import exception.WordAlreadyExistsException;
import storage.Storage;

import java.util.HashSet;
import java.util.TreeMap;

public class WordBank extends Bank {
    private TreeMap<String, Word> wordBank;

    public WordBank(Storage storage) {
        wordBank = storage.loadFile();
    }

    public WordBank() {
        this.wordBank = new TreeMap<>();
    }

    public TreeMap<String, Word> getWordBank() {
        return wordBank;
    }

    /**
     * Searched for the Word object containing the word.
     * @param word the word to be found
     * @return a Word object containing the word and its meaning
     * @throws NoWordFoundException when the wordBank does not contain the word
     */
    public Word getWord(String word) throws NoWordFoundException {
        try {
            if (wordBank.containsKey(word)) {
                return wordBank.get(word);
            } else {
                throw new NoWordFoundException(word);
            }
        } catch (NoWordFoundException e) {
            e.showError();
        }
        return null;
    }

    public boolean isEmpty() {
        return wordBank.isEmpty();
    }

    /**
     * Deletes a word with a specific description.
     * @param word string represents a word to be deleted
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public void deleteWord(Word word) throws NoWordFoundException {
        if (wordBank.containsKey(word.getWordString())) {
            wordBank.remove(word.getWordString());
        } else {
            throw new NoWordFoundException(word.getWordString());
        }
    }

    /**
     * Adds a word to the WordBank.
     * @param word Word object represents the word to be added
     * @throws WordAlreadyExistsException if the word has already exists in the WordBank
     */
    public void addWord(Word word) throws WordAlreadyExistsException {
        if (wordBank.containsKey(word.getWordString())) {
            throw new WordAlreadyExistsException(word.getWordString());
        }
        this.wordBank.put(word.getWordString(), word);
    }

    /**
     * Looks up for meaning of a specific word.
     * @param word word to be searched for its meaning
     * @return a string represents meaning of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank nor Oxford dictionary
     */
    public String searchWordMeaning(String word) throws NoWordFoundException {
        word = word.toLowerCase();
        String s = "";
        if (!(wordBank.containsKey(word))) {
            s = "Unable to locate \"" + word + "\" in local dictionary. Looking up Oxford dictionary\n";
            String result = OxfordCall.onlineSearch(word);
            Word temp = new Word(word, result);
            wordBank.put(word, temp);
        }
        return s + wordBank.get(word).getMeaning();
    }

    /**
     * Searches for all words with a few beginning characters.
     * @param word a string represents the beginning substring
     * @return list of words that have that beginning substring
     * @throws NoWordFoundException if no words in the WordBank have that beginning substring
     */
    public ArrayList<String> searchWordWithBegin(String word) throws NoWordFoundException {
        word = word.toLowerCase();
        ArrayList<String> arrayList = new ArrayList<>();
        String upperBoundWord = wordBank.ceilingKey(word);
        if (!upperBoundWord.startsWith(word)) {
            throw new NoWordFoundException(word);
        }
        SortedMap<String, Word> subMap = wordBank.subMap(upperBoundWord, wordBank.lastKey());
        for (String s : subMap.keySet()) {
            if (s.startsWith(word)) {
                arrayList.add(s);
            } else {
                break;
            }
        }
        return arrayList;
    }

    /**
     * Updates the meaning of a specific word.
     * @param wordToBeEdited word whose meaning is updated
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public void editWordMeaning(String wordToBeEdited, String newMeaning) throws NoWordFoundException {
        if (wordBank.containsKey(wordToBeEdited)) {
            wordBank.get(wordToBeEdited).editMeaning(newMeaning);
        } else {
            throw new NoWordFoundException(wordToBeEdited);
        }
    }

    /**
     * Adds a tag to a specific word in word bank.
     * @param wordToBeAddedTag word that the tag is set for
     * @param tags new tags input by user
     * @return tags lists of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public HashSet<String> addWordToSomeTags(String wordToBeAddedTag, ArrayList<String> tags) throws NoWordFoundException {
        if (!wordBank.containsKey(wordToBeAddedTag)) {
            throw new NoWordFoundException(wordToBeAddedTag);
        }
        Word word = wordBank.get(wordToBeAddedTag);
        for (String tag : tags) {
            word.addTag(tag);
        }
        return word.getTags();
    }

    /**
     * Adds synonyms to a specific word in word bank
     * synonymsWords will be added to the wordKey(MAIN WORD)
     */
    public HashSet<String> addSynonym(String wordKey, ArrayList<String> synonymsWords) throws NoWordFoundException {
        if(!wordBank.containsKey(wordKey)){
            throw new NoWordFoundException(wordKey);
        }
        Word word = wordBank.get(wordKey);
        /**For each synonym in the ArrayList, we add it into hashset synonym of wordKey*/
        for(String synoWord : synonymsWords){
            word.addSynonym(synoWord);
        }
        return word.getSynonyms(); //return of HashSet<String> is from Word class
    }

    /**
     * Deletes tags from a word.
     * @param word string represent the word
     * @param tagList list of tags to be deleted
     * @param deletedTags tags to be deleted
     * @param nonExistTags tags that doesn't exist in the word
     */
    public void deleteTags(String word, ArrayList<String> tagList,
                           ArrayList<String> deletedTags, ArrayList<String> nonExistTags) {
        HashSet<String> tags = wordBank.get(word).getTags();
        for (String tag : tagList) {
            if (tags.contains(tag)) {
                tags.remove(tag);
                deletedTags.add(tag);
            } else {
                nonExistTags.add(tag);
            }
        }
    }

    /**
     * Checks spelling when user input a non-existing word.
     * @param word word to be searched
     * @return list of words that is considered to be close from the word user is looking for
     */
    public ArrayList<String> getClosedWords(String word) {
        ArrayList<String> closedWords = new ArrayList<>();
        for (Word w : wordBank.values()) {
            if (w.isClosed(word)) {
                closedWords.add(w.getWordString());
            }
        }
        return closedWords;
    }

    public void addTagToWord(String word, String tag) {
        wordBank.get(word).addTag(tag);
    }

    public Word[] getAllWords() {
        return wordBank.values().toArray(new Word[wordBank.size()]);
    }
}
