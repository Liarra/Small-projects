/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package charcalculator;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author nina
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char[] operators=new char[]{'+','-','*','/','\\','%'};
        char[] smileys=new char[]{')','('};

        print("-------~~~~Char Calculator~~~~-------");
        print("------~~~~~~By AlienTiger~~~~~~------");

        do{
            try{
                String formula=read();
                String result=compute(formula, 0, formula.length()-1);
                print("_______________");
                print(result);
                print("\n");

            }catch(Exception e){e.printStackTrace();}
        }while(true);

    }

    static void print(String s){
        System.out.println(s);
    }

    static String read() throws IOException{
        Scanner in=new Scanner(System.in);
        String s= in.nextLine();
        //in.close();
        return s;
    }
    
    static String compute (String s, int start, int end){
        int smiley1=0;
        do{
        for(int i=start;i<end;i++){

            if(s.charAt(i)=='(')smiley1=i;
            if(s.charAt(i)==')'){
                String s0=compute(s,smiley1+1,i);
                String torep=s.substring(smiley1, i+1);
                int del=torep.length();
                s=s.replace(torep, s0);
                end-=del;
            }
        }


        for(int i=start;i<end;i++)
            if(s.charAt(i)=='*'){
                String torep=s.substring(i-1, i+2);
                char c1=(char) ((s.charAt(i - 1)) * (s.charAt(i + 1)));
                s=s.replace(torep, String.valueOf(c1));
                end-=2;
            }

        for(int i=start;i<end;i++)
            if(s.charAt(i)=='/'){
                String torep=s.substring(i-1, i+2);
                char c1=(char) ((s.charAt(i - 1)) / (s.charAt(i + 1)));
                s=s.replaceFirst(torep, String.valueOf(c1));
                end-=2;
            }

            for(int i=start;i<end;i++)
                if(s.charAt(i)=='+'){
                    String torep=s.substring(i-1, i+2);
                    char c1=(char) ((s.charAt(i - 1)) + (s.charAt(i + 1)));
                    s=s.replace(torep, String.valueOf(c1));
                    end-=2;
                }

                for(int i=start;i<end;i++)
                if(s.charAt(i)=='-'){
                    String torep=s.substring(i-1, i+2);
                    char c1=(char) ((s.charAt(i - 1)) - (s.charAt(i + 1)));
                    s=s.replace(torep, String.valueOf(c1));
                    end-=2;
                }

        for(int i=start;i<end;i++)
                if(s.charAt(i)=='%'){
                    String torep=s.substring(i-1, i+2);
                    char c1=(char) ((s.charAt(i - 1)) % (s.charAt(i + 1)));
                    s=s.replace(torep, String.valueOf(c1));
                    end-=2;
                }

    }while(end-start>1);

        int ret=end-start>0?(end):end+1;
        return s.substring(start, ret);
    }

}
