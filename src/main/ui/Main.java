package ui;

import java.io.FileNotFoundException;

// Begins the Roll For Shoes Game
public class Main {
    public static void main(String[] args) {
        try {
            RollForShoesGame rfs = new RollForShoesGame();
            rfs.runRollForShoes();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
