import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class InputEngine implements ActionListener {
    Menu parent;

    InputEngine(Menu parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String username = "";
        parent.setVisible(false);
        parent.set(username);
    }
}
