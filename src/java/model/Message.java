/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.PlayersHand;

/**
 *
 * @author leshan
 */
public class Message {
    private String[] cards;
    private boolean showHand=false;
    private boolean showCards=false;
    private String message;
   // private PlayersHand playersHand;

    /**
     * @return the cards
     */
    public String[] getCards() {
        return cards;
    }

    /**
     * @param cards the cards to set
     */
    public void setCards(String[] cards) {
        
            
//        PlayersHand playersHand=new PlayersHand();
//        playersHand.cardMapper(playersHand.cards);
//        playersHand.sharingCards();
//        this.cards=new String[playersHand.playersHands[0].size()];

        this.cards=cards;
       
    }

    /**
     * @return the showHand
     */
    public boolean isShowHand() {
        return showHand;
    }

    /**
     * @param showHand the showHand to set
     */
    public void setShowHand(boolean showHand) {
        this.showHand = showHand;
    }

    /**
     * @return the showCards
     */
    public boolean isShowCards() {
        return showCards;
    }

    /**
     * @param showCards the showCards to set
     */
    public void setShowCards(boolean showCards) {
        this.showCards = showCards;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the playersHand
     */


    /**
     * @param playersHand the playersHand to set
     */
    public void setPlayersHand() {
        
    }
    
    
}
