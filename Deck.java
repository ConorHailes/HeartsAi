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
 * @author conor_000
 */
public class Deck {
    ArrayList<Card> cards;
    public Deck(){
        String value = "";
        String suit = "";
               
        cards = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            if(i<13){
                suit = "H";
                
                if(i==0){
                    value = "A";
                }
                if(i>0&&i<10){
                    value = (i+1)+"";
                }
                if(i==10){
                    value = "J";
                }
                if(i==11){
                    value = "Q";
                }
                if(i==12){
                    value = "K";
                }
            }
            if(i>=13&&i<26){
                suit = "C";
                if(i-13==0){
                    value = "A";
                }
                if(i-13>0&&i-13<10){
                    value = (i-12)+"";
                }
                if(i-12==11){
                    value = "J";
                }
                if(i-12==12){
                    value = "Q";
                }
                if(i-12==13){
                    value = "K";
                }
            }
            if(i>=26&&i<39){
                suit = "D";
                if(i-26==0){
                    value = "A";
                }
                if(i-26>0&&i-26<10){
                    value = (i-25)+"";
                }
                if(i-25==11){
                    value = "J";
                }
                if(i-25==12){
                    value = "Q";
                }
                if(i-25==13){
                    value = "K";
                }
            }
            if(i>=39){
                suit = "S";
                if(i-39==0){
                    value = "A";
                }
                if(i-39>0&&i-39<10){
                    value = (i-38)+"";
                }
                if(i-38==11){
                    value = "J";
                }
                if(i-38==12){
                    value = "Q";
                }
                if(i-38==13){
                    value = "K";
                }
            }
            cards.add(new Card(suit,value));
        }
    }
    
    public void printDeck(){
        for(Card card:cards){
            System.out.println(card.getValue()+card.getSuit());
        }
    }
    
    public void shuffle(){
        Random r = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int newSpot = r.nextInt(cards.size());
            Card tempCard = cards.get(i);
            cards.set(i, cards.get(newSpot));
            cards.set(newSpot, tempCard);
        }
    }
    public int deckSize(){
        return cards.size();
    }
    public Card drawCard(){        
        return cards.remove(cards.size()-1);
    }
}
