import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Model { // faili sisu lugemine
    private String filename;
    private ArrayList<Filedata> fileData;
    private String[] columnNames;


    public Model() {
        fileData = new ArrayList<>();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public ArrayList<Filedata> getFiledata() {
        return fileData;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void readFromFile() {
        fileData = new ArrayList<>(); // eelnevad andmed kustutatakse.
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int r = 0;
            for (String line; (line = br.readLine()) != null; ) {
                String[] parts = line.split(";");
                if (r > 0) {
                    LocalDate birth = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate death;
                    if (parts.length == 8) { // Kõik olemas, isik elab
                        fileData.add(new Filedata(parts[0], parts[1], parts[2], birth, null, parts[5], parts[6], parts[7]));
                    } else {
                        death = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        fileData.add(new Filedata(parts[0], parts[1], parts[2], birth, death, "", "", ""));
                    }
                } else { // loome päise ja lisame nimed massiivi
                    columnNames = new String[parts.length]; // määrame suuruse, fikseeritud
                    columnNames = parts;
                }
                r++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Faili lugemisega probleem");
        }
    }
}
