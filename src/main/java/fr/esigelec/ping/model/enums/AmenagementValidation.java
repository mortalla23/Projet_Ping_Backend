package fr.esigelec.ping.model.enums;

public enum AmenagementValidation {
    ONGOING,
    VALIDATED,
    REFUSED;
    
	public static boolean isValid(String status) {
	    for (AmenagementValidation a : AmenagementValidation.values()) {
	        if (a.name().equalsIgnoreCase(status)) {
	            return true;
	        }
	    }
	    return false;
	}
}
