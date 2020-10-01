import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args)throws IOException{
        ServerSocket serverSocket = null;
        Socket socket = null;
        byte[] clientPayload = null;


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

        InputStream inStream = socket.getInputStream();
        DataInputStream dIn = new DataInputStream(inStream);
        OutputStream outStream = socket.getOutputStream();
        DataOutputStream dOut = new DataOutputStream(outStream);

        System.out.println("[NOTICE] Connection to client has been established!");
        String[] clientRawIP = socket.getInetAddress().toString().split("/");
        String clientIP = clientRawIP[1];
        System.out.println("[NOTICE] Client ip address: " + clientIP + " on port "+ socket.getLocalPort());

        //Obtaining file size from client
        int clientFileSize = dIn.readInt();
        if(clientFileSize <= 0){
            System.out.println("[ERROR] Client file size is less than or equal to zero. Please check client input! Exiting..");
            System.exit(-1);
        }

        System.out.printf("[RX] Receiving file size: %d bytes\r\n",clientFileSize);
        clientPayload = new byte[clientFileSize];

        //Trying to read data from buffer sent by client
        try{
            dIn.readFully(clientPayload);
            System.out.println("[RX] Receiving payload from client");
        }
        catch(NullPointerException e){
            System.out.println("[ERROR] Null pointer exception thrown when trying to read data input stream. Exiting..");
            System.exit(-1);
        }
        catch(EOFException e){
            System.out.println("[ERROR] Reached EOF before reading all of the bytes. Exiting..");
            System.exit(-1);
        }

        //Sending Over byte array to client
        dOut.write(clientPayload);
        dOut.flush();
        System.out.println("[TX] Transmitting payload to client");

        System.out.println("[NOTICE] Server has finished its procedures. Closing I/O Streams and closing socket..");

        dOut.close();
        dIn.close();
        socket.close();
        serverSocket.close();
    }
}
//Writing length back to client
//        dOut.writeInt(clientPayload.length);
//        dOut.flush();
//        System.out.println("[TX] Transmitting file size to client");