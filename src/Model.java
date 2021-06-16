import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Model { // faili sisu lugemine
    private String filename;
    private ArrayList<Filedata> filedata;
    private String[] columNames;


    public Model() {
        filedata = new ArrayList<>();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ArrayList<Filedata> getFiledata() {
        return filedata;
    }

    public String[] getColumNames() {
        return columNames;
    }

    public void readFromFile() {
        filedata = new ArrayList<>(); // eelnevad andmed kustutatakse.
        // Kui soovid faili olemasolu kontrollida
        File file = new File(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int r = 0;
            for (String line; (line = br.readLine()) != null; ) {
                if (r > 0) {
                    String[] parts = line.split(";");
                    LocalDate birth = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate death;
                    if (parts.length == 8) { // Kõik olemas, isik elab
                        death = null;
                        filedata.add(new Filedata(parts[0], parts[1], parts[2], birth, death, parts[5], parts[6], parts[7]));
                    } else {
                        death = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        filedata.add(new Filedata(parts[0], parts[1], parts[2], birth, death, "", "", ""));
                    }
                } else { // loome päise ja lisame nimed massiivi
                    String[] parts = line.split(";");
                    columNames = new String[parts.length]; // määrame suuruse, fikseeritud
                    columNames = parts;
                }
                r++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Faili lugemisega probleem");
        }
    }
}
