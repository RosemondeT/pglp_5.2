package uvsq21921354;

/**
 * 
 * Classe PersonnelsSerializableDAO qui hérite de la classe SerializableDAO
 *
 */

public class PersonnelsSerializableDAO extends SerializableDAO<PersonnelsUnchangeable> {
	public String getFilename(String id) {
		return id + ".personnelsUnchangeable";
	}

	public String getFilename(PersonnelsUnchangeable objet) {
		return getFilename(objet.getNom());

	}

	


}
