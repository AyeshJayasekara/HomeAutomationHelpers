/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHomeBroadcaster;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author ayeshjayasekara1
 */
public class Broadcaster {
    
    public PNConfiguration pnConfiguration;
    public PubNub pubnub ;

    public Broadcaster() {
    PNConfiguration pnConfiguration = new PNConfiguration();
    pnConfiguration.setSubscribeKey("sub-c-e7c47d7a-13be-11e7-a9ec-0619f8945a4f"); 
    pnConfiguration.setPublishKey("pub-c-89d87bfb-89a1-4ae9-941c-631dd1ea5dbd");
     
    pubnub = new PubNub(pnConfiguration);
    
    pubnub.addListener(new SubscribeCallback() {
    @Override
    public void status(PubNub pubnub, PNStatus status) {
//        if (status.getCategory() == PNStatusCategory.PNConnectedCategory){
//            //complexData data = new complexData();
//            //data.fieldA = "Awesome";
//            //data.fieldB = 10;
//            pubnub.publish().channel("kozi").message("Hey").async(new PNCallback<PNPublishResult>() {
//                @Override
//                public void onResponse(PNPublishResult result, PNStatus status) {
//                    // handle publish response
//                }
//            });
//        }
    }
 
    @Override
    public void message(PubNub pubnub, PNMessageResult message) {
        System.out.println("REcieved "+message.getMessage().toString());
    }
 
    @Override
    public void presence(PubNub pubnub, PNPresenceEventResult presence) {
 
    }
    });
    
    }
    
    
    public void sendMessage(String msg,String channel){
      //      pubnub.subscribe()
    //.channels(Arrays.asList("0")); // subscribe to channels
    pubnub.publish()
    .message(msg)
    .channel("0")
    .async(new PNCallback<PNPublishResult>() {
        @Override
        public void onResponse(PNPublishResult result, PNStatus status) {
            // handle publish result, status always present, result if successful
            // status.isError to see if error happened
        }
    });
        

   // .execute();
    }
    
}
