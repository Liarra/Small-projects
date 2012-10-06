/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import util.Action;

/**
 *
 * @author nina
 */
public class Meter extends JPanel{
    int points=0;
    Icon pic;
    JProgressBar meter=new JProgressBar();
    JButton ten=new JButton("+10"),
            tf=new JButton("+25"),
            ff=new JButton("+50"),
            hun=new JButton("+100");
    JButton cancel=new JButton("X");
    JTextField plus=new JTextField(5);
    ArrayList<Integer> history=new ArrayList<Integer>();
    Action action;
    JLabel scoreLabel=new JLabel();

    public void addPoints(int X){
        history.add(X);
        points+=X;
        if(points>meter.getMaximum())
            meter.setMaximum(points);
        meter.setValue(points);
        scoreLabel.setText(points+"");
    }

    public void setMaximum(int i){
        meter.setMaximum(i);
        meter.repaint();
    }

    public void undoLast(){
        if(history.size()==2)return;
        int last=history.get(history.size()-1);
        points-=last;
        meter.setValue(points);
        scoreLabel.setText(points+"");
        history.remove(history.size()-1);
    }

    public Meter(URL iconpath,int points){
        pic=new ImageIcon(iconpath);
        this.addPoints(points);
        placeItAll();
        addThemHandlers();
    }
    
    public int getValue(){
        return points;
    }

    public void addAction(final Action ac){
        action=ac;
    }

    private void placeItAll(){
        JPanel BtnPanel=new JPanel();
        Dimension two=new Dimension(2, 2);
        plus.setSize(60, 10);
        plus.setPreferredSize(new Dimension(60, 10));
          // <editor-fold defaultstate="collapsed" desc=" F**king with button panel ">
        BtnPanel.setLayout(new BoxLayout(BtnPanel, BoxLayout.LINE_AXIS));
        BtnPanel.add(ten);
        BtnPanel.add(new Box.Filler(two, two, two));
        BtnPanel.add(tf);
        BtnPanel.add(new Box.Filler(two, two, two));
        BtnPanel.add(ff);
        BtnPanel.add(new Box.Filler(two, two, two));
        BtnPanel.add(hun);
        BtnPanel.add(new Box.Filler(two, two, two));
        BtnPanel.add(new JLabel("+"));
        BtnPanel.add(new Box.Filler(two, two, two));
        BtnPanel.add(plus);
        BtnPanel.add(new Box.Filler(two, two, two));
        BtnPanel.add(cancel);
        //</editor-fold>
        JPanel meterPanel=new JPanel();
        meterPanel.setLayout(new BoxLayout(meterPanel, BoxLayout.LINE_AXIS));
        meterPanel.add(scoreLabel);
        meterPanel.add(new Box.Filler(two, two, two));
        meterPanel.add(meter);
        JPanel vertPanel=new JPanel();
        vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.PAGE_AXIS));
        vertPanel.add(meterPanel);
        vertPanel.add(new Box.Filler(two, two, two));
        vertPanel.add(new Box.Filler(two, two, two));
        vertPanel.add(BtnPanel);
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        this.add(new JLabel(pic));
        this.add(vertPanel);
    }

    private void addThemHandlers(){
        ten.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPoints(10);
                action.run();}
        });

        tf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {addPoints(25);action.run();}
        });

        ff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {addPoints(50);action.run();}
        });

        hun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {addPoints(100);action.run();}
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {undoLast();action.run();}
        });

        plus.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    int toplus=Integer.parseInt(plus.getText());
                    plus.setText("");
                    addPoints(toplus);
                    action.run();
                }
            }
        });
    }
}
