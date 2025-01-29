package fr.esigelec.ping.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkRequest {
	private Integer studentId; // Utiliser Integer au lieu de String
    private Integer teacherId;
}

