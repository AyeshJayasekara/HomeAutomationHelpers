/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myhomehelper;


/**
 *
 * @author ayeshjayasekara1
 */
public class MyHomeHelper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Listen ob = new Listen("pub-c-89d87bfb-89a1-4ae9-941c-631dd1ea5dbd","sub-c-e7c47d7a-13be-11e7-a9ec-0619f8945a4f");
        ob.run();
        
    }
    
}
