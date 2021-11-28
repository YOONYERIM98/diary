package Test;


import javax.swing.JFrame;

import Frame.GUI;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class MainFrame {

    public static GUI gui;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        gui = new GUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

 
}