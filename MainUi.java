import javax.swing.*;
import Controller.Controller;

public class MainUi {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::createAndShowGUI);
    }
}