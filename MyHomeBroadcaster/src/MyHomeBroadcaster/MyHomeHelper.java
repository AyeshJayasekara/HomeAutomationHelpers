/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHomeBroadcaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ayeshjayasekara1
 */
public class MyHomeHelper extends Thread {
    
    private boolean isSet;
    private Broadcaster broadcast;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null,size= null;
    private String MSG=null;

    public MyHomeHelper() {
        isSet= false;
        this.broadcast  = new Broadcaster();
    }
    
    
    @Override
    public void run(){       
        while(true){
            if(!isSet){
                try{
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost/user_login","root","");
                statement = (Statement) connect.createStatement();
                
                        
            }
            catch(Exception e){
                System.out.println("Error " + e.toString() );
            }
                isSet= true;
            }
            try {
                resultSet = statement.executeQuery("select * from Status where username='asd@asd.com'");
                while(resultSet.next()){
                        MSG = ""+resultSet.getString(3)+resultSet.getString(4)+resultSet.getString(5)
                                +resultSet.getString(6)+resultSet.getString(7)+resultSet.getString(8)
                                +resultSet.getString(9)+resultSet.getString(10)+resultSet.getString(11)
                                +resultSet.getString(17)+resultSet.getString(18);
                        //broadcast.sendMessage(MSG, resultSet.getString(2));
                        broadcast.sendMessage(MSG, "0");
                        System.out.println(""+MSG);
            }
                
            } catch (SQLException ex) {
                Logger.getLogger(MyHomeHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(Exception e){
                System.out.println(""+e.getMessage());
            }
            try { 
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyHomeHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
}
