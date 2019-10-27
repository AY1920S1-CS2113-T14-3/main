package dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;

public class TagBank {
    private TreeMap<String, HashSet<String>> tagBank;

    public TagBank() {
        tagBank = new TreeMap<>();
    }

    /**
     * Adds a word to some tags.
     * @param wordDescription word that need to be added
     * @param tags list of tags that will add the word
     */
    public void addWordToSomeTags(String wordDescription, ArrayList<String> tags) {
        for (String tag : tags) {
            if (tagBank.containsKey(tag)) {
                tagBank.get(tag).add(wordDescription);
            } else {
                tagBank.put(tag, new HashSet<>(Collections.singletonList(wordDescription)));
            }
        }
    }

    /**
     * Deletes a word from all tags of that word.
     * @param word word to be deleted
     */
    public void deleteWordAllTags(Word word) {
        for (String tag : word.getTags()) {
            tagBank.get(tag).remove(word.getWord());
        }
    }

    /**
     * Deletes a word from some tags of that word.
     * @param deletedTags list of tags to delete the word
     * @param deletedWord word to be deleted
     */
    public void deleteWordSomeTags(ArrayList<String> deletedTags, String deletedWord) {
        for (String tag : deletedTags) {
            tagBank.get(tag).remove(deletedWord);
        }
    }

    /**
     * Adds a word to all tags in TagBank of that the word has.
     * @param word word to add tag
     */
    public void addWordToAllTags(Word word) {
        for (String tag : word.getTags()) {
            if (tagBank.containsKey(tag)) {
                tagBank.get(tag).add(word.getWord());
            } else {
                tagBank.put(tag, new HashSet<>(Collections.singletonList(word.getWord())));
            }
        }
    }
    public String[] getAllTagsAsList() {
        return tagBank.keySet().toArray(new String[tagBank.size()]);
    }

    /**
     * Gets all words of a specific tag as an array.
     * @param tag a string represents the tag
     * @return an array of words of that tag
     */
    public String[] getAllWordsOfTag(String tag) {
        HashSet<String> allWords = tagBank.get(tag);
        return allWords.toArray(new String[allWords.size()]);
    }

    public void addWordToOneTag(String word, String tag) {
        if (tagBank.containsKey(tag)) {
            tagBank.get(tag).add(word);
        } else {
            tagBank.put(tag, new HashSet<>(Collections.singletonList(word)));
        }
    }
}
