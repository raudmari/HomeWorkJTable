import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT)); // paneelil olevad asjad joondatakse vasakule
    private JPanel pnlBottom = new JPanel(new BorderLayout());
    private JButton btnOpenFile = new JButton("Ava fail"); // nupp nimetusega Ava fail


    public View() {
        super("Isikud");
        setPreferredSize(new Dimension(500, 300));
        pnlTop.setBackground(Color.orange); // ülemise paneeli taustavärv

        //nupu lisamine paneelile
        pnlTop.add(btnOpenFile);
        // Sellele paneelile lähevad ülemine ja alumine paneel
        JPanel container = new JPanel(new BorderLayout());
        container.add(pnlTop, BorderLayout.NORTH);
        container.add(pnlBottom, BorderLayout.CENTER); // läheb dünaamiliselt väiksemaks/suuremaks kui ekraani vähendada/suurendada

        add(container); // container lisatakse JFram'ile
    }

    /**
     * fileOpen nupu funktsionaalsus
     * @param al
     */

    public void registerFileOpenBtn(ActionListener al) {
        btnOpenFile.addActionListener(al);
    }

    /**
     * Vajalik, et siia saaks peale isada JTable
     * @return
     */

    public JPanel getPnlBottom() {
        return pnlBottom;
    }
}
