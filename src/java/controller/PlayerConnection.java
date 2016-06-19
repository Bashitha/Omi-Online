/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Message;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author leshan
 */
@WebServlet(name = "PlayerConnection", urlPatterns = {"/PlayerConnection"})
public class PlayerConnection extends HttpServlet {
    
    private String connection=null;
    private String players[]={"playerOne","playerTwo","playerThree","playerFour"};//to store the user ids of players
    private static HttpSession session=null;
    private static PlayersHand playersHand=null;
    
    public static int counter=0;//to count the no of sessions created
    public static Object lockCounter = new Object();// lock to handle the count variable
    public static HashMap<String,AsyncContext> playerConnections = new HashMap<String,AsyncContext>();
    public static Object lockConnection = new Object();
    
    
    
    
   
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
  
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

            response.setContentType("text/event-stream");
            response.setCharacterEncoding("UTF-8");
            
            //checking whether a new user entered
            if(isSessionNew(request,response)){
                
                Message msg1=new Message();
                msg1.setCards(null);
                msg1.setShowHand(true);
                msg1.setShowCards(true); 

                msg1.setMessage("Waiting for others to connect. Only " +(counter) + " players connected ..,,,,,");

                ObjectMapper mapper=new ObjectMapper();
                String message=mapper.writeValueAsString(msg1);


                message=message.replace("\\","").replace("\"{", "{").replace("}\"", "}");

                try{
                    PrintWriter writer = response.getWriter();
                    writer.write("data: "+ message +"\n\n");
                    writer.flush();
                    System.out.println("data sent");
                }catch(IOException ex){
                    ex.printStackTrace();
                }

               asynchronousCheck(request);
            }
            else if(counter>=4)
            {
                Message msg2=new Message();//creates the msg to share cards for the connected players
                
                if(counter==4){
                    playersHand=new PlayersHand();
                    playersHand.cardMapper(playersHand.cards);
                    playersHand.sharingCards();
                }
                counter++;//increments the count to not to call the above if condition again 
                
                String[] cards=null;
                
                if(request.getSession().getAttribute("userId")==players[0]){
                    cards=new String[playersHand.playersHands[0].size()];
                    
                    for(int i=0;i<cards.length;i++){
                        //{"cards":[{"image": "cards/1_8.png" }

                        cards[i]="{\"image\": \"cards/"+playersHand.playersHands[0].get(i)+"\" }";
                    }
                }else if(request.getSession().getAttribute("userId")==players[1]){
                    cards=new String[playersHand.playersHands[1].size()];
                    
                    for(int i=0;i<cards.length;i++){
                        //{"cards":[{"image": "cards/1_8.png" }

                        cards[i]="{\"image\": \"cards/"+playersHand.playersHands[1].get(i)+"\" }";
                    }
                }else if(request.getSession().getAttribute("userId")==players[2]){
                    cards=new String[playersHand.playersHands[2].size()];
                    
                    for(int i=0;i<cards.length;i++){
                        //{"cards":[{"image": "cards/1_8.png" }

                        cards[i]="{\"image\": \"cards/"+playersHand.playersHands[2].get(i)+"\" }";
                    }
                }else if(request.getSession().getAttribute("userId")==players[3]){
                    cards=new String[playersHand.playersHands[3].size()];
                    
                    for(int i=0;i<cards.length;i++){
                        //{"cards":[{"image": "cards/1_8.png" }

                        cards[i]="{\"image\": \"cards/"+playersHand.playersHands[3].get(i)+"\" }";
                    }
                }
                
                msg2.setCards(cards);
                msg2.setShowHand(true);
                msg2.setShowCards(true); 

                msg2.setMessage("Play your card..");

                ObjectMapper mapper=new ObjectMapper();
                String message=mapper.writeValueAsString(msg2);


                message=message.replace("\\","").replace("\"{", "{").replace("}\"", "}");

                try{
                    PrintWriter writer = response.getWriter();
                    writer.write("data: "+ message +"\n\n");
                    writer.flush();
                    System.out.println("data sent");
                }catch(IOException ex){
                    ex.printStackTrace();
                }

                
                
            }

    }
    
    private void asynchronousCheck(HttpServletRequest request){
        
        final AsyncContext ac = request.startAsync();
	ac.addListener(new AsyncListener(){
		

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                playerConnections.remove(connection);
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                playerConnections.remove(connection);
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                playerConnections.remove(connection);
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                
            }
	});
		
		
        //add current connections to a hashmap
        synchronized(lockConnection){
                playerConnections.put(players[counter-1], ac);
                System.out.println("HashMap : "+playerConnections.size());
                //this.handleGame.getAccessStatus().onConnect(players);
        }
    }
    
    protected boolean isSessionNew(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            
            session=request.getSession();

            this.connection =(String) session.getAttribute("userId");

            //if the connection is  null giving a userId for the new session created
            if(connection == null){
                
                synchronized(lockCounter){
                    counter++;//if new session is created increments the count variable
                }


                if(counter>=4){
                    if(counter==4){
                        session.setAttribute("userId", players[counter-1] );
                    }
                    return false;

                }
                session.setAttribute("userId", players[counter-1] );

                
                return true;
            
            }
        
        
        return false;
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("get ");
        //if(isSessionNew(request,response)){    
           System.out.println("seesssion checcked");
           processRequest(request, response);
       // }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
            playCards(request, response);
//        response.setContentType("text/html");
//        PrintWriter writer = response.getWriter();
//        writer.println(request.getParameter("card"));
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void playCards(HttpServletRequest request, HttpServletResponse response) {
         response.setContentType("text/event-stream");
         response.setCharacterEncoding("UTF-8");
         
         Message msg3=new Message();
         
         String[] cards=null;
         
         
         if(request.getSession().getAttribute("userId")==players[0]){
             
         }
//        PrintWriter writer = response.getWriter();
//        writer.println(request.getParameter("card"));
    }

}
