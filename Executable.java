import java.util.Scanner;

public class Executable {

    static String arg1 = "";
    static String arg2 = "";
    static String arg3 = "";
    static String arg4 = "";

    /* Arguments: -sizeRatio -outputName -inputName -pieceName */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("LilyMirai's art quick resizer (@LilyMirai_ on Twitter)");
        System.out.println("Arguments for transform, leave blank for default.");
        System.out.println("");
        System.out.println("Size Ratio to transform to: (default is 0.5)");
        arg1 = scanner.nextLine();

        System.out.println("");
        System.out.println("Output name for the file: (default is result)");
        arg2 = scanner.nextLine();

        System.out.println("");
        System.out.println("Input name for the file: (don't add the .png, default is 'image'");
        arg3 = scanner.nextLine();

        System.out.println("");
        System.out.println("Folder to create and store into: (default throws the file at root)");
        arg4 = scanner.nextLine();

        scanner.close();

        SizeChanger.main(new String[] {arg1, arg2, arg3, arg4});

    }

}
