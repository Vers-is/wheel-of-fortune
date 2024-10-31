import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class index {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HashMap<String, String> hints = saveWords();
        ArrayList<String> names = saveNames(scanner);

        printWordHint(hints, "Bread");
    }

    // field
    static HashMap<String, String> hints;

    public static void cleanConsole() { 
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }

    public static ArrayList<String> saveNames(Scanner scanner) { ///// players names
        cleanConsole();
       
        System.out.println("\u001B[31mWrite down your names\u001B[0m \u001B[90m(\"done\" to finish):\u001B[0m");

        ArrayList<String> names = new ArrayList<>();
        
        while(true) {
            String name = scanner.nextLine();

            if(!name.equals("done")) {
                names.add(name);
            } else {
                break;
            } 
        } 
        return names;
    }

    public static HashMap<String, String> saveWords() {
        HashMap<String, String> hints = new HashMap<>();

        hints.put("Cat", "Pet with whiskers and fluffy ears");
        hints.put("Bread", "A baked staple food made from flour and water.");
        hints.put("Rabbit", "A small mammal with long ears and strong hind legs.");
        hints.put("Mother", "A female parent");
        hints.put("Hair", "Grow from the skin, mainly on the head.");
        hints.put("Jacket", "Cloth worn for warmth");
        hints.put("Eye", "The organ for vision");
        hints.put("Lightning", "A bright electrical discharge seen during storms.");
        hints.put("Leg", "A part of body used for standing and walking");
        hints.put("Infinity", "A concept representing endlessness.");
        hints.put("Limit", "A boundary or point beyond which something does not extend.");
        return hints;
    }

    // Custom method to create padding spaces
    public static String createPadding(int length) { 
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < length; i++) {
            padding.append(" ");
        }
        return padding.toString();
    }

    // Updated centerText method to use createPadding instead of .repeat()
    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return createPadding(padding) + text;
    }

    // Updated printWordHint method to use centerText for centering output
    public static void printWordHint(HashMap<String, String> hints, String keyToPrint) {
        cleanConsole();
        int terminalWidth = 80; // Adjust this width based on your terminal's width
    
        if (hints.containsKey(keyToPrint)) {
            String separator = "====================================";
            String title = keyToPrint;
            String description = hints.get(keyToPrint);
    
            System.out.println("\u001B[31m" + centerText(separator, terminalWidth));
            System.out.println();
            System.out.println(centerText(title, terminalWidth));
            System.out.println(centerText(description, terminalWidth));
            System.out.println();
            System.out.println(centerText(separator, terminalWidth) + "\u001B[0m" + "\n");
    
        } else {
            System.out.println("Invalid.");
        }
    }
    
}    
