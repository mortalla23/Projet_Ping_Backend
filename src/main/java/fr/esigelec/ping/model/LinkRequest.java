package fr.esigelec.ping.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkRequest {
    private String studentId; // ID de l'étudiant à lier
    private String teacherId; // ID de l'enseignant avec lequel l'étudiant sera lié
}

