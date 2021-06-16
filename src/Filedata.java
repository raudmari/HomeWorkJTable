import java.time.LocalDate;
import java.time.LocalDateTime;

public class Filedata {

    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birth;
    private LocalDate death;
    private String place;
    private String type;
    private String county;

    public Filedata(String firstName, String lastName, String gender, LocalDate birth, LocalDate death, String place, String type, String county) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birth = birth;
        this.death = death;
        this.place = place;
        this.type = type;
        this.county = county;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDate getDeath() {
        return death;
    }

    public String getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

    public String getCounty() {
        return county;
    }
}
