import java.net.*;
        import java.io.*;
public class client {
    public static void main(String[] args)throws IOException{
        try {
            Socket socket = new Socket("localhost", 4999);
        }
        catch (Exception e){
            System.out.println("ERROR: Could not establish new connection to server");
        }

    }
}
