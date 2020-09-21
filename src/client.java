import java.net.*;
import java.io.*;
public class client {
    public static void main(String[] args)throws IOException{
        Socket socket = null;
        try {
            socket = new Socket("localhost", 4999);
        }
        catch (SecurityException e){
            System.out.println("CLIENT ERROR: Security manager blocked creation of socket\r\nExiting Program.");
            System.exit(-1);
        }
        catch (IllegalArgumentException e){
            System.out.println("CLIENT ERROR: Could not create socket since port is out of range." +
                               "\r\nAcceptable ports: 0 and 65535 inclusive" +
                               "\r\nExiting Program.");
            System.exit(-1);
        }
        catch (IOException e){
            System.out.println("CLIENT ERROR: Could not create socket (IOException Throw)\r\nExiting Program.");
            System.exit(-1);
        }

    }
}
