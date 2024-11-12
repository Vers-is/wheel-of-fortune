import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
public class index {   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
            
        hints = saveWords();
        ArrayList<String> names = saveNames(scanner);
        startGuessingCycle(names, scanner);
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
    
        hints.put("Cat", "Pet with whiskers and fluffy ears");
        hints.put("Bread", "A baked staple food made\n" + //
                        "| from flour and water.");
        hints.put("Rabbit", "A small mammal with long ears\n" + //
                        "| and strong hind legs.");
        hints.put("Mother", "A female parent");
        hints.put("Hair", "Grow from the skin, mainly on the head.");
        hints.put("Jacket", "Cloth worn for warmth");
        hints.put("Eye", "The organ for vision");
        hints.put("Lightning", "A bright electrical\n" + //
                        "| discharge seen during storms.");
        hints.put("Leg", "A part of body used for standing and walking");
        hints.put("Infinity", "A concept representing endlessness.");
        hints.put("Limit", "A boundary or point beyond which\n" + //
                        "| something does not extend.");
        return hints;
    }
    
    public static void printWordHint(HashMap<String, String> hints, String keyToPrint) { // PRINT BOARD
        String separator = "+-------------------------";
        String colorStart = "\u001B[31m";
        String colorEnd = "\u001B[0m";
        System.out.println(colorStart + separator);
        String value = hints.get(keyToPrint);
        System.out.println("| "+ keyToPrint + "\n| " + value);
        System.out.println(separator + colorEnd);
    }
    
    public static void checkAnswer(String name){
        int letter = 20;
        int word = 60;
        int score = 0;
    
        System.out.println("\u001B[33m>>> " + name + "'s score: " + score + "\u001B[0m");
    }
    
    public static void startGuessingCycle(ArrayList<String> players, Scanner scanner) { // PLAYER GUESS
        int playerIndex = 0; 
    
        while (true) {
            cleanConsole();
            String currentPlayer = players.get(playerIndex);
            printWordHint(hints, "Bread"); // (EDIT) BOARD
            checkAnswer(currentPlayer); // SCORE
            System.out.print("\u001B[32m" + currentPlayer + "'s\u001B[0m" + " turn! Guess a letter: "); //ASK LETTER
            String letter = scanner.nextLine().trim().toLowerCase();
            cleanConsole(); // CLEAN
            playerIndex = (playerIndex + 1) % players.size();  
        }
    }
}
