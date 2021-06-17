import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.registerFileOpenBtn(new MyOpenFileBtn());

    }

    private class MyOpenFileBtn implements ActionListener { // Nupu klick'imise funktsioon
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.dir")));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV failid", "csv");
            jfc.setFileFilter(filter);
            int result = jfc.showOpenDialog(view);

            //System.out.println(result); 0 kui valiti fail ja 1 kui ei valitud faili
            if (result == JFileChooser.APPROVE_OPTION) {

                File f = jfc.getSelectedFile();
                model.setFileName(f.getAbsolutePath()); // pane failinimi mudelisse
                model.readFromFile(); // loeb faili sisu ja tulemus massiivi
                List<Filedata> filedata = model.getFileData();
                if (filedata.size() > 0) {
                    //System.out.println(filedata.size()); // testimiseks, et näidata faili kirjete arvu/suurust
                    view.getPnlBottom().removeAll();
                    createMyTable();
                    getFileName();


                }
            }
        }
    }

    private void createMyTable() {
        List<Filedata> fileDatas = model.getFileData(); // faili sisu (kõik 15000 rida)
        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(tableModel); // anname kaasa
        // Päise tegemine
        for (String colName : model.getColumnNames()) {
            tableModel.addColumn(colName);
        }

        // Tabeli sisu
        for (Filedata filedata : fileDatas) {
            String firstName = filedata.getFirstName();
            String lastName = filedata.getLastName();
            String gender = filedata.getGender();
            String birth = filedata.getBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String death = "";
            if (filedata.getDeath() != null) {
                death = filedata.getDeath().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
            String place = filedata.getPlace();
            String type = filedata.getType();
            String county = filedata.getCounty();

            // Lisa rida mudelisse
            Object[] objectRow = new Object[]{firstName, lastName, gender, birth, death, place, type, county};
            tableModel.addRow(objectRow);


        }
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                String values = tableModel.getDataVector().elementAt(table.getSelectedRow()).toString();
                String[] parts = values.split(",");
                String fName = parts[0];
                String lname = parts[1];
                String gender = parts[2];
                String birth = parts[3];
                String death = parts[4];
                String place = parts[5];
                String type = parts[6];
                String county = parts[7];
                String rowInfo = "Eesnimi: " + fName.substring(1) + "\n"
                            + "Perenimi: " + lname + "\n"
                            + "Sugu: " + gender + "\n"
                            + "Sündinud: " + birth + "\n"
                            + "Surnud: " + death + "\n"
                            + "Asula: " + place + "\n"
                            + "Tüüp: " + type + "\n"
                            + "Maakond: " + county.substring(0, county.length() - 1) + "\n";

                JOptionPane jop = new JOptionPane();
                jop.setMessage(rowInfo);
                jop.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = jop.createDialog("Isiku info");
                dialog.setVisible(true);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });


        int row = tableModel.getRowCount();
        int col = table.getColumnCount();
        tableModel.isCellEditable(row, col);

        view.getPnlBottom().

                add(new JScrollPane(table)); // Tabeli lisamine paneelile koos kerimisribaga, kui selleks on vajadus. Vastavalt sisu suurusele
        view.pack();
        view.getPnlBottom().

                setVisible(true);

    }

    private void getFileName() {
        String name = "";
        File f = new File(model.getFileName());
        if (f.exists()) {
            name = f.getAbsolutePath();
        }
        view.getPnlTop().add(new JLabel(name));
        view.pack();
        view.getPnlTop().setVisible(true);
    }

}


