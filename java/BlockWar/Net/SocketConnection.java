package BlockWar.Net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class SocketConnection implements Runnable {

    private ArrayList<ClientHandler> clientsHandlers;
    private  ClientHandler clientHandler;
    private ServerSocket listener;

    public SocketConnection(ArrayList<ClientHandler> clientsHandlers) throws IOException {
        this.clientsHandlers = clientsHandlers;
    }
    public ArrayList<ClientHandler> getClientsHandlers() {
        return clientsHandlers;
    }


    public void connect() throws IOException, InterruptedException {

        ServerUtil.s.acquire(1);
        Socket client;
        if (clientsHandlers.size() >= 2){
            try{
                if (clientsHandlers.size() < 4) {
                    listener = new ServerSocket(ServerRoutine.PORT);
                    listener.setSoTimeout(2000);

                    client = listener.accept(); // bloccante
                    listener.close();
                    System.out.println("[SERVER] Connected!");
                    System.out.println("Waiting for players...");
                    clientHandler = new ClientHandler(client);
                    clientsHandlers.add(clientHandler);
                    ServerUtil.t.release(1);
                }else {
                    if (ServerRoutine.onetimeSocket){
                        System.err.println("Basta connessioni.");
                        ServerRoutine.onetimeSocket = !ServerRoutine.onetimeSocket;
                        CountDown.setInterval(0);
                    }
                    ServerUtil.t.release(1);
                }
            } catch (java.io.InterruptedIOException e){
                listener.close();
                ServerUtil.t.release(1);
            }
            catch (SocketException | NullPointerException ignored) { }
        } else {
            try{
                listener = new ServerSocket(ServerRoutine.PORT);
                listener.setSoTimeout(60000);
                client = listener.accept();
                listener.close();
                System.out.println("[SERVER] Connected!");
                System.out.println("Waiting for players...");
                clientHandler = new ClientHandler(client);
                clientsHandlers.add(clientHandler);
                ServerUtil.t.release(1);
            } catch (java.io.InterruptedIOException e){
                listener.close();
                System.err.println("No players found. try later.");
                System.exit(0);
            } catch (SocketException | NullPointerException ignored) { }
        }




    }

    @Override
    public void run() {
        while (CountDown.getInterval() > 0){
            try {
                connect();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
