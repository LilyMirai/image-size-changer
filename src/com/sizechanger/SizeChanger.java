package com.sizechanger;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;

public class SizeChanger {

    static int resizedWidth = 0;
    static int resizedHeight = 0;
    static float floatResizedWidth = 0;
    static float floatResizedHeight = 0;
    static float sizeRatio = 0;
    static String pieceName = "";
    static String stringDirectory = "";
    static String outputName = "";
    static String sizeArg = "";
    static String inputName = "";
    static String finalOutputName = "";
    static int folderCounter = 0;
    static int fileCounter = 0;
    static String argument2 = "";
    static String defaultOutputName = "result";
    static String arg4 = "";

    /* Arguments: -sizeRatio -outputName -inputName -pieceName */
    public static void main(String[] args) {


        if (args.length >= 1) {
            if (args[0].isEmpty()) {
                sizeRatio = 0.5f;

            }
            else {
                sizeArg = args[0];
                System.out.println("Size ratio: " + sizeArg);
                sizeRatio = Float.parseFloat(sizeArg);
            }
        } else {
            System.out.println("Size ratio argument is not present, using 0.5");
            sizeRatio = 0.5f;
        }
        if (args.length >= 2) {
            if (args[1].isEmpty()) {
                argument2 = defaultOutputName;
                outputName = defaultOutputName + ".png";
            }
            else {
                outputName = args[1] + ".png";
                argument2 = args[1];

            }
        } else {
            argument2 = defaultOutputName;
            outputName = defaultOutputName + ".png";
        }
        if (args.length >= 3) {
            if (args[2].isEmpty()) {
                System.out.println("Input file argument not present, using 'image.png' instead.");
                inputName = "image.png";

            }
            else {
                inputName = args[2] + ".png";
                System.out.println("Input file: " + inputName);
            }
        } else {
            System.out.println("Input file argument not present, using 'image.png' instead.");
            inputName = "image.png";
        }
        if (args.length >= 4) {
            if (args[3].isEmpty()
            ) {
                System.out.println("Piece name argument is not present, returning at root.");
                pieceName = null;

            }
            else {
                arg4 = args[3];
                pieceName = args[3];
                System.out.println("Piece name: " + pieceName);
            }
        } else {
            System.out.println("Piece name argument is not present, returning at root.");
            pieceName = null;
        }

        try {
            BufferedImage originalImage = ImageIO.read(new File("image.png"));
            float originalWidth = originalImage.getWidth();
            float originalHeight = originalImage.getHeight();

            floatResizedWidth = originalWidth * sizeRatio;
            floatResizedHeight = originalHeight * sizeRatio;
            resizedWidth = (int) floatResizedWidth;
            resizedHeight = (int) floatResizedHeight;

            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImagePng = resizeImage(originalImage, type);

            if (pieceName != null) {
                File directory = new File(pieceName + "/");
                doesFolderExist(directory);
            } else {
                doesFileExist(outputName);
            }
            ImageIO.write(resizeImagePng, "png", new File(finalOutputName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, resizedWidth, resizedHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    public static void doesFolderExist(File directory) throws IOException {
        if (directory.exists()) {
            System.out.println("Directory already exists, creating with next avaliable name instead.");
            folderCounter++;
            pieceName = arg4 + "(" + folderCounter + ")";
            directory = new File(pieceName + "/");
            doesFolderExist(directory);
        } else {
            Files.createDirectory(directory.toPath());
            finalOutputName = pieceName + "/" + outputName;
        }
    }

    public static void doesFileExist(String outputName) {
        File output = new File(outputName);
        if (output.exists()) {
            fileCounter++;
            outputName = argument2 + " (" + fileCounter + ")" + ".png";
            doesFileExist(outputName);
            System.out.println("File already exists, creating next with avaliable name instead.");
        }
        else {
            finalOutputName = outputName;
        }
    }

}
