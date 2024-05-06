package src;

import java.util.*;

public abstract class Solver {
    public Dictionary dictionary;
    public String startWord;
    public String endWord;
    public int visitedWords;
    public PriorityQueue<Word> queue;
    public List<Word> result;
    public HashSet<String> visited;

    // Constructor
    public Solver(String startWord, String endWord, Dictionary dictionary) {
        this.dictionary = dictionary;
        this.startWord = startWord.toLowerCase();
        this.endWord = endWord.toLowerCase();
        this.visitedWords = 0;
        this.queue = new PriorityQueue<>(new Comparator<Word>() {
            public int compare(Word word1, Word word2){
                return Integer.compare(word1.getCost(), word2.getCost());
            }
        });
        this.result = new ArrayList<>();
        this.visited = new HashSet<>();
    }

    public abstract void solve() ;

    public void printResult(){
        int pathLength = result.size();
        if (pathLength > 0){
            System.out.printf("\nVisited words: %d\n", this.visitedWords);
            System.out.printf("Path length: %d\n", pathLength);
            System.out.println("Path: ");
            for (int i = 0; i < pathLength-1 ;i++){
                System.out.printf("%s -> ", result.get(i).getWord());
            }
            System.out.println(result.get(pathLength-1).getWord());
        }
        else{
            System.out.println("\nNo result found!");
        }
    }

    // generate path from start word to end word  
    public void generateEndPath(Word w){
        Word ancestor = w.getparent();
        this.result.add(w);
        while (ancestor != null){
            this.result.add(0, ancestor);
            ancestor = ancestor.getparent();
        }
    }

    /*calculate heuristic by finding offset to endword */
    public int calculateHeuristic(String word, String endword){
        int offset = 0;
        int len = word.length();
        for (int i = 0; i < len; i++){
            if (word.charAt(i) != endword.charAt(i)){
                offset ++;
            }
        }
        return offset;
    }

    /*calculate cost for children */
    public int calculateCost(Word parentWord){
        return parentWord.getCost() + 1;
    }
}
