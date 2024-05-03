package src;

import java.util.*;
import java.io.*;

public class Dictionary {
    protected HashSet<String> words;
    protected int wordLength;
    protected int wordCount = 0;

    public Dictionary(int length) throws FileNotFoundException {
        this.wordLength = length;
        Scanner s = new Scanner(new File("dictionary.txt"));;
        words = new HashSet<String>(); 
        while (s.hasNext()){
            String nextWord = s.next();
            if (nextWord.length() == length){
                words.add(nextWord);
                wordCount++;
            }
        }
        s.close();
    }

    public boolean contains(String word){
        return this.words.contains(word.toLowerCase());
    }

    public void printDictionary(){
        for (String word : words){
            System.out.println(word);
        }
        System.out.printf("\nWord in dictionary: %d\n", wordCount);
    }
}
