package fr.esigelec.ping.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import fr.esigelec.ping.model.enums.Role;

import java.util.Date;

@Document(collection = "users")
public class User {

    @Field("id")  // ✅ Identifiant métier
    private int id;

    @Field("birth_date")
    private Date birthDate;

    @Field("username")
    private String username;

    @Field("last_name")
    private String lastName;

    @Field("first_name")
    private String firstName;

    @Field("password_hash")
    private String password;

    @Field("email")
    private String email;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;

    @Field("role")
    private Role role;

    @Field("validate")
    private String validate;

    // 🔧 Constructeur par défaut
    public User() {}

    // 🔧 Constructeur complet
    public User(int id, Date birthDate, String username, String password,
                String email, Date createdAt, Date updatedAt, Role role, String validate) {
        this.id = id;
        this.birthDate = birthDate;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.validate = validate;
    }

    // 🔍 Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // 🛠️ Getters et Setters

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }
}
