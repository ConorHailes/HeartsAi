/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author conor_000
 */
public class Hearts {

    private static Deck deck;
    private static Card[] trick;
    private static Player[] players;
    private static int firstPlayer;
    private static String trickSuit;
    private static int currentPlayerId;
    private static int[] winsForPlayer;
    private static int[] positionsForPlayer0;
    private static int[] positionsForPlayer1;
    private static int[] positionsForPlayer2;
    private static int[] positionsForPlayer3;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        winsForPlayer = new int[4];
        positionsForPlayer0 = new int[4];
        positionsForPlayer1 = new int[4];
        positionsForPlayer2 = new int[4];
        positionsForPlayer3 = new int[4];
        for (int i = 0; i < 100; i++) {
            runGame();
        }
        
//        for (int i = 0; i < winsForPlayer.length; i++) {
//            System.out.println(winsForPlayer[i]);
//        }
        System.out.println("Player 0");
        for (int i = 0; i < 4; i++) {
            System.out.println("position " +(i+1)+" "+ positionsForPlayer0[i]);           
        }
                System.out.println("Player 1");
        for (int i = 0; i < 4; i++) {
            System.out.println("position " +(i+1)+" "+ positionsForPlayer1[i]);
        }
                System.out.println("Player 2");
        for (int i = 0; i < 4; i++) {
            System.out.println("position " +(i+1)+" "+ positionsForPlayer2[i]);
        }
                System.out.println("Player 3");
        for (int i = 0; i < 4; i++) {
            System.out.println("position " +(i+1)+" "+ positionsForPlayer3[i]);
        }
    }

    private static int trickWinner() {
        /**
         * trick[] starts at firstPlayer and goes till last say firstPlayerId ==
         * 2
         *
         * trick[0] = firstPlayer = 2 trick[1] = 3 trick[2] = 0 trick[3] = 1
         */
        int playerId = currentPlayerId;
        int winningPlayer = -1;
        Card highestCard = null;
        for (Card card : trick) {
            if (highestCard == null) {
                winningPlayer = playerId;
                highestCard = card;
            } else if (card.compareTo(highestCard)) {
                highestCard = card;
                winningPlayer = playerId;
                System.out.println(playerId);
            }
            playerId++;
            if (playerId == 4) {
                playerId -= 4;
            }
        }
        return winningPlayer;
    }

    private static void runGame(){
        Scanner scanner = new Scanner(System.in);
        String nextLine;
        trickSuit = "";
        currentPlayerId = 0;
        int tricksPlayed = 0;
        deck = new Deck();
        deck.shuffle();
        deck.shuffle();
        firstPlayer = 0;
        int cardsPlayed = 0;
        trick = new Card[4];
        boolean roundOver = false;
        boolean iceBroken = false;
        boolean gameOver = false;
        boolean trickOver = false;
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(i);

        }

        int playerId = 0;
        while (!gameOver) {
            if (roundOver) {
                roundOver = false;
                for (Player player : players) {
                    player.setFirst(false);
                    player.tallyPoints();
                    player.printTaken();
                    System.out.println(" points " + player.getPoints());
                    if (player.getPoints() >= 100) {
                        gameOver = true;
                    }
                }
                if (gameOver) {
                    break;
                }
                tricksPlayed = 0;
                deck = new Deck();
                deck.shuffle();
                deck.shuffle();
                iceBroken = false;
                playerId = 0;

            }
            while (deck.deckSize() > 0) {
                players[playerId].drawCard(deck.drawCard());
                playerId++;
                if (playerId == 4) {
                    playerId = 0;
                }
            }

            for (Player player : players) {
                player.printHand();
                System.out.println();
                if (player.isFirst()) {
                    firstPlayer = player.getId();
                }

            }
            trick[firstPlayer] = players[firstPlayer].playCard("2C");
            cardsPlayed++;
            currentPlayerId = firstPlayer + 1;
            if (currentPlayerId == 4) {
                currentPlayerId -= 4;
            }
            trickSuit = "C";
            System.out.println("");

            System.out.println("");

            while (!roundOver) {
                if (!trickOver) {
                    for (Player player : players) {
                        player.printHand();
                        System.out.println("");
                    }
                    System.out.println("Current Trick. Suit " + trickSuit + " current player is " + currentPlayerId);

                    for (Card card : trick) {
                        if (card != null) {
                            card.printCard();
                        }
                    }
                    System.out.println("");
                }
                if (trickOver) {
                    tricksPlayed++;
                    currentPlayerId = trickWinner();
                    System.out.println("Player " + currentPlayerId + " wins trick. Suit " + trickSuit);
                    for (Card card : trick) {
                        if (card != null) {
                            card.printCard();
                        }
                    }

                    players[currentPlayerId].giveCards(trick);
                    trickSuit = "";
                    cardsPlayed = 0;
                    trickOver = false;
                    players[0].update(trick, trickSuit, iceBroken);
                    trick = new Card[4];
                    System.out.println("");
                    if (tricksPlayed == 13) {
                        roundOver = true;
                        break;
                    }
                    for (Player player : players) {
                        player.printHand();
                        System.out.println("");
                    }
                    System.out.println("New Trick. Player " + currentPlayerId + " starts. Ice broken "+ iceBroken);

                }
                while (!trickOver) {
                    //Thread.sleep(100);
                    if (currentPlayerId == -1) {
                        nextLine = scanner.nextLine();
                        while ((!nextLine.contains(trickSuit) && players[currentPlayerId].hasSuit(trickSuit)) || (nextLine.contains("H") && !iceBroken)) {
                            if (!players[currentPlayerId].hasSuit(trickSuit)) {
                                break;
                            }
                            System.out.println("Invalid choice");
                            nextLine = scanner.nextLine();
                        }
                        while (trick[cardsPlayed] == null) {
                            trick[cardsPlayed] = players[currentPlayerId].playCard(nextLine);
                            if (trick[cardsPlayed] == null) {
                                System.out.println("Invalid choice");
                                nextLine = scanner.nextLine();
                            } else if (trickSuit.equals("")) {
                                trickSuit = trick[cardsPlayed].getSuit();
                            }
                        }
                    } else {
                        Card playerCard = players[currentPlayerId].playCard(players[currentPlayerId].chooseCard(trick, trickSuit, iceBroken));                      
                        //cardsPlayed++;
                        System.out.println(playerCard.getValue() + playerCard.getSuit());
                        trick[currentPlayerId] = playerCard;
                        if (trickSuit.equals("")) {
                            trickSuit = trick[currentPlayerId].getSuit();
                        }

                    }
                    if (trick[currentPlayerId].getSuit().equals("H") && !iceBroken) {
                        iceBroken = true;
                    }
                    cardsPlayed++;
                    if (cardsPlayed == 4) {
                        trickOver = true;
                        currentPlayerId++;
                        if (currentPlayerId == 4) {
                            currentPlayerId -= 4;
                        }
                        break;
                    }
                    currentPlayerId++;
                    if (currentPlayerId == 4) {
                        currentPlayerId -= 4;
                    }

                    System.out.println("Current Trick. Suit " + trickSuit + " current player is " + currentPlayerId);
                    for (Card card : trick) {
                        if (card != null) {
                            card.printCard();
                        }
                    }
                    System.out.println("");

                }

            }
        }

        ArrayList<Player> orderedPlayers = new ArrayList();
        int index = 0;
        for (Player player : players) {
            if (index == 0) {
                orderedPlayers.add(player);
            } else {
                boolean spotFound = false;
                int position = -1;
                for (int i = index; i > 0; i--) {
                    if (player.getPoints() >= orderedPlayers.get(i-1).getPoints()) {
                        position = i;
                        spotFound=true;
                        break;
                    }
                }
                if(spotFound){
                    orderedPlayers.add(position, player);
                }else{
                    orderedPlayers.add(0, player);
                }

            }
//[],1->[1],5->[1,5],3->[1,3,5],2->[1,2,3,5]
            index++;
        }
        boolean winner = true;
        int counter = 0;
        for (Player player : orderedPlayers) {
            if(winner){
                winsForPlayer[player.getId()]++;
                winner = false;
            }
            switch(player.getId()){
                case 0:
                    positionsForPlayer0[counter]++;
                    break;
                case 1:
                    positionsForPlayer1[counter]++;
                    break;
                case 2:
                    positionsForPlayer2[counter]++;
                    break;
                case 3:
                    positionsForPlayer3[counter]++;
                    break;
            }
            System.out.println("Player " + player.getId() + ". Points " + player.getPoints());
            counter++;
        }


    }
}
