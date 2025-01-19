package in.sh.main.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Pattern(regexp = "^[a-zA-Z ]{5,25}$", message = "Name: 5-25 characters, letters & spaces only.")
    private String name;
    @Column
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ,message = "Enter a valid email.")
    private String email;
    @Column
    @Pattern(regexp = "^[a-z,A-Z,0-9]{5,25}$",message = "Password: 5-25 characters, letters & numbers.")
    private String password;
    @Column
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone: 10 digits only.")
    private String phoneno;
    @Column
    @Pattern(regexp = "^[a-z,A-Z]{3,25}$" , message = "City: 3-25 letters only.")
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
