import entity.LEVEL;
import entity.Rating;
import databaseNew.RatingRepository;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;

public class Board extends JFrame {

    public static final int MAGIC_SIZE = 30; //pixels

    String name;
    LEVEL level;

    RatingRepository ratingRepository = new RatingRepository();


    private JButton[][] buttons;  // The Grid buttons
    private JPanel panel1;  // Top panel containing labels and a smile button
    private JPanel panel2;  // Bottom panel containing the grid of buttons
    private JLabel flagsLabel;  // Number of flags remaining to be used
    private JButton smileButton;
    private JLabel timeLabel;

    private int noOfMines = 0;
    private int[][] mineLand;
    private boolean[][] revealed;  // Whether the button has been clicked
    private int noOfRevealed;  // How many of them?
    private boolean[][] flagged;  // Or the got flagged?
    private boolean smiling;

    private Image smiley;
    private Image newSmiley;
    private Image flag;
    private Image newFlag;
    private Image mine;
    private Image newMine;
    private Image dead;
    private Image newDead;

    public Board(String name, int toughness) {
        this.name = name;
        int size = 30;
        this.level = LEVEL.values()[toughness];
        noOfMines = 1; /*size * (1 + toughness * 2);*/
        this.setSize(size * MAGIC_SIZE, size * MAGIC_SIZE + 50);
        this.setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    public void create(Board frame, int size) {

        GameEngine gameEngine = new GameEngine(frame);
        MyMouseListener myMouseListener = new MyMouseListener(frame);
        JPanel mainPanel = new JPanel();

        panel1 = new JPanel();
        panel2 = new JPanel();

        this.noOfRevealed = 0;

        revealed = new boolean[size][size];
        flagged = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }

        try {
            smiley = ImageIO.read(getClass().getResource("images/Smiley.png"));
            newSmiley = smiley.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);

            dead = ImageIO.read(getClass().getResource("images/dead.png"));
            newDead = dead.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);

            flag = ImageIO.read(getClass().getResource("images/flag.png"));
            newFlag = flag.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);

            mine = ImageIO.read(getClass().getResource("images/mine.png"));
            newMine = mine.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        BoxLayout g1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(g1);

        JLabel jLabel1 = new JLabel(" Flags = ");
        jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabel1.setHorizontalAlignment(JLabel.LEFT);
        flagsLabel = new JLabel("" + this.noOfMines);

        smiling = true;
        smileButton = new JButton(new ImageIcon(newSmiley));
        smileButton.setPreferredSize(new Dimension(MAGIC_SIZE, MAGIC_SIZE));
        smileButton.setMaximumSize(new Dimension(MAGIC_SIZE, MAGIC_SIZE));
        smileButton.setBorderPainted(true);
        smileButton.setName("smileButton");
        smileButton.addActionListener(gameEngine);

        JLabel jLabel2 = new JLabel(" Time :");
        timeLabel = new JLabel("0");
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);

        panel1.add(jLabel1);
        panel1.add(flagsLabel);
        panel1.add(Box.createRigidArea(new Dimension((size - 1) * 15 - 80, 50)));
        panel1.add(smileButton, BorderLayout.PAGE_START);
        panel1.add(Box.createRigidArea(new Dimension((size - 1) * 15 - 85, 50)));
        panel1.add(jLabel2);
        panel1.add(timeLabel);

        GridLayout g2 = new GridLayout(size, size);
        panel2.setLayout(g2);

        buttons = new JButton[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(12, 12));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setName(i + " " + j);
                buttons[i][j].addActionListener(gameEngine);
                buttons[i][j].addMouseListener(myMouseListener);
                panel2.add(buttons[i][j]);
            }
        }
        // Both panels done

        mainPanel.add(panel1);
        mainPanel.add(panel2);
        frame.setContentPane(mainPanel);
        this.setVisible(true);

        setMines(size);

        TimeThread timer = new TimeThread(this);
        timer.start();
    }

    private void setMines(int size) {
        Random rand = new Random();

        mineLand = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mineLand[i][j] = 0;
            }
        }

        int count = 0;
        int xPoint;
        int yPoint;
        while (count < noOfMines) {
            xPoint = rand.nextInt(size);
            yPoint = rand.nextInt(size);
            if (mineLand[xPoint][yPoint] != -1) {
                mineLand[xPoint][yPoint] = -1;  // -1 represents bomb
                count++;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mineLand[i][j] == -1) {
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            try {
                                if (mineLand[i + k][j + l] != -1) {
                                    mineLand[i + k][j + l] += 1;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public void timer() {
        int time = getTimer();
        ++time;
        this.timeLabel.setText(Integer.toString(time) + " s");
    }

    public int getTimer() {
        String[] time = this.timeLabel.getText().split(" ");
        return Integer.parseInt(time[0]);
    }

    public void changeSmile() {
        if (smiling) {
            smileButton.setIcon(new ImageIcon(newDead));
        } else {
            smileButton.setIcon(new ImageIcon(newSmiley));
        }
        smiling = !smiling;
    }

    public void buttonRightClicked(int x, int y) {
        if (!revealed[x][y]) {
            if (flagged[x][y]) {
                buttons[x][y].setIcon(null);
                flagged[x][y] = false;
                int old = Integer.parseInt(this.flagsLabel.getText());
                ++old;
                this.flagsLabel.setText("" + old);
            } else if (Integer.parseInt(this.flagsLabel.getText()) > 0) {
                buttons[x][y].setIcon(new ImageIcon(newFlag));
                flagged[x][y] = true;
                int old = Integer.parseInt(this.flagsLabel.getText());
                --old;
                this.flagsLabel.setText("" + old);
            }
        }
    }  //!!! cell class (optional)

    private boolean gameWon() {
        return (this.noOfRevealed) == (Math.pow(this.mineLand.length, 2) - this.noOfMines);
    }

    private void handleGameWin() throws SQLException {
        int yourTime = getTimer();
        Rating rating = new Rating(0, name, level, null, yourTime);
        try {
            ratingRepository.addGameResultToDB(rating);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        int input = JOptionPane.showConfirmDialog(null, "Congratulations! You've Won! Your game time is: " + getTimer(), "Title", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        try {
            ArrayList<Rating> top10rating = ratingRepository.getAllRatingsFromDB(level);
            RatingOutput ratingsOutput = new RatingOutput(top10rating, level);
            ratingsOutput.setVisible(true);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void buttonClicked(int x, int y) {
        if (!revealed[x][y] && !flagged[x][y]) {
            revealed[x][y] = true;

            switch (mineLand[x][y]) {
                case -1:
                    try {
                        buttons[x][y].setIcon(new ImageIcon(newMine));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    buttons[x][y].setBackground(Color.RED);
                    try {
                        smileButton.setIcon(new ImageIcon(newDead));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(this, "Game Over !", null,
                            JOptionPane.ERROR_MESSAGE);

                    System.exit(0);

                    break;

                case 0:
                    buttons[x][y].setBackground(Color.lightGray);
                    ++this.noOfRevealed;

                    if (gameWon()) {
                        try {
                            handleGameWin();
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                    }

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            try {
                                buttonClicked(x + i, y + j);
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                    }

                    break;

                default:
                    buttons[x][y].setText(Integer.toString(mineLand[x][y]));
                    buttons[x][y].setBackground(Color.LIGHT_GRAY);
                    ++this.noOfRevealed;
                    if (gameWon()) {
                        try {
                            handleGameWin();
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                    }

                    break;
            }
        }
    }
}

