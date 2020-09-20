import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args)throws IOException{
        int timeout = 500;
        ServerSocket serverSocket = new ServerSocket(4999);
        Socket socket = serverSocket.accept();

        System.out.println("Connection to client has been established!");

    }
}
