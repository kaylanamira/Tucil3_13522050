package src;

import java.util.LinkedList;

public class Word {
    protected String word;
    protected int heuristic;
    protected int cost;
    protected LinkedList<String> path;
    
    public Word(String word){
        this.path =  new LinkedList<>();
        this.word = word;
        this.cost = 0;
        this.heuristic = 0;
    }

    public Word(String word, Word parentWord, String endword){
        this.path = new LinkedList<>();
        for (String w : parentWord.path){
            this.path.add(w);
        }
        this.path.add(parentWord.word);
        this.word = word;
        this.cost = parentWord.getCost() + 1;

        // heuristic calculation
        int offset = 0;
        int len = word.length();
        for (int i = 0; i < len; i++){
            if (word.charAt(i) != endword.charAt(i)){
                offset ++;
            }
        }
        this.heuristic = offset;
    }

    public String getWord(){
        return this.word;
    }
    
    public int getHeuristic(){
        return this.heuristic;
    }

    public int getCost(){
        return this.cost;
    }

    public LinkedList<String> getPath(){
        return this.path;
    }
    
    public void setHeuristic(int h){
        this.heuristic = h;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public void printPathToWord(){
        System.out.printf("h(n): %d ,", this.heuristic);
        System.out.printf("g(n): %d ,", this.cost);
        System.out.printf("Path to %s: ", this.word);
        for (String p : path){
            System.out.printf("%s -> ", p);
        }
        System.out.println(this.word);
        System.out.println();
    }
}
