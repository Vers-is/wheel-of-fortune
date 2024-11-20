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
        System.out.print("\033[96m| \033[1mRULES<<<<<<<<<<<<<<<<<<<<\033[0m\n\033[94m| Max score: 600 points\n| 1 letter = 10 points\n| 1 word = 50 points\n| 10 words to guess\n+-------------------------\n\033[0m| Enter anything if got it: ");
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

        hints.put("CAT", "Pet with whiskers\n| and fluffy ears.");
        hints.put("BREAD", "A baked staple food made\n| from flour and water.");
        hints.put("RABBIT", "A small mammal with long ears\n| and strong hind legs.");
        hints.put("MOTHER", "A female parent.");
        hints.put("HAIR", "Grow from the skin,\n| mainly on the head.");
        hints.put("JACKET", "Cloth worn for warmth.");
        hints.put("EYE", "The organ for vision.");
        hints.put("LIGHTENING", "A bright electrical\n| discharge seen during storms.");
        hints.put("LEG", "A part of body used\n| for standing and walking.");
        hints.put("INFINITY", "A concept representing\n| endlessness.");
        hints.put("LIMIT", "A boundary or point beyond which\n| something does not extend.");
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
        String message = playerMessages.getOrDefault(name, "No recent messages.");
        System.out.println("\u001B[33m>>> " + name + "'s score: " + score + "\u001B[0m");
    }

    public static boolean allLettersGuessed(String word, ArrayList<Character> guessedLetters) {
        for (char c : word.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false; // ???? ???? ?? ???? ????? ?? ???????
            }
        }
        return true;
    }

    public static void playerGuess(ArrayList<String> players, Scanner scanner) {
        int playerIndex = 0;
        ArrayList<String> wordList = saveWordsList();
        ArrayList<Character> guessedLetters = new ArrayList<>();
        boolean gameEnded = false;
    
        for (String player : players) {
            scores.put(player, 0);
            playerMessages.put(player, "");
        }
    
        while (!gameEnded) {
            for (String currentWord : wordList) {
                guessedLetters.clear();
                boolean wordCompleted = false;
    
                while (!wordCompleted) {
                    cleanConsole();
                    String currentPlayer = players.get(playerIndex);
                    printWordHint(currentWord, guessedLetters);
                    checkAnswer(currentPlayer);
    
                    String message = playerMessages.get(currentPlayer);
                    if (!message.isEmpty()) {
                        System.out.println("\u001B[36m>>> " + message + "\u001B[0m");
                        playerMessages.put(currentPlayer, ""); 
                    }
    
                    System.out.print("\u001B[32m" + currentPlayer + "'s turn! Guess: \u001B[0m");
                    String guess = scanner.nextLine().trim().toUpperCase();
    
                    if (guess.isEmpty() || (!guess.matches("[A-Z]+") && !guess.equalsIgnoreCase(currentWord))) {
                        System.out.println("\u001B[31mInvalid input!\u001B[0m");
                        playerMessages.put(currentPlayer, "Invalid input.");
                        continue;
                    }
    
                    boolean correct = false;
                    if (guess.length() == 1) {
                        char guessedChar = guess.charAt(0);
                        if (guessedLetters.contains(guessedChar)) {
                            playerMessages.put(currentPlayer, "Repeated letter.");
                        } else if (currentWord.indexOf(guessedChar) >= 0) {
                            guessedLetters.add(guessedChar);
                            scores.put(currentPlayer, scores.get(currentPlayer) + 10);
                            playerMessages.put(currentPlayer, "Correct! You earned the turn.");
                            correct = true;
    
                            if (allLettersGuessed(currentWord, guessedLetters)) {
                                scores.put(currentPlayer, scores.get(currentPlayer) + 50);
                                cleanConsole();
                                System.out.println("\u001B[33mCongrats! \"" + currentWord + "\" has been guessed!\u001B[0m");
                                playerMessages.put(currentPlayer, "Next word!");
                                wordCompleted = true;
                            }
                        } else {
                            playerMessages.put(currentPlayer, "Wrong.");
                        }
                    } else if (guess.equals(currentWord)) {
                        scores.put(currentPlayer, scores.get(currentPlayer) + 50);
                        cleanConsole();
                        System.out.println("\u001B[33mCongrats! " + currentPlayer + " guessed the word \"" + currentWord + "\"!\u001B[0m");
                        playerMessages.put(currentPlayer, "Next word!");
                        wordCompleted = true;
                    } else {
                        playerMessages.put(currentPlayer, "Wrong.");
                    }
    
                    if (!correct && !wordCompleted) {
                        playerIndex = (playerIndex + 1) % players.size();
                    }
                }
    
                System.out.print("\u001B[34mDo you want to continue to the next word? (yes/no): \u001B[0m");
                String continueGame = scanner.nextLine().trim().toLowerCase();
                if (continueGame.equals("no")) {
                    gameEnded = true;
                    break;
                }
            }
    
            if (!gameEnded) {
                cleanConsole();
                System.out.println("\033[96m| FINAL SCORES:\n+-------------------");
                for (String player : players) {
                    System.out.println("\033[96m| " + player + ":\u001B[0m " + scores.get(player) + " points");
                }
                System.out.println("\u001B[33mAll words have been guessed!\u001B[0m");
                gameEnded = true;
            } else if (gameEnded) {
                cleanConsole();
                System.out.println("\033[96m| FINAL SCORES:\n+------------------");
                for (String player : players) {
                    System.out.println("\033[96m| " + player + ":\u001B[0m " + scores.get(player) + " points");
                    
                }
                
                gameEnded = true;
            }
        }
    }
    
    
}
