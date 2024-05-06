package src;


public class Ucs extends Solver {

    public Ucs(String startWord, String endWord, Dictionary dictionary) {
        super(startWord, endWord, dictionary);
    }

    public void solve(){
        // nNo word in dict
        if (!dictionary.contains(startWord) || !dictionary.contains(endWord)){
            return;
        }
        // same input for start word and endword
        if (startWord.equals(endWord)){
            return;
        }

        queue.add(new Word(startWord)); 

        while (!queue.isEmpty()) {
            
            Word currentWord = queue.poll(); //dequeue the first Word in queue
            visited.add(currentWord.getWord()); // label current word as visited
            visitedWords++; 

            // path is found
            if (currentWord.getWord().equals(endWord)){
                generateEndPath(currentWord);
                return;
            }

            // generate neighbors (words in dict with 1 offset from current word)
            char[] characters = currentWord.getWord().toCharArray();
            for (int j = 0; j < characters.length; ++j) {
                char originalChar = characters[j];
                for (char ch = 'a'; ch <= 'z'; ++ch) {
                    characters[j] = ch;
                    String newWord = new String(characters);
                    // Skip if new word is not in the word set or visited
                    if (!dictionary.contains(newWord) || visited.contains(newWord)) {
                        continue;
                    }
                    // Add new word to the queue
                    queue.add(new Word(newWord, currentWord, calculateCost(currentWord)));
                }
                characters[j] = originalChar;
            }
        }
    }
}
