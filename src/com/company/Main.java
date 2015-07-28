package com.company;

import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);
    static String input;
    static int[] hand = new int[5];
    static int points = 0;
    static int handsLeft = 13;
    static int rollsLeft = 2;

    static String[] upper = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes"};
    static String[] lower = {"Three of a Kind", "Four of a Kind", "Full House", "Small Straight", "Large Straight", "Chance", "Yahtzee"};

    static double[] upperOdds = new double[6];
    static double[] lowerOdds = new double[7];

    public static void main(String[] args) {
        System.out.println("Welcome to the YahtzeeBot");
        while (true) {
            rollFresh();
            calculateOdds();
//            lowerCalc(2);
        }
    }

    public static void rollFresh() {
        System.out.println("Please enter your hand");
        input = scan.nextLine();
        if (input.length() != 5) {
            System.out.println("Too many or too few characters were entered");
            rollFresh();
            return;
        }
        for (int i = 0; i <= 4; i++) {
            hand[i] = input.charAt(i) - '0';
            if (hand[i] > 6 || hand[i] < 1) {
                System.out.println("What the hell kind of dice are you using?");
                rollFresh();
                break;
            }
        }
    }

    public static void calculateOdds() {
        for (int i = 0; i < 6; i++) {   //Iterates through the upper level of Yahtzee
            if (upper[i] != "") {
                upperCalc(i + 1);
            }
        }
        for (int i = 0; i < 7; i++) {
            lowerCalc(i);
        }
    }

    public static void upperCalc(int num) {
        int non = 0;    //number of numbers
        for (int i = 0; i < 5; i++) if (hand[i] == num) non++;  //Iterates through entire hand
        upperOdds[num - 1] = Math.round(5 / Math.pow(6, 5 - non) * 10000.0) / 100.0;
        if (rollsLeft == 2) {
            upperOdds[num - 1] *= 2;
            if (non <= 2)
                upperOdds[num - 1] += Math.round(125 / Math.pow(6, 7 - non) * 10000.0) / 100.0;   //A switch statement could clear this up
            if (non <= 1) upperOdds[num - 1] += Math.round(625 / Math.pow(6, 8 - non) * 10000.0) / 100.0;
            if (non == 0) upperOdds[num - 1] += Math.round(3125 / Math.pow(6, 9 - non) * 10000.0) / 100.0;
        }
        if (non >= 4) upperOdds[num - 1] = 100.00;
        System.out.println(num + " = " + upperOdds[num - 1] + "% chance");
    }

    public static void lowerCalc(int num) {
        switch (num) {
            case 0:
                //3 of a kind odds
                int non = 0;    //num of nums
                int[] roll3 = new int[6];
                for (int i = 0; i < 6; i++) roll3[i] = 0;   //Searches through the hand to find the most common die
                for (int i = 0; i < 5; i++) roll3[hand[i] - 1]++;
                for (int i = 0; i < 6; i++) if (roll3[i] > non) non = roll3[i];
                lowerOdds[0] = Math.round(25 / Math.pow(6, 5 - non) * 10000.0) / 100.0;
                if (rollsLeft == 2) {
                    lowerOdds[0] *= 2;
                    if (non == 1) lowerOdds[0] += Math.round(3125 / Math.pow(6, 7) * 10000.0) / 100.0;
                }
                if (non >= 3) lowerOdds[0] = 100.00;
                System.out.println(lower[num] + " = " + lowerOdds[num] + "% chance");
                break;
            case 1:
                //4 of a kind odds
                int non1 = 0;   //num of nums
                int[] roll1 = new int[6];
                for (int i = 0; i < 6; i++) roll1[i] = 0;
                for (int i = 0; i < 5; i++) roll1[hand[i] - 1]++;
                for (int i = 0; i < 6; i++) if (roll1[i] > non1) non1 = roll1[i];
                lowerOdds[1] = Math.round(5 / Math.pow(6, 5 - non1) * 10000.0) / 100.0;
                if (rollsLeft == 2) {
                    lowerOdds[1] *= 2;
                    if (non1 <= 2)
                        lowerOdds[1] += Math.round(125 / Math.pow(6, 7 - non1) * 10000.0) / 100.0;   //A switch statement could clear this up
                    if (non1 == 1) lowerOdds[1] += Math.round(625 / Math.pow(6, 8 - non1) * 10000.0) / 100.0;
                }
                if (non1 >= 4) lowerOdds[1] = 100.00;
                System.out.println(lower[num] + " = " + lowerOdds[num] + "% chance");
                break;
            case 2:
                //Full House odds
                int non2 = 0, snon2 = 0, noni = -1;
                int[] roll2 = new int[6];
                for (int i = 0; i < 6; i++) roll2[i] = 0;
                for (int i = 0; i < 5; i++) roll2[hand[i] - 1]++;
                for (int i = 0; i < 6; i++) {
                    if (roll2[i] > non2) {
                        non2 = roll2[i];
                        noni = i;
                    }
                }
                for (int i = 0; i < 6; i++) if (i != noni && roll2[i] > snon2) snon2 = roll2[i];
                if (non2 >= 4) lowerOdds[2] = 00.00;
                switch (non2) {
                    case 3:
                        switch (snon2) {
                            case 2:
                                lowerOdds[2] = 100.00;
                                break;
                            case 1:
                                lowerOdds[2] = Math.round(1.0 / 6.0 * 10000.0) / 100.0;
                                if (rollsLeft == 2) lowerOdds[2] *= 2;
                                break;
                        }
                        break;
                    case 2:
                        switch (snon2) {
                            case 2:
                                lowerOdds[2] = Math.round(1.0 / 3.0 * 10000.0) / 100.0;
                                if (rollsLeft == 2) lowerOdds[2] *= 2;
                                break;
                            case 1:
                                lowerOdds[2] = Math.round(2.0 / 36.0 * 10000.0) / 100.0;
                                if (rollsLeft == 2) {
                                    lowerOdds[2] *= 2;
                                    lowerOdds[2] += Math.round(20.0 / Math.pow(6, 4) * 10000.0) / 100.0;
                                }
                                break;
                        }
                        break;
                    case 1:
                        lowerOdds[2] = Math.round(4.0 / Math.pow(6, 3) * 10000.0) / 100.0;
                        if (rollsLeft == 2) {
                            lowerOdds[2] *= 2;
                            lowerOdds[2] += Math.round(20/ Math.pow(6, 4) * 10000.0) / 100.0;
                            lowerOdds[2] += Math.round(80/ Math.pow(6, 5) * 10000.0) / 100.0;
                            lowerOdds[2] = Math.round(lowerOdds[2] * 100.0) / 100.0;
                        }
                        break;
                }
                System.out.println(lower[num] + " = " + lowerOdds[2] + "% chance");
                break;
            case 3:
                //Small Straight odds
                break;
            case 4:
                //Large Straight odds
                break;
            case 5:
                //Chance = no odds
                break;
            case 6:
                //Yahtzee odds
                break;
        }
    }

    public static void status() {
        System.out.println("You have " + points + " points.");
        System.out.println(handsLeft + " hands are left to be played\n");
        if (handsLeft > 0) {
            System.out.println("From the Upper Row:");
            for (int i = 0; i < 6; i++) {
                if (upper[i] != "") System.out.println(upper[i]);
            }
            System.out.println("\nFrom the Lower Row");
            for (int i = 0; i < 7; i++) {
                if (lower[i] != "") System.out.println(lower[i]);
            }
        }
    }
}