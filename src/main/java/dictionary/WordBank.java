package dictionary;
import command.OxfordCall;
import exception.NoWordFoundException;
import exception.WordAlreadyExistException;
import storage.Storage;

import java.util.*;




public class WordBank {
    private TreeMap<String, Word> wordBank;

    /**
     * Maps the search count (KEY) to an ordered list of words (VALUE) with that search count.
     */
    private TreeMap<Integer, TreeMap<String, Word>> wordCount = new TreeMap<>();

    public WordBank(Storage storage) {
            wordBank = storage.loadFile();
            makeWordCount();
    }

    public WordBank() {
        this.wordBank = new TreeMap<>();
    }

    public TreeMap<String, Word> getWordBank() {
        return wordBank;
    }

    public boolean isEmpty() {
        return wordBank.isEmpty();
    }

    public TreeMap<Integer, TreeMap<String, Word>> getWordCount() {
        return wordCount;
    }

    protected void makeWordCount() {
        for (Map.Entry<String, Word> entry : wordBank.entrySet()) {
            //find key. if exists append to treemap, else create new hashmap entry
            int numberOfSearches = entry.getValue().getNumberOfSearches();
            String wordText = entry.getValue().getWord();
            Word wordWord = entry.getValue();
            if (!wordCount.isEmpty()) {
                if (wordCount.containsKey(numberOfSearches)) {
                    wordCount.get(numberOfSearches).put(wordText, wordWord);
                }
            } else {
                wordCount.put(numberOfSearches, new TreeMap<>());
                wordCount.get(numberOfSearches).put(wordText, wordWord);
            }
        }
    }

    /**
     * Increases the search count for a word.
     * @param searchTerm word that is being searched for by the user
     * @throws NoWordFoundException if word does not exist in the word bank
     */
    public void increaseSearchCount(String searchTerm) throws NoWordFoundException {
        if (wordBank.containsKey(searchTerm)) {
            Word searchedWord = wordBank.get(searchTerm);
            int searchCount = searchedWord.getNumberOfSearches();
            searchedWord.incrementNumberOfSearches();
            wordCount.get(searchCount).remove(searchTerm);
            if (wordCount.get(searchCount).isEmpty()) { //treemap is empty, delete key
                wordCount.remove(searchCount);
            }
            int newSearchCount = searchCount + 1;
            if (wordCount.containsKey(newSearchCount)) {
                wordCount.get(newSearchCount).put(searchTerm, searchedWord); //add directly to existing treemap
            } else {
                wordCount.put(newSearchCount, new TreeMap<>());
                wordCount.get(newSearchCount).put(searchTerm, searchedWord); //create new entry and add word to treemap
            }
        } else {
            throw new NoWordFoundException(searchTerm);
        }
    }

    /**
     * Adds a word to the WordBank.
     * @param word Word object represents the word to be added
     * @throws WordAlreadyExistException if the word has already exists in the WordBank
     */
    public void addWord(Word word) throws WordAlreadyExistException {
        if (wordBank.containsKey(word.getWord())) {
            throw new WordAlreadyExistException(word.getWord());
        }
        this.wordBank.put(word.getWord(), word);
    }

    /**
     * Looks up for meaning of a specific word.
     * @param word word to be searched for its meaning
     * @return a string represents meaning of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank nor Oxford dictionary
     */
    public String searchForMeaning(String word) throws NoWordFoundException {
        word = word.toLowerCase();
        String s = "";
        String meaning = "";
        if (!(wordBank.containsKey(word))) {
            s = "Unable to locate \"" + word + "\" in local dictionary. Looking up Oxford dictionary\n";
            meaning = OxfordCall.onlineSearch(word);
            Word temp = new Word(word, meaning);
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
    public Word getAndEditMeaning(String wordToBeEdited, String newMeaning) throws NoWordFoundException {
        if (wordBank.containsKey(wordToBeEdited)) {
            wordBank.get(wordToBeEdited).editMeaning(newMeaning);
            return wordBank.get(wordToBeEdited);
        } else {
            throw new NoWordFoundException(wordToBeEdited);
        }
    }

    /**
     * Deletes a word with a specific description and return it.
     * @param word string represents a word to be deleted
     * @return the word itself
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public Word getAndDelete(String word) throws NoWordFoundException {
        if (wordBank.containsKey(word)) {
            return wordBank.remove(word);
        } else {
            throw new NoWordFoundException(word);
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
                closedWords.add(w.getWord());
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
