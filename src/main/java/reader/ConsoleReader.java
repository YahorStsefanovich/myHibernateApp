package reader;

import java.util.Scanner;

/**
 * Using to input integer values and strings
 * @author StephanovichYegor
 * @version 1.1
 */
public class ConsoleReader {
    public static Scanner scanner = new Scanner(System.in);

    /**
     * Using to input integer values
     * @param upper Top possible value of generating value
     * @return integer value
     */
    public static int getInteger(int upper){
        while (!scanner.hasNextInt()){
            scanner.next();
        }
        int result = scanner.nextInt();
        if ((result > upper) || (result < 0)){
            return upper;
        }
        return result;
    }

    /**
     * Using to input strings
     * @return string value
     */
    public static String getString(){
        return scanner.nextLine();
    }
}
