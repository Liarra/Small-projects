/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author nina
 */
public class B3KRequest {
    public String reqString;
    final static String div="%";

    public static HashMap<String,Integer> getData(String req){
        HashMap<String,Integer> ret=new HashMap<String, Integer>();
        StringTokenizer tk=new StringTokenizer(req, "\t");
        while(tk.hasMoreTokens()){
            StringTokenizer tk0=new StringTokenizer(tk.nextToken(), div);
            ret.put(tk0.nextToken(), Integer.parseInt(tk0.nextToken()));
        }

        return ret;
    }

    public static String getString(HashMap<String,Integer> data){
        String ret="";
        Iterator it=data.keySet().iterator();
        while(it.hasNext()){
            String name=it.next().toString();
            ret+=name+div;
            ret+=data.get(name)+"\t";
        }
        return ret;
    }

    public void send(Socket socket) throws IOException{
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToServer.writeBytes(this.reqString + "\n");
            String s = inFromServer.readLine();
    }

    public String receive(Socket socket) throws IOException{
        InputStream in=socket.getInputStream();
        Scanner sc=new Scanner(new InputStreamReader(in));
        String ret="";
        while(sc.hasNext()){
            ret+=sc.next();
        }
        return ret;
    }

    public String sendAndReceive(Socket socket) throws IOException{
        OutputStream out=socket.getOutputStream();
        InputStream in=socket.getInputStream();
        PrintWriter pw = new PrintWriter(out);
        pw.print(reqString);
        pw.flush();
        
        BufferedReader br =  new BufferedReader(new InputStreamReader(in));
        String ret="";
        String r=null;
        while((r=br.readLine())!=null)ret+=r;

        pw.close();
        br.close();
        return ret;
    }

    public B3KRequest(HashMap<String,Integer> data){
        reqString=getString(data);
    }

    public B3KRequest(){
        reqString="0";
    }
}
