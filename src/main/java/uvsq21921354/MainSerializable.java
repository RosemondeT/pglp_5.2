package uvsq21921354;

import uvsq21921354.AbstractDAOFactory.DAOType;


/**
 * 
 * Classe qui contient le main
 *
 */

public enum MainSerializable {
	ENVIRONNEMENT;
	private void run(String[] args, DAOType dt){
		DAO<PersonnelsUnchangeable> personnelDAO = AbstractDAOFactory.getFactory(dt).getPersonnelDAO();
		DAO<Groupe> pgDAO = AbstractDAOFactory.getFactory(dt).getPersonnelGroupeDAO();
				// Creation personnel
		PersonnelsUnchangeable PAM = new PersonnelsUnchangeable.Builder("PAM", "Nadia").build();
		PersonnelsUnchangeable  TOURE = new PersonnelsUnchangeable.Builder("TOURE", "Jonathan").build();
				Groupe pg = new Groupe();
				Groupe spg = new Groupe();
				pg.addPersonnel(spg);
				pg.addPersonnel(PAM);
				spg.addPersonnel(TOURE);
				// Ajout DAO
				pgDAO.create(pg);
				pgDAO.create(spg);
				personnelDAO.create(PAM); // update
				personnelDAO.create(TOURE); // update
				System.out.println("\t" + pgDAO.read(spg.getId()));
				System.out.println("\t" + personnelDAO.read("TOURE"));
	}
	
	public static void main(String[] args){
		
		InitialisationJDBC ji = new InitialisationJDBC();
		ji.dropCreate();
		// ENVIRONNEMENT.run(args, DAOType.Serial);
		ENVIRONNEMENT.run(args, DAOType.JDBC);
	}
}
