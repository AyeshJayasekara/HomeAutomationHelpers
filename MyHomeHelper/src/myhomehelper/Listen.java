/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myhomehelper;

import com.google.gson.JsonElement;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 *
 * @author ayeshjayasekara1
 */
public class Listen implements Runnable {
    PNConfiguration pnConfiguration = new PNConfiguration();
    PubNub pubnub;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null,size= null;
    boolean isSet=false;
    String channels[];
    int temp;
    boolean Rain,Plant;
    int i=0;
        
    @Override
    public void run() {
        while(true)
        if(!isSet){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost/user_login","root","");
                statement = (Statement) connect.createStatement();
                
                size = statement.executeQuery("select count(channel) from Status");
                size.first();
                        //System.out.println(""+size.getInt(1));   
                      channels = new String[size.getInt(1)];
                      size.close();
                      resultSet = statement.executeQuery("select channel from Status");
                      while(resultSet.next())  {
                          
                          //System.out.println(""+resultSet.getString(1));
                          channels[i++] = resultSet.getString(1);
                          
                     }
                      //for(String a : channels)
                         // System.out.println(""+a);
                      
                      
                      pubnub.addListener(new SubscribeCallback() {
                    @Override
                    public void status(PubNub pubnub, PNStatus pns) {
                       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void message(PubNub pubnub, PNMessageResult pnmr) {
                        try{
                            String c = pnmr.getChannel();
                            JsonElement msg=pnmr.getMessage();
                            System.out.println(""+msg);
                            
                            String T =msg.toString();
                            if(T.contains("!")){
                            String t = T.substring(1, T.indexOf('!'));
                            temp = Integer.parseInt(t);
                            System.out.println("Temp "+temp);
                            T = T.substring(T.indexOf('!')+1);
                            t = T.substring(0, T.indexOf('!'));
                            int S = Integer.parseInt(t);
                            if(S>512)
                                Plant=true;
                            else 
                                Plant=false;
                            System.out.println("Soil "+t);
                            T = T.substring(T.indexOf('!')+1);
                            t = T.substring(0,T.indexOf('!'));
                            int R = Integer.parseInt(t);
                            if(R>512) // TODO
                                Rain=true;
                            else 
                                Rain=false;
                            System.out.println("Rain "+t);
                            
                            //From here
                            // create a mysql database connection
                            String myDriver = "com.mysql.jdbc.Driver";
                            String myUrl = "jdbc:mysql://localhost/user_login";
                            Class.forName(myDriver);
                            Connection conn = DriverManager.getConnection(myUrl, "root", "");

      // the mysql insert statement
      String query = " update Status set temp = ? where channel='0'";

      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
      preparedStmt.setInt (1, temp);
      // execute the preparedstatement
      preparedStmt.execute();
      
      conn.close();}
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                        }
                        catch(Exception e){
                            System.out.println(""+e.getMessage());
                        }
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void presence(PubNub pubnub, PNPresenceEventResult pnper) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                      });
                      pubnub.subscribe().channels(Arrays.asList("0")).execute();

                      //System.out.println("asddddddddddddd");
                      
                          
            }
            catch(Exception e){
                System.out.println("Error " + e.toString() );
            }
            isSet=true;
        }
        else{
            pubnub.publish().channel("0").message("asdasdd");
        }
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Listen(String pubkey,String subkey) {
        pnConfiguration.setSubscribeKey(subkey);
        pnConfiguration.setPublishKey(pubkey);
        pubnub = new PubNub(pnConfiguration);
        
        
    }
    
    
    private void Texter(String l){
        String T = l.substring(0, l.indexOf('!'));
        temp = Integer.parseInt(T);
    }
    
    
}
