/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author conor_000
 */
public class SmartAi implements Ai {

    private ArrayList<Card> hand;
    private String trickSuit;
    private Card[] trick;
    private Boolean iceBroken;
    private int smallestList;
    private int player1HandSize;
    private int player2HandSize;
    private int player3HandSize;
    private boolean[] player1CardProbabilities;
    private boolean[] player2CardProbabilities;
    private boolean[] player3CardProbabilities;
    private double[] player1SuitProbabilities;
    private double[] player2SuitProbabilities;
    private double[] player3SuitProbabilities;

    //values will go from 2-A through suits S D C H
    public SmartAi() {
        player1CardProbabilities = new boolean[52];
        player2CardProbabilities = new boolean[52];
        player3CardProbabilities = new boolean[52];
        player1SuitProbabilities = new double[4];
        player2SuitProbabilities = new double[4];
        player3SuitProbabilities = new double[4];
        Arrays.fill(player1CardProbabilities, Boolean.TRUE);
        Arrays.fill(player2CardProbabilities, Boolean.TRUE);
        Arrays.fill(player3CardProbabilities, Boolean.TRUE);
        player1HandSize = 13;
        player2HandSize = 13;
        player3HandSize = 13;

    }

    /*
    prob of getting point = chance of losing trick * chance of giving point
    chance of losing trick = choosen card > AI card
    chance of getting point = chance of playing heart card + chance of QS
    chance of playing heart = chance of none of suit * chance of having H
    chance of having card = suit cards in hand / suit cards in play
     */
    @Override
    public Card chooseCard() {
        Card bestCard = null;
        int suit = 0;
        double chanceOfLosingTrick = 0.0;
        double chanceOfTakingPoints = 0.0;
        double optimalChance = 0.0;
        double optimalChanceThreshold = 0.45;
        switch (trickSuit) {
            case "S":
                suit = 0;
                break;
            case "D":
                suit = 1;
                break;
            case "C":
                suit = 2;
                break;
            case "H":
                suit = 3;
                break;

        }
//        int[] playersTrick = new int[3];
//        for(int i=0;i<4;i++){
//            if(trick[i]!=null){
//                playersTrick[i] = i;
//            }
//        }
        for (Card card : hand) {
            if (trickSuit.equals("")) {
                double count = 0;
                double countLess = 0;
                for (int i = 0; i < 52; i++) {
                    if (player1CardProbabilities[i]) {
                        count++;
                        int x = 0;
                        if (i < 13) {
                            x = i + 2;
                        }
                        if (i >= 13 && i < 26) {
                            x = i - 13 + 2;
                        }
                        if (i >= 26 && i < 39) {
                            x = i - 2 * 13 + 2;

                        }
                        if (i >= 39 && i < 52) {
                            x = i - 3 * 13 + 2;

                        }

                        if (x < card.getNumberValue()) {
                            countLess++;
                        }

                    }
                }
                if (countLess > 2) {
                    chanceOfLosingTrick = countLess / count;
                }
                switch (card.getSuit()) {
                    case "S":
                        suit = 0;
                        break;
                    case "D":
                        suit = 1;
                        break;
                    case "C":
                        suit = 2;
                        break;
                    case "H":
                        suit = 3;
                        break;

                }

                double chanceOfPlayingH = (1 - player1SuitProbabilities[suit]) * player1SuitProbabilities[3];
                double chanceOfPlayingQS;
                if (!player1CardProbabilities[10]) {
                    if (suit == 0) {
                        if (card.getNumberValue() > 12) {
                            chanceOfPlayingQS = 1;
                        } else {
                            chanceOfPlayingQS = 0;
                        }
                    } else {
                        chanceOfPlayingQS = (1 - player1SuitProbabilities[suit]) * player1SuitProbabilities[0];
                    }

                } else {
                    chanceOfPlayingQS = 0;
                }
                chanceOfTakingPoints = chanceOfLosingTrick * (chanceOfPlayingH + chanceOfPlayingQS);
                if (chanceOfTakingPoints > optimalChance && chanceOfTakingPoints <= optimalChanceThreshold) {
                    optimalChance = chanceOfTakingPoints;
                    bestCard = card;
                }

            } else if (card.getSuit().equals(trickSuit)) {
                double count = 0;
                double countLess = 0;
                for (int i = suit * 13; i < (suit + 1) * 13; i++) {
                    if (player1CardProbabilities[i]) {
                        count++;
                        int x = i - suit * 13 + 2;
                        if (x < card.getNumberValue()) {
                            countLess++;
                        }

                    }
                }
                if (countLess > 2) {
                    chanceOfLosingTrick = countLess / count;
                }
                double chanceOfPlayingH = (1 - player1SuitProbabilities[suit]) * player1SuitProbabilities[3];
                double chanceOfPlayingQS;
                if (!player1CardProbabilities[10]) {
                    if (suit == 0) {
                        if (card.getNumberValue() > 12) {
                            chanceOfPlayingQS = 1;
                        } else {
                            chanceOfPlayingQS = 0;
                        }
                    } else {
                        chanceOfPlayingQS = (1 - player1SuitProbabilities[suit]) * player1SuitProbabilities[0];
                    }

                } else {
                    chanceOfPlayingQS = 0;
                }
                chanceOfTakingPoints = chanceOfLosingTrick * (chanceOfPlayingH + chanceOfPlayingQS);
                if (chanceOfTakingPoints > optimalChance && chanceOfTakingPoints <= optimalChanceThreshold) {
                    optimalChance = chanceOfTakingPoints;
                    bestCard = card;
                }

            }
        }
        if (bestCard == null) {
            for (Card card : hand) {
                if (card.getValue().equals("Q")) {
                    bestCard = card;
                }
            }
        }

        if (bestCard == null) {
            for (Card card : hand) {
                if (card.getSuit().equals("H")) {
                    if (bestCard == null) {
                        bestCard = card;
                    }
                    if (bestCard.getNumberValue() < card.getNumberValue()) {
                        bestCard = card;
                    }
                }
            }
        }
        if (bestCard == null) {
            for (Card card : hand) {

                if (bestCard == null) {
                    bestCard = card;
                }
                if (bestCard.getNumberValue() < card.getNumberValue()) {
                    bestCard = card;
                }

            }
        }
        return bestCard;
    }

