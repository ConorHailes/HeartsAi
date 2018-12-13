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
public class LowCardAi implements Ai {

    private ArrayList<Card> hand;
    private String trickSuit;
    private Card[] trick;
    private Boolean iceBroken;

    @Override
    public void update(ArrayList<Card> hand, String trickSuit, Card[] trick, Boolean iceBroken) {
        this.hand = hand;
        this.trickSuit = trickSuit;
        this.trick = trick;
        this.iceBroken = iceBroken;
    }

    @Override
    public Card chooseCard() {
        Card returnCard = null;
        Card lowestCard = null;
        if (trickSuit.equals("")) {
            Card lowestSpade = null;
            Card lowestHeart = null;
            Card lowestDiamond = null;
            Card lowestClub = null;
            for (Card card : hand) {
                switch (card.getSuit()) {
                    case "S":
                        if (lowestSpade == null) {
                            lowestSpade = card;
                        } else if (!card.compareTo(lowestSpade)) {
                            lowestSpade = card;
                        }
                        break;
                    case "H":
                        if (lowestHeart == null) {
                            lowestHeart = card;
                        } else if (!card.compareTo(lowestHeart)) {
                            lowestHeart = card;
                        }
                        break;
                    case "D":
                        if (lowestDiamond == null) {
                            lowestDiamond = card;
                        } else if (!card.compareTo(lowestDiamond)) {
                            lowestDiamond = card;
                        }
                        break;
                    case "C":
                        if (lowestClub == null) {
                            lowestClub = card;
                        } else if (!card.compareTo(lowestClub)) {
                            lowestClub = card;
                        }
                        break;
                }

            }
            Card[] cards = new Card[]{lowestSpade, lowestDiamond, lowestClub, lowestHeart};
            lowestCard = lowestCard(cards);
        } else {
            for (Card card : hand) {
                if (card.getSuit().equals(trickSuit)) {
                    if (lowestCard != null) {
                        if (!card.compareTo(lowestCard)) {
                            lowestCard = card;

                        }
                    } else {
                        lowestCard = card;
                    }

                }
            }
        }
        if (lowestCard != null) {
            return lowestCard;
        }

        Card lowestSpade = null;
        Card lowestHeart = null;
        Card lowestDiamond = null;
        Card lowestClub = null;
        for (Card card : hand) {
            switch (card.getSuit()) {
                case "S":
                    if (lowestSpade == null) {
                        lowestSpade = card;
                    } else if (!card.compareTo(lowestSpade)) {
                        lowestSpade = card;
                    }
                    break;
                case "H":
                    if (lowestHeart == null) {
                        lowestHeart = card;
                    } else if (!card.compareTo(lowestHeart)) {
                        lowestHeart = card;
                    }
                    break;
                case "D":
                    if (lowestDiamond == null) {
                        lowestDiamond = card;
                    } else if (!card.compareTo(lowestDiamond)) {
                        lowestDiamond = card;
                    }
                    break;
                case "C":
                    if (lowestClub == null) {
                        lowestClub = card;
                    } else if (!card.compareTo(lowestClub)) {
                        lowestClub = card;
                    }
                    break;
            }

        }
        Card[] cards = new Card[]{lowestSpade, lowestDiamond, lowestClub, lowestHeart};
        lowestCard = lowestCard(cards);
        return lowestCard;
    }

    private Card lowestCard(Card[] cards) {
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
        if (iceBroken||(!hasSuit("S")&&!hasSuit("C")&&!hasSuit("D"))) {

            int lowestNumber = 0;

            for (int j = 0; j < 4; j++) {
                if (lowestNumber == 0) {
                    lowestNumber = numbers[j];
                    position = j;
                }
                if (numbers[j] < lowestNumber && numbers[j] != 0) {
                    lowestNumber = numbers[j];
                    position = j;
                }
            }
        } else {

            int lowestNumber = 0;
            for (int j = 0; j < 3; j++) {
                if (lowestNumber == 0) {
                    lowestNumber = numbers[j];
                    position = j;
                }
                if (numbers[j] < lowestNumber && numbers[j] != 0) {
                    lowestNumber = numbers[j];
                    position = j;
                }
            }
        }
        Card returnCard = cards[position];

        return returnCard;
    }

    private boolean hasSuit(String suitToCheck) {
        for (Card card : hand) {
            if (card.getSuit().equals(suitToCheck)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void firstHand(ArrayList<Card> hand) {
    }
}
