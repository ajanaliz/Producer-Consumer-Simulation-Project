package utils;

import java.io.IOException;

/**
 * Created by Ali J on 2/26/2015.
 */
public class GetInput {
    //Read a Character
    public static char ReadChar() throws IOException {
        return (char) System.in.read();
    }

    //Read Multiple Characters
    public static String ReadString( int howmany )  {
        String string = "";
        int k;
        for (k = 0; k < howmany; k++){
            try {
                string += (char)System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return string;
    }

    //Read a Line
    public static String ReadLine()  {
        String string = "";
        char c;
        try {
            while ((c=(char)System.in.read()) != '\n')
                string += c;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    //Read an Integer
    public static int ReadInt()  {
        String string = "";
        char c;
        while (true) {
            try {
                c = (char) System.in.read();
                if ((c =='\n' || (c==' '))){
                    break;
                }
                string += c;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Integer n = new Integer(string.trim());
        return n.intValue();
    }

    //Read a Float
    public static float ReadFloat()  {
        String string = "";
        char c;
        while (true){
            try {
                c = (char) System.in.read();
                if ( (c == '\n') || (c == ' '))
                    break;
                string += c;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Float n = new Float(string.trim());
        return n.floatValue();
    }

    //Read a Double
    public static double ReadDouble() {
        String string = "";
        char c;
        while (true) {
            try {
                c = (char) System.in.read();
                if ((c == '\n') || (c == ' '))
                    break;
                string += c;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Double n = new Double(string.trim());
        return n.doubleValue();
    }

}
