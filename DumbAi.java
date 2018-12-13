/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author conor
 */
public class DumbAi implements Ai {

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
        Card returnCard=null;
        if (trickSuit.equals("")) {

        } else {
            for (Card card : hand) {
                if (card.getSuit().equals(trickSuit)) {
                    return card;
                }
            }
        }
        Random rand = new Random();
        returnCard = hand.get(rand.nextInt(hand.size()));
        while(trick[0]==null&&returnCard.getSuit().equals("H")){
            if(iceBroken||(!hasSuit("S")&&!hasSuit("C")&&!hasSuit("D"))){
                return returnCard;
            }
            returnCard = hand.get(rand.nextInt(hand.size()));
        }
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
