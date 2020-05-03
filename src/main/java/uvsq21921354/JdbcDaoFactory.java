package uvsq21921354;

/**
 * 
 * Fabrique l'instanciationJDBC
 *
 */

public class JdbcDaoFactory extends AbstractDAOFactory{
	
	public DAO<PersonnelsUnchangeable> getPersonnelDAO(){
		return new PersonnelsJdbcDAO();
	}
	public DAO<Groupe> getPersonnelGroupeDAO(){
		return new GroupeJdbcDAO();
	}
}
