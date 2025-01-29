package fr.esigelec.ping.model;

/**
 * Classe pour représenter un enseignant avec des informations de base.
 */
public class Teacher {
    private int id;
    private String firstName;
    private String lastName;
    private int patientId;

    public Teacher(int id, String firstName, String lastName,int patientId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patientId = patientId;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patientId=" + patientId +
                '}';
    }

}

