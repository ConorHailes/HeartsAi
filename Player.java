/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts;

import java.util.ArrayList;

/**
 *
 * @author conor_000
 */
public class Player {

    private ArrayList<Card> hand;
    private int points;
    private int playerId;
    private boolean first;
    private ArrayList<Card> cardsTaken;
    private Ai algorithm;

    public Player(int playerId) {
        this.playerId = playerId;
        points = 0;
        hand = new ArrayList();
        cardsTaken = new ArrayList();
        first = false;

        switch(playerId){
            case 0:
                algorithm = new SmartAi();
                break;
            case 1:
                algorithm = new LowCardAi();
                break;
            case 2:
                algorithm = new HighCardAi();
                break;
            case 3:
                algorithm = new VoidAi();
                break;
        }

    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void drawCard(Card card) {
        hand.add(card);
        if (card.getValue().equals("2") && card.getSuit().equals("C")) {
            first = true;
        }
        if(hand.size()==13&&playerId==0){
            algorithm.firstHand(hand);
        }
    }

    public void tallyPoints() {
        int roundPoints = 0;
        if (cardsTaken.size() > 0) {
            for (Card card : cardsTaken) {
                roundPoints += card.getPoint();
            }
        }
        if(roundPoints != 26){            
            points+=roundPoints;
        }
    }

    public Card playCard(String card) {
        String cardInHand;
        Card playCard = null;
        for (Card handCard : hand) {
            cardInHand = handCard.getValue() + handCard.getSuit();
            if (cardInHand.equals(card)) {
                playCard = handCard;
                hand.remove(handCard);
                break;
            }
        }
        return playCard;
    }

    public Card playCard(Card card) {
        Card playCard = null;
        for (Card handCard : hand) {
            if (card == handCard) {
                playCard = handCard;
                hand.remove(handCard);
                break;
            }

        }
        return playCard;
    }

    public int getId() {
        return playerId;
    }

    public void giveCards(Card[] trick) {
        for (Card card : trick) {
            cardsTaken.add(card);
        }
    }

    public boolean hasSuit(String suitToCheck) {
        for (Card card : hand) {
            if (card.getSuit().equals(suitToCheck)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFirst() {
        return first;
    }

    public int getPoints() {
        return points;
    }

    public void printHand() {
        System.out.print(playerId + " ");
        for (Card card : hand) {
            card.printCard();
        }
        //System.out.println("");
    }

    public void printTaken() {
        System.out.print(playerId + " ");
        for (Card card : cardsTaken) {
            if(card.getSuit().equals("H"))
                card.printCard();
            if(card.getSuit().equals("S")&&card.getValue().equals("Q")){
                card.printCard();
            }
        }
        cardsTaken = new ArrayList();
    }
    public Card chooseCard(Card[] trick, String trickSuit, Boolean iceBroken) {
        algorithm.update(hand, trickSuit, trick, iceBroken);
        return algorithm.chooseCard();
    }
    public void update(Card[] trick, String trickSuit, Boolean iceBroken){
        algorithm.update(hand, trickSuit, trick, iceBroken);
    }
}
