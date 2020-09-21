import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args)throws IOException{
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
             serverSocket = new ServerSocket(4999);
             socket = serverSocket.accept();
        }
        catch (SecurityException e){
            System.out.println("SERVER ERROR: Security manager blocked creation of socket\r\nExiting Program.");
            System.exit(-1);
        }
        catch (IllegalArgumentException e){
            System.out.println("SERVER ERROR: Could not create socket since port is out of range." +
                               "\r\nAcceptable ports: 0 and 65535 inclusive" +
                               "\r\nExiting Program.");
            System.exit(-1);
        }
        catch (IOException e){
            System.out.println("SERVER ERROR: Could not create socket (IOException Throw)\r\nExiting Program.");
            System.exit(-1);
        }


        System.out.println("Connection to client has been established!");

        socket.close();

    }
}
