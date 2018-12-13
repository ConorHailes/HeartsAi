/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts;

import java.util.ArrayList;

/**
 *
 * @author conor
 */
public class VoidAi implements Ai {

    private ArrayList<Card> hand;
    private ArrayList<Card> spadeCards;//0
    private ArrayList<Card> diamondCards;//1
    private ArrayList<Card> clubCards;//2
    private ArrayList<Card> heartCards;//3
    private String trickSuit;
    private Card[] trick;
    private Boolean iceBroken;
    private int smallestList;

    @Override
    public Card chooseCard() {
        Card highestCard = null;
        //play highest card of smallest suit if tricksuit not set
        Card[] tempArray = null;
        if (trickSuit.equals("")) {

            switch (smallestList) {
                case 0:
                    tempArray = new Card[spadeCards.size()];
                    for (int i = 0; i < spadeCards.size(); i++) {
                        tempArray[i] = spadeCards.get(i);
                    }
                    
                    break;
                case 1:
                    tempArray = new Card[diamondCards.size()];
                    for (int i = 0; i < diamondCards.size(); i++) {
                        tempArray[i] = diamondCards.get(i);
                    }
                    
                    break;
                case 2:
                    tempArray = new Card[clubCards.size()];
                    for (int i = 0; i < clubCards.size(); i++) {
                        tempArray[i] = clubCards.get(i);
                    }
                   
                    break;
                case 3:
                    tempArray = new Card[heartCards.size()];
                    for (int i = 0; i < heartCards.size(); i++) {
                        tempArray[i] = heartCards.get(i);
                    }
                
                    break;
            }
            return highestCard(tempArray);
        } //play highest suit card if tricksuit set and not set to heart
        else {
            //play lowest heart if tricksuit is heart
            if (trickSuit.equals("H") && hasSuit("H")) {
                tempArray = new Card[heartCards.size()];
                for (int i = 0; i < heartCards.size(); i++) {
                    tempArray[i] = heartCards.get(i);
                }
                return lowestCard(tempArray);
            } else if (trickSuit.equals("H") && !hasSuit("H")) {
                Card lowestCard = null;
                tempArray = new Card[diamondCards.size() + spadeCards.size() + clubCards.size()];
                int i = 0;
                for (Card card : diamondCards) {
                    tempArray[i] = card;
                    i++;
                }
                for (Card card : spadeCards) {
                    tempArray[i] = card;
                    i++;
                }
                for (Card card : clubCards) {
                    tempArray[i] = card;
                    i++;
                }
                return lowestCard(tempArray);
            } else {
                for (Card card : hand) {
                    if (card.getSuit().equals(trickSuit)) {
                        if (highestCard != null) {
                            if (card.compareTo(highestCard)) {
                                highestCard = card;

                            }
                        } else {
                            highestCard = card;
                        }

                    }
                }
            }

        }

        //play highest heart if tricksuit set and dont have that suit
        if (!trickSuit.equals("") && !hasSuit(trickSuit)) {
            if (hasSuit("H")) {
                tempArray = new Card[heartCards.size()];
                for (int i = 0; i < heartCards.size(); i++) {
                    tempArray[i] = heartCards.get(i);
                }
                highestCard = highestCard(tempArray);
            }else{
                 Card lowestCard = null;
                tempArray = new Card[diamondCards.size() + spadeCards.size() + clubCards.size()];
                int i = 0;
                for (Card card : diamondCards) {
                    tempArray[i] = card;
                    i++;
                }
                for (Card card : spadeCards) {
                    tempArray[i] = card;
                    i++;
                }
                for (Card card : clubCards) {
                    tempArray[i] = card;
                    i++;
                }
                return lowestCard(tempArray);
            }
        }
        if (highestCard != null) {
            return highestCard;
        }

        return null;
    }

    @Override
    public void update(ArrayList<Card> hand, String trickSuit, Card[] trick, Boolean iceBroken) {
        this.hand = hand;
        this.trickSuit = trickSuit;
        this.trick = trick;
        this.iceBroken = iceBroken;
        spadeCards = cardsInSuit("S");
        diamondCards = cardsInSuit("D");
        clubCards = cardsInSuit("C");
        heartCards = cardsInSuit("H");
        smallestArrayList();
    }

    public ArrayList<Card> cardsInSuit(String suit) {
        ArrayList<Card> returnList = new ArrayList();
        for (Card card : hand) {
            if (card.getSuit().equals(suit)) {
                returnList.add(card);
            }
        }
        return returnList;
    }

    private boolean hasSuit(String suitToCheck) {
        for (Card card : hand) {
            if (card.getSuit().equals(suitToCheck)) {
                return true;
            }
        }
        return false;
    }

    private Card highestCard(Card[] cards) {
        int[] numbers = new int[cards.length];
        int i = 0;
        for (Card card : cards) {
            if (card != null) {
                try {
                    numbers[i] = Integer.parseInt(card.getValue());
                } catch (NumberFormatException e) {
                    switch (card.getValue()) {
                        case "J":
                            numbers[i] = 11;
                            break;
                        case "Q":
                            numbers[i] = 12;
                            break;
                        case "K":
                            numbers[i] = 13;
                            break;
                        case "A":
                            numbers[i] = 14;
                            break;
                    }
                }
            }
            i++;
        }

        int position = 0;
        int highestNumber = 0;

        for (int j = 0; j < i; j++) {
            if (highestNumber == 0) {
                highestNumber = numbers[j];
                position = j;
            }
            if (numbers[j] > highestNumber && numbers[j] != 0) {
                highestNumber = numbers[j];
                position = j;
            }
        }

        Card returnCard = cards[position];

        return returnCard;
    }

    private void smallestArrayList() {
        int spadeSize = spadeCards.size();
        int diamondSize = diamondCards.size();
        int heartSize = heartCards.size();
        if (!iceBroken) {
            heartSize = 99;
        }
        int clubSize = clubCards.size();
        int[] suitSizeArray = new int[]{spadeSize, diamondSize, clubSize, heartSize};
        int lowestNumber = 0;
        int position = 0;
        for (int j = 0; j < 4; j++) {
            if (lowestNumber == 0) {
                lowestNumber = suitSizeArray[j];
                position = j;
            }
            if (suitSizeArray[j] < lowestNumber && suitSizeArray[j] != 0) {
                lowestNumber = suitSizeArray[j];
                position = j;
            }
        }
        smallestList = position;
    }

    private Card lowestCard(Card[] cards) {
        int[] numbers = new int[cards.length];
        int i = 0;
        for (Card card : cards) {
            if (card != null) {
                try {
                    numbers[i] = Integer.parseInt(card.getValue());
                } catch (NumberFormatException e) {
                    switch (card.getValue()) {
                        case "J":
                            numbers[i] = 11;
                            break;
                        case "Q":
                            numbers[i] = 12;
                            break;
                        case "K":
                            numbers[i] = 13;
                            break;
                        case "A":
                            numbers[i] = 14;
                            break;
                    }
                }
            }
            i++;
        }

        int position = 0;
        int lowestNumber = 0;

        for (int j = 0; j < i; j++) {
            if (lowestNumber == 0) {
                lowestNumber = numbers[j];
                position = j;
            }
            if (numbers[j] < lowestNumber && numbers[j] != 0) {
                lowestNumber = numbers[j];
                position = j;
            }
        }

        Card returnCard = cards[position];

        return returnCard;
    }

    @Override
    public void firstHand(ArrayList<Card> hand) {
    }
}
