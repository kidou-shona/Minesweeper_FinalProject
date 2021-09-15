import entity.LEVEL;
import entity.Rating;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RatingOutput extends JFrame implements ActionListener {

    private Object[][] data;
    private String[] columnNames = {"Username", "Date", "Game Time (seс)"};
    private DefaultTableModel tableModel;
    private JTable table;

    public RatingOutput(ArrayList<Rating> ratings, LEVEL level) {
        super("Top 10 rating of level: " + level);
        setBounds(100,100,400,230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Object[][] output = convert2Data(ratings);
        tableModel = new DefaultTableModel(output, columnNames);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(380,280));
        JPanel panel = new JPanel();
        panel.add(scrollPane);
        add(panel,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public Object[][] convert2Data (ArrayList<Rating> ratings) {
        Object[][] data = new Object[ratings.size()][3];
        for (int i = 0; i < ratings.size(); i++) {
            data[i][0] = ratings.get(i).getUsername();
            data[i][1] = ratings.get(i).getDueDate();
            data[i][2] = ratings.get(i).getGameTime();
        }
        return data;
    }
}
