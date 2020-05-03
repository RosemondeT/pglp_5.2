package uvsq21921354;

/**
 * 
 * Fabrique l'instanciation DAO
 *
 */

public class DAOFactory extends AbstractDAOFactory {

	public DAO<PersonnelsUnchangeable> getPersonnelDAO(){
		return new PersonnelsSerializableDAO();
	}
	public DAO<Groupe> getPersonnelGroupeDAO(){
		return new GroupeSerializableDAO();
	}

}

