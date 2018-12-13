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
public class HighCardAi implements Ai {

    private ArrayList<Card> hand;
    private String trickSuit;
    private Card[] trick;
    private Boolean iceBroken;

    @Override
    public Card chooseCard() {
        Card returnCard = null;
        Card highestCard = null;
        if (trickSuit.equals("")) {
            Card highestSpade = null;
            Card highestHeart = null;
            Card highestDiamond = null;
            Card highestClub = null;
            for (Card card : hand) {
                switch (card.getSuit()) {
                    case "S":
                        if (highestSpade == null) {
                            highestSpade = card;
                        } else if (card.compareTo(highestSpade)) {
                            highestSpade = card;
                        }
                        break;
                    case "H":
                        if (highestHeart == null) {
                            highestHeart = card;
                        } else if (card.compareTo(highestHeart)) {
                            highestHeart = card;
                        }
                        break;
                    case "D":
                        if (highestDiamond == null) {
                            highestDiamond = card;
                        } else if (card.compareTo(highestDiamond)) {
                            highestDiamond = card;
                        }
                        break;
                    case "C":
                        if (highestClub == null) {
                            highestClub = card;
                        } else if (card.compareTo(highestClub)) {
                            highestClub = card;
                        }
                        break;
                }

            }
            Card[] cards = new Card[]{highestSpade, highestDiamond, highestClub, highestHeart};
            highestCard = highestCard(cards);
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
        if (highestCard != null) {
            return highestCard;
        }

        Card highestSpade = null;
        Card highestHeart = null;
        Card highestDiamond = null;
        Card highestClub = null;
        for (Card card : hand) {
            switch (card.getSuit()) {
                case "S":
                    if (highestSpade == null) {
                        highestSpade = card;
                    } else if (card.compareTo(highestSpade)) {
                        highestSpade = card;
                    }
                    break;
                case "H":
                    if (highestHeart == null) {
                        highestHeart = card;
                    } else if (card.compareTo(highestHeart)) {
                        highestHeart = card;
                    }
                    break;
                case "D":
                    if (highestDiamond == null) {
                        highestDiamond = card;
                    } else if (card.compareTo(highestDiamond)) {
                        highestDiamond = card;
                    }
                    break;
                case "C":
                    if (highestClub == null) {
                        highestClub = card;
                    } else if (card.compareTo(highestClub)) {
                        highestClub = card;
                    }
                    break;
            }

        }

        Card[] cards = new Card[]{highestSpade, highestDiamond, highestClub, highestHeart};
        highestCard = highestCard(cards);
        return highestCard;
    }

    @Override
    public void update(ArrayList<Card> hand, String trickSuit, Card[] trick, Boolean iceBroken) {
        this.hand = hand;
        this.trickSuit = trickSuit;
        this.trick = trick;
        this.iceBroken = iceBroken;
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
        int[] numbers = new int[4];
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
        if (iceBroken || (!hasSuit("S") && !hasSuit("C") && !hasSuit("D"))) {

            int highestNumber = 0;

            for (int j = 0; j < 4; j++) {
                if (highestNumber == 0) {
                    highestNumber = numbers[j];
                    position = j;
                }
                if (numbers[j] > highestNumber && numbers[j] != 0) {
                    highestNumber = numbers[j];
                    position = j;
                }
            }
        } else {

            int highestCard = 0;
            for (int j = 0; j < 3; j++) {
                if (highestCard == 0) {
                    highestCard = numbers[j];
                    position = j;
                }
                if (numbers[j] > highestCard && numbers[j] != 0) {
                    highestCard = numbers[j];
                    position = j;
                }
            }
        }
        Card returnCard = cards[position];

        return returnCard;
    }

    @Override
    public void firstHand(ArrayList<Card> hand) {
    }
}
