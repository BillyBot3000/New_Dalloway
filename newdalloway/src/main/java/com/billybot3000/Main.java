package com.billybot3000;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        sentenceDetection();
    }

    private static String getText(String filename) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File not found lmao");
            System.exit(1);
        }
        String text = "";
        while(scanner.hasNextLine()) {
            text += scanner.nextLine();
        }
        scanner.close();
        return text;
    }

    private static void sentenceDetection() {
        SentenceDetectorME sentenceDetector = null;
        try {
            FileInputStream file = new FileInputStream("newdalloway/src/main/java/com/billybot3000/models/opennlp-en-ud-ewt-sentence-1.2-2.5.0.bin");
            sentenceDetector = new SentenceDetectorME(new SentenceModel(file));
            file.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        // detect sentences
        String[] sentences = sentenceDetector.sentDetect(getText("newdalloway/src/main/java/com/billybot3000/dalloway.txt"));

        // write data to csv
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("newdalloway/src/main/java/com/billybot3000/output/sentenceLength.csv"));
            bw.write("Index,Character Count (including spaces),Word Count\n");
            for (int i = 0; i < sentences.length; i++) {
                bw.write(i + "," + sentences[i].length() + "," + sentences[i].split(" ").length + "\n");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("error lmao");
            System.exit(1);
        }
    }
    
    private static void nameAnalysis() {
        NameFinderME nameFinder = null;
        try {
            FileInputStream file = new FileInputStream("newdalloway/src/main/java/com/billybot3000/models/en-ner-person.bin");
            nameFinder = new NameFinderME(new TokenNameFinderModel(file));
            file.close();
        } catch(IOException e) {
            System.out.println("error lmao");
            System.exit(1);
        }
    }
    
}