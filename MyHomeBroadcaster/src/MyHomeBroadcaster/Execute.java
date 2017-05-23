/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHomeBroadcaster;

/**
 *
 * @author ayeshjayasekara1
 */
public class Execute {
    public static void main(String[] args) throws InterruptedException {
        MyHomeHelper help = new MyHomeHelper();
        help.start();
        while(true){
        help.join(2000);
        //help.start();
        
        }
    }
}
