import Parsers.*;
import Query.Command;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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

            try {
                while (true) {
                    processNextCommand(in, out);
                }
            }
            catch(Exception e){e.printStackTrace();}

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
        try {
            List<Token> tokens = new Tokeniser(line).tokenise();
            System.out.println(tokens);
            Command command = new Parser(tokens).parseCommand();
            try{
                out.write(command.run(null));
            }
            catch (NullPointerException e) {
                out.write("Error");
            }

            //out.write("Input successfully parsed \n");

        } catch (UnexpectedCharacterException | UnexpectedTokenException ue) {
            ue.printStackTrace();
           // out.write("Error: Invalid Input");
//todo: change this bit
            System.out.println(ue.toString());

        } finally {
            out.write("\n" + EOT + "\n");
            out.flush();
        }
    }

    public static void main(String[] args){

        new DBServer(8888);
    }

}
