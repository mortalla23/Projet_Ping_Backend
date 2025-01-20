package fr.esigelec.ping.model.enums;

public enum LinkValidation {
    ONGOING,
    VALIDATED,
    REFUSED;
    
	public static boolean isValidRole(String status) {
	    for (LinkValidation l : LinkValidation.values()) {
	        if (l.name().equalsIgnoreCase(status)) {
	            return true;
	        }
	    }
	    return false;
	}
    
}
