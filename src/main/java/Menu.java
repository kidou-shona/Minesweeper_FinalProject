import entity.LEVEL;
import entity.Rating;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Menu extends JFrame {

    Rating rating = new Rating();
    private int size = 30;

    public void set(String username) {
        rating.setUsername(username);
        chooseLevel();
    }

    public Menu() {
        this.setSize(400, 100);
        this.setTitle("Input");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    public void start() {
        Menu menu = new Menu();
        while (menu.createWelcomeTable(menu)) {
            menu.playMusic(menu);
        }
    }

    private void playMusic(Menu menu) {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/Luciel_707/Desktop/minesweeper.wav");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(550000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean createWelcomeTable(Menu menu) {
        InputEngine inputEngine = new InputEngine(menu);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter your name : ");
        JTextField text = new JTextField(30);

        panel.add(label);
        text.addActionListener(inputEngine);
        panel.add(text);
        menu.setContentPane(panel);
        this.setVisible(true);
        return true;
    }

    public void chooseLevel() {
        int toughness = 1;
        Object[] options = {LEVEL.EASY, LEVEL.MODERATE, LEVEL.HARD};
        toughness = JOptionPane.showOptionDialog(null,
                "What's your difficulty level ?", "Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (toughness == -1)
            System.exit(0);
        Board newBoard = new Board(toughness);
        newBoard.create(newBoard, size);
    }
}

