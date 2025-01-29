package fr.esigelec.ping.model.enums;

public enum Role {
    admin,
    TEACHER,
    PATIENT,
    STUDENT,
    ORTHOPHONIST;
    
	public static boolean isValidRole(String role) {
	    for (Role r : Role.values()) {
	        if (r.name().equalsIgnoreCase(role)) {
	            return true;
	        }
	    }
	    return false;
	}

}