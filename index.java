import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
public class index {   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
            
        hints = saveWords();
        ArrayList<String> names = saveNames(scanner);
        playerGuess(names, scanner);
    }

    // field
    static HashMap<String, String> hints;
    static String keyToPrint;
    
    public static void cleanConsole() { 
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }
    
    public static ArrayList<String> saveNames(Scanner scanner) { ///// players names
            cleanConsole();
           
            System.out.println("\u001B[31mWrite down your names\u001B[0m \u001B[90m(\"done\" to finish):\u001B[0m");
    
            ArrayList<String> names = new ArrayList<>();
            
                while(true) {
                    String toScan = scanner.nextLine().trim().toLowerCase();
                    String firstLetter = toScan.substring(0, 1).toUpperCase();
                    String lastLetters = toScan.substring(1);
                    String name = firstLetter + lastLetters; 
                    
                    if (!names.contains(name)){
                        if(!name.equals("Done")) names.add(name);
                        else break;
                    }
                    
                } 
                Collections.shuffle(names); // RANDOM SET
                
            return names;
        }
                
    public static HashMap<String, String> saveWords() { // LIST OF WORDS
        HashMap<String, String> hints = new HashMap<>();
    
        hints.put("CAT", "Pet with whiskers\n" + 
                        "| and fluffy ears.");

        hints.put("BREAD", "A baked staple food made\n" + 
                        "| from flour and water.");

        hints.put("RABBIT", "A small mammal with long ears\n" + 
                        "| and strong hind legs.");

        hints.put("MOTHER", "A female parent.");

        hints.put("HAIR", "Grow from the skin,\n" + 
                        "| mainly on the head.");

        hints.put("JACKET", "Cloth worn for warmth.");

        hints.put("EYE", "The organ for vision.");

        hints.put("LIGHTENING", "A bright electrical\n" + 
                        "| discharge seen during storms.");

        hints.put("LEG", "A part of body used\n" + 
                        "| for standing and walking.");

        hints.put("INFINITY", "A concept representing\n" + 
                        "| endlessness.");

        hints.put("LIMIT", "A boundary or point beyond which\n" + 
                        "| something does not extend.");
        return hints;
    }

    public static ArrayList<String> saveWordsList() {
        ArrayList<String> wordList = new ArrayList<>();
        
        wordList.add("CAT");
        wordList.add("BREAD");
        wordList.add("RABBIT");
        wordList.add("MOTHER");
        wordList.add("HAIR");
        wordList.add("JACKET");
        wordList.add("EYE");
        wordList.add("LIGHTENING");
        wordList.add("LEG");
        wordList.add("INFINITY");
        wordList.add("LIMIT");
        
        Collections.shuffle(wordList); 
        
        return wordList;
    }

    public static String hideWord(String word) {
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            hiddenWord.append("[ ]");
        }
        return hiddenWord.toString(); 
    }
    
    
    public static void printWordHint(HashMap<String, String> hints, String keyToPrint) { // PRINT BOARD
        String separator = "+-------------------------";
        String colorStart = "\u001B[31m";
        String colorEnd = "\u001B[0m";
        System.out.println(colorStart + separator);
        String value = hints.get(keyToPrint);
        String hiddenWord = hideWord(keyToPrint); 
        System.out.println("| "+ hiddenWord + "\n| " + value);
        System.out.println(separator + colorEnd);
    }
    
    public static void checkAnswer(String name){ // EDIT
        int letter = 20;
        int word = 60;
        int score = 0;
    
        System.out.println("\u001B[33m>>> " + name + "'s score: " + score + "\u001B[0m");
    }
    
    public static void playerGuess(ArrayList<String> players, Scanner scanner) { // PLAYER GUESS
        int playerIndex = 0; 
        ArrayList<String> wordList = saveWordsList(); 
        int wordIndex = 0;
    
        while (true) {
            if (wordIndex >= wordList.size()) {
                System.out.println("All words have been guessed!");
                break;  
            }
            cleanConsole(); // CLEAN
            String currentPlayer = players.get(playerIndex);
            String currentWord = wordList.get(wordIndex);
            printWordHint(hints, currentWord); // (EDIT) BOARD
            checkAnswer(currentPlayer); // SCORE
            System.out.print("\u001B[32m" + currentPlayer + "'s\u001B[0m" + " turn! Guess a letter: "); //ASK LETTER
            String letter = scanner.nextLine().trim().toUpperCase();
            playerIndex = (playerIndex + 1) % players.size();  
        }
    }
}
