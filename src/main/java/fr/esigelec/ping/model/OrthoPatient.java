package fr.esigelec.ping.model;

public class OrthoPatient {
    private User ortho;
    private Integer patientId;

    public OrthoPatient(User ortho, Integer patientId) {
        this.ortho = ortho;
        this.patientId = patientId;
    }

    public User getOrtho() {
        return ortho;
    }

    public Integer getPatientId() {
        return patientId;
    }

    @Override
    public String toString() {
        return "OrthoPatient{" +
                "ortho=" + ortho +
                ", patientId=" + patientId +
                '}';
    }
}