package src;

public class Word {
    protected String word;      // word name
    protected int cost;         // cost value
    protected Word parent;      // previous Word
    
    /*Constructor for startWord */
    public Word(String word){
        this.parent =  null;
        this.word = word;
        this.cost = 0;
    }

    /*Constructor for word */
    public Word(String word, Word parentWord, int cost){
        this.word = word;
        this.cost = cost;       
        this.parent = parentWord;
    }

    /* GETTERS */
    public String getWord(){
        return this.word;
    }

    public int getCost(){
        return this.cost;
    }

    public Word getparent(){
        return this.parent;
    }

    // Debugging purpose
    public void printPath(){
        System.out.printf("f(n): %d,", this.cost);
        System.out.printf("Path to %s: ", this.word);
        Word ancestor = parent;
        String path = this.word;
        while (ancestor != null){
            path = ancestor.getWord() + "->" + path;
            ancestor = ancestor.getparent();
        }
        System.out.println(path);
        System.out.println();
    }
}
