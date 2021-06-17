import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            int result = jfc.showOpenDialog(view);
            //System.out.println(result); 0 kui valiti fail ja 1 kui ei valitud faili
            if(result == JFileChooser.APPROVE_OPTION) {
                File f = jfc.getSelectedFile();
                model.setFilename(f.getAbsolutePath()); // pane failinimi mudelisse
                model.readFromFile(); // loeb faili sisu ja tulemus massiivi
                List<Filedata> filedata = model.getFiledata();
                if(filedata.size() > 0) {
                    //System.out.println(filedata.size()); // testimiseks, et näidata faili kirjete arvu/suurust
                    view.getPnlBottom().removeAll();
                    createMyTable();
                    getFileName();

                }
            }
        }
    }

    private void createMyTable() {
        List<Filedata> filedatas = model.getFiledata(); // faili sisu (kõik 15000 rida)
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel); // anname kaasa
        // Päise tegemine
        for(String colName : model.getColumnNames()) {
            tableModel.addColumn(colName);
        }

        // Tabeli sisu
        for(Filedata filedata : filedatas) {
            String firstName = filedata.getFirstName();
            String lastName = filedata.getLastName();
            String gender = filedata.getGender();
            String birth = filedata.getBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String death = "";
            if(filedata.getDeath() != null) {
                death = filedata.getDeath().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
            String place = filedata.getPlace();
            String type = filedata.getType();
            String county = filedata.getCounty();

            // Lisa rida mudelisse
            tableModel.addRow(new Object[] {firstName, lastName,gender, birth, death, place, type, county});
        }

        view.getPnlBottom().add(new JScrollPane(table)); // Tabeli lisamine paneelile koos kerimisribaga, kui selleks on vajadus. Vastavalt sisu suurusele
        view.pack();
        view.getPnlBottom().setVisible(true);

    }

    public void getFileName() {
        String name = "";
        File f = new File(model.getFilename());
        if(f.exists()) {
            name = f.getAbsolutePath();
        }
        view.getPnlTop().add(new JLabel(name));
        view.pack();
        view.getPnlTop().setVisible(true);





    }
}
