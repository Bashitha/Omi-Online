/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author leshan
 */
public class PlayersHand {
    
    //to store the initial order of the cards
    public static String[] cards = {"0_1.png","0_2.png","0_3.png","0_4.png","0_5.png","0_6.png","0_7.png","0_8.png","0_9.png","0_10.png","0_11.png","0_12.png","0_13.png","1_1.png","1_2.png","1_3.png","1_4.png","1_5.png","1_6.png","1_7.png","1_8.png","1_9.png","1_10.png","1_11.png","1_12.png","1_13.png","2_1.png","2_2.png","2_3.png","2_4.png","2_5.png","2_6.png","2_7.png","2_8.png","2_9.png","2_10.png","2_11.png","2_12.png","2_13.png","3_1.png","3_2.png","3_3.png","3_4.png","3_5.png","3_6.png","3_7.png","3_8.png","3_9.png","3_10.png","3_11.png","3_12.png","3_13.png"};
    
    //to map the initial order of the cards to the respective integer
    public static Map<String,Integer> cardIndexerMapper=new HashMap<String,Integer>();
    
    public  List<String>[] playersHands = new ArrayList[4];//to store the cards of 4 players
    
    public static String triumpSuit=null;//to store the triump suite
    
    //map the card to the index
    public void cardMapper(String[] cards){
        for(int i=0;i<cards.length;i++){
            cardIndexerMapper.put(cards[i],i);
        }
    }
    public String[] shuffleCards(String[] array) {
        List<String> list = new ArrayList<String>();
        for (String i : array) {
          list.add(i);
        }

        Collections.shuffle(list);

        for(int i=0;i<list.size();i++){
            array[i]=list.get(i);
        }   
        return array;
    }
    
    public void sharingCards(){
        
        String[] shuffledCards=shuffleCards(this.cards);
        
        for(int i=0;i<playersHands.length;i++){
            playersHands[i]=new ArrayList<String>();
        }
        
        //share cards between 4 players equally
        for(int i=0 ; i < 52 ; i++){
            playersHands[i%4].add(shuffledCards[i]);
//            System.out.println(playersHand[i%4].get(i));
        }
        triumpSuit=playersHands[3].get(12);
    }
    
    public static void main(String[] args) {
        PlayersHand hand=new PlayersHand();
        hand.cardMapper(cards);
        hand.sharingCards();
        //System.out.println(cardIndexerMapper.get(shuffledCards[7]));
        
        for(int i=0;i<hand.playersHands[0].size();i++){
            System.out.println(hand.playersHands[0].get(i));
        }
        System.out.println("***********");
        for(int i=0;i<hand.playersHands[1].size();i++){
            System.out.println(hand.playersHands[1].get(i));
        }
        System.out.println("***********");
        for(int i=0;i<hand.playersHands[2].size();i++){
            System.out.println(hand.playersHands[2].get(i));
        }
        System.out.println("***********");
        for(int i=0;i<hand.playersHands[3].size();i++){
            System.out.println(hand.playersHands[3].get(i));
        }
    }
    
}
