/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import net.B3KRequest;

/**
 *
 * @author nina
 */
public class B3KGate {
    public void acceptSocket(Socket s){
        try{
            BufferedReader inFromClient =new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream outToClient =new DataOutputStream(s.getOutputStream());
            String req= inFromClient.readLine();

            DataLoader dl=new DataLoader();
            if(req.equals("0")){
                System.err.println("Got request");
                outToClient.writeBytes(dl.read());
            }
            else if((req.startsWith("GET /"))){
                 System.err.println("Got HTML request");
                 String reqLine=req.split(" ")[1];
                 if(reqLine.equals("/"))
                outToClient.writeBytes(dl.readHTML());
                 else
                     outToClient.writeBytes(dl.readFile(reqLine));
            }
            else {
                


                System.err.println("Got data");
                dl.Write(req);}


            inFromClient.close();
            outToClient.close();
            s.close();
            s=null;
        }catch(Exception e){e.printStackTrace();}
    }

}
