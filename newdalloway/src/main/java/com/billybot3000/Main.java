package com.billybot3000;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
        sentenceDetection();
        nameAnalysis();
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

    private static String[] tokenize() {
        TokenizerME tokenizer = null;
        try {
            FileInputStream file = new FileInputStream("newdalloway/src/main/java/com/billybot3000/models/opennlp-en-ud-ewt-tokens-1.2-2.5.0.bin");
            tokenizer = new TokenizerME(new TokenizerModel(file));
            file.close();
        } catch(IOException e) {
            System.out.println("error lmao 3");
            System.exit(1);
        }
        return tokenizer.tokenize(getText("newdalloway/src/main/java/com/billybot3000/dalloway.txt"));
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
            System.out.println("error lmao 2");
            System.exit(1);
        }
    }
    
    private static void nameAnalysis() {
        String[] tokens = tokenize();
        
        // name map
        ArrayList<Integer> clarissa = new ArrayList<Integer>();
        ArrayList<Integer> peter = new ArrayList<Integer>();
        ArrayList<Integer> septimus = new ArrayList<Integer>();
        ArrayList<Integer> lucrezia = new ArrayList<Integer>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("Clarissa") || tokens[i].equals("Mrs. Dalloway")) {
                clarissa.add(i);
            } else if (tokens[i].equals("Peter") || tokens[i].equals("Walsh")) {
                peter.add(i);
            } else if (tokens[i].equals("Septimus")) {
                septimus.add(i);
            } else if (tokens[i].equals("Lucrezia") || tokens[i].equals("Rezia")) {
                lucrezia.add(i);
            }
        }
        // write data to csv
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("newdalloway/src/main/java/com/billybot3000/output/characters.csv"));
            bw.write("Clarissa, ");
            for (int i = 0; i < clarissa.size(); i++) {
                bw.write(clarissa.get(i) + ",");
            }
            bw.write("\nPeter, ");
            for (int i = 0; i < peter.size(); i++) {
                bw.write(peter.get(i) + ",");
            }
            bw.write("\nSeptimus, ");
            for (int i = 0; i < septimus.size(); i++) {
                bw.write(septimus.get(i) + ",");
            }
            bw.write("\nLucrezia, ");
            for (int i = 0; i < lucrezia.size(); i++) {
                bw.write(lucrezia.get(i) + ",");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("error lmao 2");
            System.exit(1);
        }
    }
    
}