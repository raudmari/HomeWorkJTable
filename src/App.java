import javax.swing.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View();
            new Controller(model, view);

            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view.pack();
            view.setLocationRelativeTo(null);
            view.setVisible(true);

        });

    }
}
