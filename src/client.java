import java.net.*;
import java.io.*;
public class client {
    public static void main(String[] args)throws IOException{
        if(args.length < 2){
            System.out.println("Error! Missing arguments!\r\nUsage: java client <Server IP> <Input Data File> <Output Data File>");
            System.out.println("Exiting Now..");
            System.exit(-1);
        }
        String serverIPAddr = args[0];
        String argFile = args[1];
        File inFile = new File(argFile);


        Socket socket = null;
        try {
            socket = new Socket(serverIPAddr, 4999);
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

        OutputStream outStream = socket.getOutputStream();
        DataOutputStream dOut = new DataOutputStream(outStream);

        FileInputStream FIS = new FileInputStream(inFile);


        long fileSize = inFile.length();
        byte[] fileData = new byte[(int)fileSize];
        fileData = FIS.readAllBytes();

        dOut.writeInt((int)fileSize);
        dOut.flush();

        dOut.write(fileData);
        dOut.flush();


        //dOut.writeUTF("Hello this is the client!");
        //dOut.flush();
        dOut.close();

        System.out.println("End of session! Closing socket");
        socket.close();
    }
}
