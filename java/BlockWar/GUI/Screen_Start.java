package BlockWar.GUI;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import BlockWar.Net.Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Screen_Start implements Runnable{

    private final Screen screen;
    private Client client;
    private final ArrayList<Window.Hint> hints;
    private final Label lbl_Title = new Label("Block War");
    private Button main_Button;
    private Button exit_Button;
    private Thread clientThread;

    public Screen_Start() throws IOException {

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.hints = new ArrayList<>();
        this.hints.add(Window.Hint.CENTERED);
    }

    @Override
    public void run() {

        try {
            this.screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Panel panel = new Panel();
        panel.addComponent(this.lbl_Title);
        Screen_Start ThisScreen = this;

        this.main_Button = new Button("Join a Game",new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Client(ThisScreen);
                    lbl_Title.setText("Ricerca giocatori...");
                    exit_Button.setVisible(false);
                    main_Button.setVisible(false);
                    clientThread = new Thread(client);
                    clientThread.start();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }

        }).addTo(panel);

        this.exit_Button = new Button("Quit", new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }).addTo(panel);

        BasicWindow window = new BasicWindow();
        window.setComponent(panel);
        window.setHints(this.hints);

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK_BRIGHT));
        gui.addWindowAndWait(window);
    }

    public Screen getScreen() {
        return screen;
    }

    public static void main(String[] args) throws IOException {
        Client.IP = args[0];
        Client.PORT = Integer.parseInt(args[1]);
        Screen_Start start = new Screen_Start();
        Thread startThread = new Thread(start);
        startThread.start();
    }
}
