/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import net.B3KRequest;
import server.B3KGate;
import util.Action;

/**
 *
 * @author nina
 */
public class NewApplet extends JApplet {
    Socket newS;
    JTabbedPane mainPane=new JTabbedPane();
    JPanel hq=new JPanel();
    JPanel xen=new JPanel();
    JPanel jun=new JPanel();

    Meter sonic=new Meter(this.getClass().getResource("/img/sonic.gif"),0);
    Meter indarian=new Meter(this.getClass().getResource("/img/indarian.gif"),0);
    Meter sinner=new Meter(this.getClass().getResource("/img/sinner.gif"),0);

    Meter sofa=new Meter(this.getClass().getResource("/img/sofa.gif"),0);
    Meter alex=new Meter(this.getClass().getResource("/img/alex.gif"),0);
    Meter tigra=new Meter(this.getClass().getResource("/img/tigra.gif"),0);
    Meter cbip=new Meter(this.getClass().getResource("/img/CbIP.gif"),0);

    Meter lipton=new Meter(this.getClass().getResource("/img/lipton.gif"),0);
    Meter alyona=new Meter(this.getClass().getResource("/img/alyona.gif"),0);
    Meter liza=new Meter(this.getClass().getResource("/img/liza.gif"),0);
    Meter mixa=new Meter(this.getClass().getResource("/img/mixa.gif"),0);
    Meter pe4enie=new Meter(this.getClass().getResource("/img/pe4enie.gif"), 0);

    List<Meter> meters=new ArrayList<Meter>();

    {
        meters.add(alex);
        meters.add(sonic);
        meters.add(indarian);
        meters.add(sinner);
        meters.add(cbip);
        meters.add(tigra);
        meters.add(sofa);

        meters.add(lipton);
        meters.add(alyona);
        meters.add(liza);
        meters.add(mixa);
    }

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    public void init() {
        try{
            newS=new Socket("localhost", 2505);
            B3KRequest br=new B3KRequest();

            DataOutputStream outToServer = new DataOutputStream(newS.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(newS.getInputStream()));
            outToServer.writeBytes(br.reqString + "\n");
            String s = inFromServer.readLine();
            if(s.equals("") || s.equals("\n"));else{
            HashMap<String,Integer> htr=B3KRequest.getData(s);
            loadValues(htr);
            refillMeters();
            }
        }catch(Exception e){e.printStackTrace();}

        this.setLayout(null);
        placeItAll();
        mainPane.setSize(500, 500);
        for(Meter m:meters){
            m.addAction(new Action() {

                public void run() {
                    refillMeters();
                    mainPane.repaint();
                    try{
                        newS=new Socket("localhost", 2505);
                        B3KRequest br=new B3KRequest(collectValues());
                        br.send(newS);
                        newS.close();
                        newS=null;
                    }catch(Exception e){e.printStackTrace();}
                }
            });
        }
        // TODO start asynchronous download of heavy resources
    }

    private void placeItAll(){
        this.add(mainPane);
        mainPane.add(hq);
        mainPane.add(xen);
        mainPane.setTitleAt(0, "HQ");
        mainPane.add(xen);
        mainPane.setTitleAt(1, "Xen");
        mainPane.add(jun);
        mainPane.setTitleAt(2, "Jun");

        hq.add(sonic);
        hq.add(indarian);
        hq.add(sinner);

        xen.add(sofa);
        xen.add(alex);
        xen.add(tigra);
        xen.add(cbip);

        jun.add(lipton);
        jun.add(mixa);
        jun.add(alyona);
        jun.add(liza);
        jun.add(pe4enie);
    }

    private void refillMeters(){
        int max=0;
        for(int j=0;j<meters.size();j++){
            if(meters.get(j).getValue()>max)max=meters.get(j).getValue();
        }

        for(int j=0;j<meters.size();j++){
            meters.get(j).setMaximum(max);
            meters.get(j).repaint();
        }
    }

    private HashMap<String,Integer> collectValues(){
        HashMap<String,Integer> tosend=new HashMap<String, Integer>();
        tosend.put("sonic",sonic.getValue());
        tosend.put("sinner",sinner.getValue());
        tosend.put("indarian",indarian.getValue());
        tosend.put("tigra",tigra.getValue());

        tosend.put("sofa",sofa.getValue());
        tosend.put("alex",alex.getValue());
        tosend.put("cbip",cbip.getValue());

        tosend.put("mixa",mixa.getValue());
        tosend.put("liza",liza.getValue());
        tosend.put("lipton",lipton.getValue());
        tosend.put("alyona",alyona.getValue());
        tosend.put("pe4enie",pe4enie.getValue());

        return tosend;
    }

    private void loadValues(HashMap<String,Integer> ht){
        sonic.addPoints(ht.get("sonic"));
        sinner.addPoints(ht.get("sinner"));
        indarian.addPoints(ht.get("indarian"));
        tigra.addPoints(ht.get("tigra"));

        cbip.addPoints(ht.get("cbip"));
        sofa.addPoints(ht.get("sofa"));
        alex.addPoints(ht.get("alex"));
        
        mixa.addPoints(ht.get("mixa"));
        liza.addPoints(ht.get("liza"));
        lipton.addPoints(ht.get("lipton"));
        alyona.addPoints(ht.get("alyona"));
        pe4enie.addPoints(ht.get("pe4enie"));
    }
}
    // TODO overwrite start(), stop() and destroy() methods

