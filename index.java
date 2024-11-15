import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class index {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        hints = saveWords();
        awareRules(scanner);
        ArrayList<String> names = saveNames(scanner);
        cleanConsole();
        playerGuess(names, scanner);
    }

    /////////////////////////////////////////////////////// field
    static HashMap<String, String> hints;
    static String keyToPrint;
    static HashMap<String, Integer> scores = new HashMap<>();
    static HashMap<String, String> playerMessages = new HashMap<>();
    ////////////////////////////////////////////////////////

    public static void cleanConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void awareRules(Scanner scanner) {
        cleanConsole();
        System.out.print("\033[96m| \033[1mRULES<<<<<<<<<<<<<<<<<<<<\033[0m\n\033[94m| Max score: 500 points\n| 1 letter = 10 points\n| 1 word = 50 points\n| 10 words to guess\n+-------------------------\n\033[0m| Enter anything if got it: ");
        String input = scanner.nextLine().trim();
    }

    public static ArrayList<String> saveNames(Scanner scanner) {
        cleanConsole();
        System.out.println("\u001B[36m| ENTER YOUR NAMES\n| d - done:\n+---------------------\u001B[0m");

        ArrayList<String> names = new ArrayList<>();

        while (true) {
            String toScan = scanner.nextLine().trim();
            if (toScan.equalsIgnoreCase("d")) break;
            if (!toScan.isEmpty()) {
                String firstLetter = toScan.substring(0, 1).toUpperCase();
                String lastLetters = toScan.substring(1);
                String name = firstLetter + lastLetters;
                if (!names.contains(name)) {
                    names.add(name);
                }
            }
        }
        Collections.shuffle(names);
        return names;
    }

    public static HashMap<String, String> saveWords() {
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
        ArrayList<String> wordList = new ArrayList<>(hints.keySet());
        Collections.shuffle(wordList);
        return wordList;
    }

    public static String hideWord(String word, ArrayList<Character> guessedLetters) {
        StringBuilder hiddenWord = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (guessedLetters.contains(Character.toUpperCase(c))) {
                hiddenWord.append(c).append(" ");
            } else {
                hiddenWord.append("[ ]");
            }
        }
        return hiddenWord.toString();
    }

    public static void printWordHint(String keyToPrint, ArrayList<Character> guessedLetters) {
        String separator = "+-------------------------";
        String colorStart = "\u001B[31m";
        String colorEnd = "\u001B[0m";
        System.out.println(colorStart + separator);
        String value = hints.get(keyToPrint);
        String hiddenWord = hideWord(keyToPrint, guessedLetters);
        System.out.println("| " + hiddenWord + "\n| " + value);
        System.out.println(separator + colorEnd);
    }

    public static void checkAnswer(String name) {
        int score = scores.get(name);
        System.out.println("\u001B[33m>>> " + name + "'s score: " + score + "\u001B[0m");
    }

    public static void playerGuess(ArrayList<String> players, Scanner scanner) {
        int playerIndex = 0;
        ArrayList<String> wordList = saveWordsList();
        int wordIndex = 0;
        ArrayList<Character> guessedLetters = new ArrayList<>();
    
        for (String player : players) {
            scores.put(player, 0);
            playerMessages.put(player, "");
        }
    
        while (true) {
            if (wordIndex >= wordList.size()) {
                cleanConsole();
                System.out.println("\u001B[35mAll words have been guessed!");
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>\u001B[0m");
                System.out.println("\033[96m| FINAL SCORES:\n+-------------------------");
                for (String player : players) {
                    System.out.println("\033[96m| " + player + ":\u001B[0m " + scores.get(player) + " points");
                }
                break;
            }
            String currentPlayer = players.get(playerIndex);
            String currentWord = wordList.get(wordIndex);
            printWordHint(currentWord, guessedLetters);
    
            checkAnswer(currentPlayer);
    
            boolean wordCompleted = true;
            for (char c : currentWord.toCharArray()) {
                if (!guessedLetters.contains(Character.toUpperCase(c))) {
                    wordCompleted = false;
                    break;
                }
            }
    
            if (wordCompleted) {
                cleanConsole();
                System.out.println("\u001B[32mThe word \"" + currentWord + "\" has been revealed!\u001B[0m");
                scores.put(currentPlayer, scores.get(currentPlayer) + 10);
                wordIndex++;
                guessedLetters.clear();
                continue;
            }
    
            System.out.print("\u001B[32m" + currentPlayer + "'s turn! Guess: ");
            String guess = scanner.nextLine().trim().toUpperCase();
    
            if (guess.isEmpty()) {
                System.out.println("\u001B[31mInvalid input! Please enter a letter or a word.\u001B[0m");
                continue;
            }
    
            String message;
            
            cleanConsole();
            
            if (guess.length() == 1) {
                char guessedChar = guess.charAt(0);
                if (guessedLetters.contains(guessedChar)) {
                    message = "\u001B[31mThis letter has already been guessed!\u001B[0m";
                } else if (currentWord.indexOf(guessedChar) >= 0) {
                    guessedLetters.add(guessedChar);
                    scores.put(currentPlayer, scores.get(currentPlayer) + 10);
                    message = "\u001B[32mCorrect! The letter is in the word.\u001B[0m";
                } else {
                    message = "\u001B[31mIncorrect! The letter is not in the word.\u001B[0m";
                }
            } else if (guess.equals(currentWord)) {
                message = "\u001B[32mCongrats! " + currentPlayer + " guessed the word \"" + currentWord + "\"!\u001B[0m";
                scores.put(currentPlayer, scores.get(currentPlayer) + 50);
                wordIndex++;
                guessedLetters.clear();
            } else {
                message = "\u001B[31mIncorrect! The guess is not the word.\u001B[0m";
            }
    
            System.out.println("\n" + message);
    
            playerIndex = (playerIndex + 1) % players.size();
        }
    }
    
    
}
