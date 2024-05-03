package src;
import java.util.*;

class GBFSSolver{
    public Dictionary dictionary;
    public String startWord;
    public String endWord;
    public Queue<String> queue;
    public ArrayList<String> result;
    public HashMap<String, ArrayList<String>> visited;
    //public HashMap<String,ArrayList<String>> graph = new HashMap<>();

    public GBFSSolver(String startWord, String endWord, Dictionary dictionary) {
        this.dictionary = dictionary;
        this.startWord = startWord.toLowerCase();
        this.endWord = endWord.toLowerCase();
        this.queue = new LinkedList<>();
        this.result = new ArrayList<>();
        this.visited = new HashMap<>();
    }

    // for all word in dict, select word that only has 1 offsets from current word
    public ArrayList<String> getNeighbors(String currentWord){
        long startTime = System.nanoTime();

        ArrayList<String> adjacentWords = new ArrayList<String>();
        for (String word : this.dictionary.words){
            if (visited.containsKey(word)){continue;}
            int difference = 0;
            for (int i = 0; i < dictionary.wordLength; i++){
                if (currentWord.charAt(i) != word.charAt(i)){
                    difference ++;
                }
                if (difference > 1){break;}
            }
            if (difference == 1){
                adjacentWords.add(word);
                queue.add(word);
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        long seconds = (duration / 1000) % 60;
        
        // for (String neighbor : adjacentWords){
        //     System.out.println(neighbor);
        // }
        // String formatedSeconds = String.format("(0.%d seconds)", seconds);
        // System.out.println("Loop through dict to find word with 1 offset = "+ formatedSeconds);
        return adjacentWords;
    }

    // for every word in dict, 

    // heuristic: the num of character changes to reach endword
    // gbfs: 

    public void solve(){
        if (!dictionary.contains(startWord) || !dictionary.contains(endWord)){
            System.out.println("Your word is not on dictionary!\n");
            return;
        }
        if (startWord.equals(endWord)){
            System.out.println("This is way too easy...\n");
            return;
        }
        // visited
        queue.add(startWord);
        int level = 0;

        while (!queue.isEmpty()) {
            String currentWord = queue.poll();
            visited.put(currentWord,new ArrayList<>());
            if (currentWord.equals(endWord)){
                result.add(endWord);
                System.out.println("Visited: ");
                for (String key : visited.keySet()){
                    System.out.printf("Key : %s\n", key);
                    System.out.printf("Path : %s\n", visited.get(key));
                }
                return;
            }
            ArrayList<String> neighbors = getNeighbors(currentWord);
        }
    }

}