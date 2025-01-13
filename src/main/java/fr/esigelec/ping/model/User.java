package fr.esigelec.ping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.esigelec.ping.model.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;
 
    private String password;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("birth_date")
    private LocalDate birthDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
  
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
   
    private LocalDateTime updatedAt;

    private Role role;

    private Boolean validate;
	


	
   

}
