import java.net.*;
import java.io.*;
public class client {
    public static void main(String[] args)throws IOException{
        if(args.length < 3){
            System.out.println("Error! Missing arguments!\r\nUsage: java client <Server IP> <Input Data File> <Output Data File>");
            System.out.println("Exiting Now..");
            System.exit(-1);
        }
        String serverIPAddr = args[0];
        String inputDataFile = args[1];
        String outputDataFile = args[2];

        File inFile = new File(inputDataFile);
        FileOutputStream clientReturnFile = new FileOutputStream(outputDataFile);


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

        OutputStream outStream = socket.getOutputStream();
        DataOutputStream dOut = new DataOutputStream(outStream);
        InputStream inStream = socket.getInputStream();
        DataInputStream dIn = new DataInputStream(inStream);

        FileInputStream FIS = new FileInputStream(inFile);

        long fileSize = inFile.length();
        byte[] fileData = new byte[(int)fileSize];
        FIS.read(fileData);

        dOut.writeInt((int)fileSize);
        dOut.flush();

        dOut.write(fileData);
        dOut.flush();

        int returnFileSize = dIn.readInt(); // return file size
        System.out.printf("Return file size is: %d\r\n",returnFileSize);
        if(returnFileSize != fileSize)
        {
            System.out.println("Error, server did not send back correct byte array size");
            System.exit(-1);
            socket.close();
        }
        byte[] returnFile = new byte[returnFileSize];
        dIn.readFully(returnFile);


        System.out.println("-----RETURN FILE BELOW-----");
        for(byte b : returnFile){
            System.out.printf("%c",b);
        }

        for(int i = 0; i < returnFile.length; i++) {
            if(returnFile[i] != fileData[i])
                System.out.printf("\r\nMISMATCH FOUND! Index %d\r\n", i);
        }

        clientReturnFile.write(returnFile);

        System.out.println("\r\nEnd of session! Closing socket");

        clientReturnFile.close();
        dOut.close();
        dIn.close();
        socket.close();
    }
}
