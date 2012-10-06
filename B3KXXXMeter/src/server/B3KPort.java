/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author nina
 */
public class B3KPort {
    B3KGate gate=new B3KGate();
    ServerSocket myS;
    private Thread innerThread;
    public B3KPort(){
        try{
        myS=new ServerSocket(2505);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void Start(){
        this.initThread();
        innerThread.start();
    }

    private void initThread () {
        this.innerThread = new Thread
            (
                new Runnable() {
                   public void run() {
                       while (true)
                       {
                           try
                           {
                               Socket accepted = myS.accept();
                               System.out.println("Accepting Socket on port 2505");
                               gate.acceptSocket(accepted);

                           }
                           catch (IOException ex)
                           {
                               //Logger.getLogger(PortWrapper.class.getName()).log(Level.SEVERE, null, ex);
                              System.out.println("Failed to accept Socket on port 2505");
                           }
                       }
                   }
                 }
            );
    }
}
