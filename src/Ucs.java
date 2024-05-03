package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Ucs {
    public Dictionary dictionary;
    public String startWord;
    public String endWord;
    public ArrayList<Word> queue;
    public LinkedList<String> result;
    public ArrayList<String> visited;
    public HashMap<String,ArrayList<String>> graph;

    public Ucs(String startWord, String endWord, Dictionary dictionary) {
        this.dictionary = dictionary;
        this.startWord = startWord.toLowerCase();
        this.endWord = endWord.toLowerCase();
        this.queue = new ArrayList<>();
        this.result = new LinkedList<>();
        this.visited = new ArrayList<>();
        this.graph =  new HashMap<>();
    }

    public void solve(){
        if (!dictionary.contains(startWord) || !dictionary.contains(endWord)){
            System.out.println("Your word is not on dictionary!\n");
            return;
        }
        if (startWord.equals(endWord)){
            System.out.println("This is way too easy...\n");
            return;
        }

        /**
         * Populate graph.
         */
        for (String word : this.dictionary.words)
        {
            ArrayList<String> adjacentWords = new ArrayList<String>();

            for (String word2 : this.dictionary.words)
            {
                if (isValidTransition(word, word2))
                {
                    adjacentWords.add(word2);
                }
                graph.put(word, adjacentWords);
            }

        }

        // visited
        queue.add(new Word(startWord));
        int iteration = 0;

        while (!queue.isEmpty()) {
            System.out.printf("Iteration %d\n", iteration);
            for (Word w : queue){
                System.out.printf("%s ", w.getWord());
                w.printPathToWord();
            }
            System.out.println();
            System.out.println();
            // sort here
            Word currentWord = expandWord();
            visited.add(currentWord.getWord());
            iteration++; 

            // currentWord.printPathToWord();

            if (currentWord.getWord().equals(endWord)){
                result = currentWord.path;
                result.add(endWord);
                return;
            }
            for (String neighbor : graph.get(currentWord.getWord())){
                if (visited.contains(neighbor)){
                    continue;
                }
                else{
                    queue.add(new Word(neighbor, currentWord, endWord));
                }
            }

        }
    }

    public boolean isValidTransition(String currentWord, String word){
        int offset = 0;
        int len = currentWord.length();
        for (int i = 0; i < len; i++){
            if (currentWord.charAt(i) != word.charAt(i)){
                offset ++;
            }
            if (offset > 1){return false;}
        }
        return true;
    }

    public void printResult(){
        int len = result.size();
        if (len > 0){
            for (int i = 0; i < len-1 ;i++){
                System.out.printf("%s -> ", result.get(i));
            }
            System.out.println(result.get(len-1));
        }
        else{
            System.out.println("\nNo result found!");
        }
    }

    public Word expandWord(){
        int size = queue.size();
        Word selectedWord = queue.get(0);
        int min = selectedWord.getCost();
        int idx = 0;
        for (int i = 1; i < size; i++){
            if (queue.get(i).getCost() < min){
                selectedWord = queue.get(i);
                min = selectedWord.getCost();
                idx = i;
            }
        }
        queue.remove(idx);
        return selectedWord;
    }
}
