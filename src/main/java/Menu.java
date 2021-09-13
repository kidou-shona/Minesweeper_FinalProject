import entity.LEVEL;
import entity.Rating;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Menu extends JFrame {
    String name;
    private int size = 30;

    public void setName(String name) {
        this.name = name;
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
        String name = inputName(menu);
        return true;
    }

    public void startGame() {
        int toughness = chooseLevel();

        Board newBoard = new Board(name, toughness);
        newBoard.create(newBoard, size);
    }

    private String inputName(Menu menu) {
        InputEngine inputEngine = new InputEngine(menu);
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Enter your name : ");
        JTextField name = new JTextField(30);

        panel.add(label);
        name.addActionListener(inputEngine);
        panel.add(name);
        menu.setContentPane(panel);
        this.setVisible(true);

        return name.toString();
    }

    public int chooseLevel() {
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

        return toughness;
    }
}

