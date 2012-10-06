/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

/**
 *
 * @author nina
 */
public class Server {
    B3KPort portw;
    B3KGate gate;

    public static void main(String[] args){
        B3KPort portw=new B3KPort();
        portw.Start();
    }

}
