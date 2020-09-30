import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

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
        String[] clientRawIP = socket.getInetAddress().toString().split("/");
        String clientIP = clientRawIP[1];
        System.out.println("Client ip address: " + clientIP + " on port "+ socket.getLocalPort());

        InputStream inStream = socket.getInputStream();
        DataInputStream dIn = new DataInputStream(inStream);
        OutputStream outStream = socket.getOutputStream();
        DataOutputStream dOut = new DataOutputStream(outStream);

        int fileSize = dIn.readInt();
        System.out.printf("filesize %d\r\n",fileSize);
        byte[] socketFile = new byte[fileSize];

        dIn.readFully(socketFile);

        //todo send back socketFile using dOut.WriteInt(socketFile.length) dOut.write(socketFile);
        //writing byte array length over
        dOut.writeInt(socketFile.length);
        dOut.flush();

        //Sending Over byte array
        dOut.write(socketFile);
        dOut.flush();

        dOut.close();
        dIn.close();
        socket.close();
        serverSocket.close();

    }
}



//dIn.readFully(socketFile);
//        for(int i = 0; i< socketFile.length; i++){
//            System.out.printf("",socketFile[i]);
//        }

//socketFile = dIn.readAllBytes();

//String message = dIn.readUTF();
//System.out.println("Message from client: "+ message);

//String s = new String(socketFile, StandardCharsets.UTF_8);

//        for (byte b : socketFile) {
//            System.out.printf("%c ", b);
//        }