    @Override
    public void update(ArrayList<Card> hand, String trickSuit, Card[] trick, Boolean iceBroken) {
        this.hand = hand;
        this.trickSuit = trickSuit;
        this.trick = trick;
        this.iceBroken = iceBroken;

        for (Card card : trick) {
            if (card != null) {
                int valuePosition = -1;
                String suit = card.getSuit();
                String value = card.getValue();
                try {
                    valuePosition = Integer.parseInt(value) - 2;
                } catch (NumberFormatException e) {
                    switch (value) {
                        case "J":
                            valuePosition = 9;
                            break;
                        case "Q":
                            valuePosition = 10;
                            break;
                        case "K":
                            valuePosition = 11;
                            break;
                        case "A":
                            valuePosition = 12;
                            break;
                    }
                }

                switch (suit) {
                    case "D":
                        valuePosition += 13;
                        break;
                    case "C":
                        valuePosition += 26;
                        break;
                    case "H":
                        valuePosition += 39;
                        break;

                }

                player1CardProbabilities[valuePosition] = false;
                player2CardProbabilities[valuePosition] = false;
                player3CardProbabilities[valuePosition] = false;
            }
        }
        double counterS = 0;
        double counterD = 0;
        double counterC = 0;
        double counterH = 0;
        for (int i = 0; i < player1CardProbabilities.length; i++) {
            if (i < 13) {
                if (player1CardProbabilities[i]) {
                    counterS++;
                }
            }
            if (i >= 13 && i < 26) {
                if (player1CardProbabilities[i]) {
                    counterD++;
                }
            }
            if (i >= 26 && i < 39) {
                if (player1CardProbabilities[i]) {
                    counterC++;
                }
            }
            if (i >= 39 && i < 52) {
                if (player1CardProbabilities[i]) {
                    counterH++;
                }
            }
        }
        player1SuitProbabilities[0] = counterS / 13;
        player1SuitProbabilities[1] = counterD / 13;
        player1SuitProbabilities[2] = counterC / 13;
        player1SuitProbabilities[3] = counterH / 13;
        counterS = 0;
        counterD = 0;
        counterC = 0;
        counterH = 0;
        for (int i = 0; i < player2CardProbabilities.length; i++) {
            if (i < 13) {
                if (player2CardProbabilities[i]) {
                    counterS++;
                }
            }
            if (i >= 13 && i < 26) {
                if (player2CardProbabilities[i]) {
                    counterD++;
                }
            }
            if (i >= 26 && i < 39) {
                if (player2CardProbabilities[i]) {
                    counterC++;
                }
            }
            if (i >= 39 && i < 52) {
                if (player2CardProbabilities[i]) {
                    counterH++;
                }
            }
        }
        player2SuitProbabilities[0] = counterS / 13;
        player2SuitProbabilities[1] = counterD / 13;
        player2SuitProbabilities[2] = counterC / 13;
        player2SuitProbabilities[3] = counterH / 13;
        counterS = 0;
        counterD = 0;
        counterC = 0;
        counterH = 0;
        for (int i = 0; i < player3CardProbabilities.length; i++) {
            if (i < 13) {
                if (player3CardProbabilities[i]) {
                    counterS++;
                }
            }
            if (i >= 13 && i < 26) {
                if (player3CardProbabilities[i]) {
                    counterD++;
                }
            }
            if (i >= 26 && i < 39) {
                if (player3CardProbabilities[i]) {
                    counterC++;
                }
            }
            if (i >= 39 && i < 52) {
                if (player3CardProbabilities[i]) {
                    counterH++;
                }
            }
        }
        player3SuitProbabilities[0] = counterS / 13;
        player3SuitProbabilities[1] = counterD / 13;
        player3SuitProbabilities[2] = counterC / 13;
        player3SuitProbabilities[3] = counterH / 13;
    }

    @Override
    public void firstHand(ArrayList<Card> hand) {
        for (Card card : hand) {
            int valuePosition = -1;
            String suit = card.getSuit();
            String value = card.getValue();
            try {
                valuePosition = Integer.parseInt(value) - 2;
            } catch (NumberFormatException e) {
                switch (value) {
                    case "J":
                        valuePosition = 9;
                        break;
                    case "Q":
                        valuePosition = 10;
                        break;
                    case "K":
                        valuePosition = 11;
                        break;
                    case "A":
                        valuePosition = 12;
                        break;
                }
            }

            switch (suit) {
                case "D":
                    valuePosition += 13;
                    break;
                case "C":
                    valuePosition += 26;
                    break;
                case "H":
                    valuePosition += 39;
                    break;

            }

            player1CardProbabilities[valuePosition] = false;
            player2CardProbabilities[valuePosition] = false;
            player3CardProbabilities[valuePosition] = false;
        }

    }

}
