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
public interface Ai {
    
    public Card chooseCard();
    public void update(ArrayList<Card> hand, String trickSuit, Card[] trick, Boolean iceBroken);
    public void firstHand(ArrayList<Card> hand);
}
