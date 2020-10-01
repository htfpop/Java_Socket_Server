/**
 * Christopher K. Leung
 * Project #1 - CS3800 Computer Networks
 * client.java - This program will take in 3 arguments: <ip address> <input data file> <output data file> and open a
 *               socket on 4999. It will parse the input data file into a byte array and send this array to the server
 *               It will then receive this byte array back and close all connections.
 *               This program also records the time (in nanoseconds).
 */

import java.net.*;
import java.io.*;
public class client {
    public static void main(String[] args)throws IOException{
        if(args.length < 3){
            System.out.println("[ERROR] Missing arguments!\r\nUsage: java client <Server IP> <Input Data File> <Output Data File>");
            System.out.println("Exiting Now..");
            System.exit(-1);
        }
        String serverIPAddr = args[0];
        String inputDataFile = args[1];
        String outputDataFile = args[2];

        File inFile = new File(inputDataFile);
        FileOutputStream clientReturnFile = new FileOutputStream(outputDataFile);
        byte[] returnFile = null;
        byte[] fileData = null;
        long roundTripTime = 0;
        long startRTT = 0;
        long endRTT = 0;

        Socket socket = null;
        try {
            socket = new Socket(serverIPAddr, 4999);
        }
        catch (SecurityException e){
            System.out.println("[CLIENT ERROR] Security manager blocked creation of socket\r\nExiting Program.");
            System.exit(-1);
        }
        catch (IllegalArgumentException e){
            System.out.println("[CLIENT ERROR] Could not create socket since port is out of range." +
                    "\r\nAcceptable ports: 0 and 65535 inclusive" +
                    "\r\nExiting Program.");
            System.exit(-1);
        }

        OutputStream outStream = socket.getOutputStream();
        DataOutputStream dOut = new DataOutputStream(outStream);
        InputStream inStream = socket.getInputStream();
        DataInputStream dIn = new DataInputStream(inStream);

        FileInputStream FIS = new FileInputStream(inFile);

        //Obtain file length and send to server
        long fileSize = inFile.length();
        fileData = new byte[(int)fileSize];
        returnFile = new byte[(int)fileSize];

        FIS.read(fileData);

        dOut.writeInt((int)fileSize);
        dOut.flush();

        //Using nanoTime to calculate RTT
        startRTT = System.nanoTime();
        dOut.write(fileData);
        dOut.flush();

        dIn.readFully(returnFile);
        endRTT = System.nanoTime();

        roundTripTime = endRTT - startRTT;
        System.out.println("roundTripTime = " + roundTripTime + " nanoseconds");

        clientReturnFile.write(returnFile);

        System.out.println("\r\nEnd of session! Closing socket");

        FIS.close();
        clientReturnFile.close();
        dOut.close();
        dIn.close();
        socket.close();
    }
}