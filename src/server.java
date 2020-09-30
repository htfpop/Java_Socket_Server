import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class server {
    public static void main(String[] args)throws IOException{
        ServerSocket serverSocket = null;
        Socket socket = null;
        byte[] socketFile = new byte[0];

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
        String[] clientRawIP = socket.getInetAddress().toString().split("/");
        String clientIP = clientRawIP[1];
        System.out.println("Client ip address: " + clientIP + " on port "+ socket.getLocalPort());

        InputStream inStream = socket.getInputStream();
        DataInputStream dIn = new DataInputStream(inStream);

        int fileSize = dIn.readInt();
        System.out.printf("filesize %d",fileSize);
        if(fileSize > 0) {
            socketFile = new byte[fileSize];
        }

        socketFile = dIn.readAllBytes();

        //dIn.readFully(socketFile);
        String s = new String(socketFile, StandardCharsets.UTF_8);

        for(int i = 0; i<socketFile.length; i++){
            System.out.printf("%d ",socketFile[i]);
        }

//        for(int i = 0; i< socketFile.length; i++){
//            System.out.printf("",socketFile[i]);
//        }

        //String message = dIn.readUTF();
        //System.out.println("Message from client: "+ message);

        socket.close();
        serverSocket.close();

    }
}
