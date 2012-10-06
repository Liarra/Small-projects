/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Locale;
/**
 *
 * @author nina
 */
public class DataLoader {
    String filePath="/home/nina/B3KFileLogs/log.txt";
    String htmlPath="/home/nina/B3KFileLogs/NewApplet.html";

    public String read(){
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
                return "";
            }
            FileReader fr=new FileReader(file);
            BufferedReader ds=new BufferedReader(fr);
            String s=ds.readLine();
            ds.close();
            return s;
        } catch (IOException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public void Write(String s){
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fr=new FileWriter(file);
            fr.write(s);
            fr.flush();
            fr.close();
    }
        catch (IOException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    public String readHTML(){
       try {
           File file=new File(htmlPath);
            InputStream is = new FileInputStream(file);
        long length = file.length(); // Get the size of the file

            FileReader fr=new FileReader(file);
            BufferedReader ds=new BufferedReader(fr);
            String s=ds.readLine();
            String ret="";
            ret+=s;
            while((s=ds.readLine())!=null){
                ret+=s;
            }
            ds.close();
            is.close();
            
            java.text.SimpleDateFormat gmtFrmt = new java.text.SimpleDateFormat( "E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

            String retS= "HTTP/1.0 200 OK\r\n"
                    +"Date: " + gmtFrmt.format(new Date()) + "\r\n"
                   + "Content-Type: text/html\r\n"
                   + "Content-Length: " + length+"\r\n"
                   + "\r\n"+ret;

            System.out.print(retS);
            return retS;
        } catch (IOException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }


    public String readFile(String file) throws FileNotFoundException, IOException{
        file="/home/nina/B3KFileLogs"+file;
        File f=new File(file);
        if(!f.exists())return "";
        InputStream is = new FileInputStream(file);
        
        long length = file.length(); // Get the size of the file
            byte[] bytes = new byte[(int)length]; // Create the byte array to hold the data
            // <editor-fold defaultstate="collapsed" desc=" Reading in the bytes ">
            //
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc=" Ensure all the bytes have been read in ">
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file ");
        }
        // </editor-fold>
            is.close();

             // <editor-fold defaultstate="collapsed" desc=" Determining MIME type ">
        String mime = null;
        int dot = f.getCanonicalPath().lastIndexOf( '.' );
        if ( dot >= 0 ) mime = (String) MIME.theMimeTypes.get( f.getCanonicalPath().substring( dot + 1 ).toLowerCase());

        if ( mime == null ) mime = MIME.MIME_DEFAULT_BINARY;
        // </editor-fold>

             java.text.SimpleDateFormat gmtFrmt = new java.text.SimpleDateFormat( "E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);


            String retS= "HTTP/1.0 200 OK\r\n"
                    +"Date: " + gmtFrmt.format(new Date()) + "\r\n"
                   + "Content-Type: "+mime+"\r\n"
                   + "Content-Length: " + length+"\r\n"
                   + "\r\n"+bytes;

            return retS;
        }

    }

