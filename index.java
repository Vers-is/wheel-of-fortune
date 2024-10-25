
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class index {

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    ArrayList<String> names = saveNames(scanner);
    HashMap<String, String> hints = saveWords();

    System.out.println(saveNames(scanner));
    saveWords();
    printWordHint(hints, "cat");

    }

    // field
    static HashMap<String, String> hints;

    public static void cleanConsole(){ 
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }

    public static ArrayList<String> saveNames(Scanner scanner){ ///// players names
        cleanConsole();
       
        System.out.println("\u001B[31mWrite down your names\u001B[0m \u001B[90m(\"done\" to finish):\u001B[0m");

        ArrayList<String> names = new ArrayList<>();
        
        while(true){
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

        hints.put("cat", "meow");
        hints.put("dog", "bark");
        hints.put("bird", "chirik");

        return hints;
    }

    public static void printWordHint(HashMap<String, String> hints, String keyToPrint) {
        if (hints.containsKey(keyToPrint)) {
            System.out.println("Word: " + keyToPrint);
            System.out.println("Des: " + hints.get(keyToPrint));
        } else {
            System.out.println("Invalid.");
        }
    }

}        

    
