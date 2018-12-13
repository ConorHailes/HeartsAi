/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts;

/**
 *
 * @author conor_000
 */
public class Card {

    private String suit;
    private String value;
    private int point;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        if (suit.equals("H")) {
            point = 1;
        }
        if (suit.equals("S") && value.equals("Q")) {
            point = 13;
        }
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }
    public int getNumberValue(){
        int numbValue = 0;
        try {
                numbValue = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                switch (value) {
                    case "J":
                        numbValue = 11;
                        break;
                    case "Q":
                        numbValue = 12;
                        break;
                    case "K":
                        numbValue = 13;
                        break;
                    case "A":
                        numbValue = 14;
                        break;
                }
            }
    return numbValue;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public int getPoint() {
        return point;
    }

    public void printCard() {
        System.out.print(value + suit + " ");
    }

    /**
     * Compares two cards. If the suits are the same compare value. If not then
     * non referenced card is higher value
     *
     * @param card card to compare to
     * @return false if reference card is higher valued.
     */
    public boolean compareTo(Card card) {
        if (suit.equals(card.getSuit())) {
            int value1 = 0;
            try {
                value1 = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                switch (value) {
                    case "J":
                        value1 = 11;
                        break;
                    case "Q":
                        value1 = 12;
                        break;
                    case "K":
                        value1 = 13;
                        break;
                    case "A":
                        value1 = 14;
                        break;
                }
            }
            int value2=0;
            try {
                value2 = Integer.parseInt(card.getValue());
            } catch (NumberFormatException e) {
                switch (card.getValue()) {
                    case "J":
                        value2 = 11;
                        break;
                    case "Q":
                        value2 = 12;
                        break;
                    case "K":
                        value2 = 13;
                        break;
                    case "A":
                        value2 = 14;
                        break;
                }
            }
            if (value2 > value1) {
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
}
