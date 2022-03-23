package BlockWar.Net;

public class Server {

        public static void main(String[] args) {
            ServerRoutine.PORT = Integer.parseInt(args[0]);

            ServerRoutine serverRoutine = new ServerRoutine();
            Thread srThread = new Thread(serverRoutine);
            srThread.start();
        }
}