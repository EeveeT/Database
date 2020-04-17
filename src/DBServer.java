import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DBServer
{
    final static char EOT = 4;

    public DBServer(int portNumber){

        try{
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while (true) acceptNextConnection(ss);
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }
    }

    private void acceptNextConnection(ServerSocket ss) {
        try{
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader((new InputStreamReader(socket.getInputStream())));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in, out);
            out.close();
            in.close();
            socket.close();
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }

    }

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException {
        String line = in.readLine();
        out.write(line);
        out.write('\n');
        out.write(EOT);
        out.write('\n');
    }

    public static void main(String[] args){

        new DBServer(8888);
    }

}