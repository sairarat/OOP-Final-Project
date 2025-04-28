public class resumeLauncher {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new resumeController();
        });
    }
}