import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class InputEngine implements ActionListener {
    Menu parent;

    InputEngine(Menu parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        parent.setVisible(false);
        parent.setName(evt.getActionCommand());
        parent.startGame();
    }
}
