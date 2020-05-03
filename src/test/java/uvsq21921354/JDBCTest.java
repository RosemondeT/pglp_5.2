package uvsq21921354;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uvsq21921354.AbstractDAOFactory.DAOType;


public class JDBCTest{
	
	private PersonnelsUnchangeable personne;
	private PersonnelsUnchangeable personne1;
	private PersonnelsUnchangeable personne2;
	private Groupe personngroup;
	private InitialisationJDBC connexion;

	@Before()
	public void InitialisationJDBCTest(){
		personne = new PersonnelsUnchangeable.Builder("TRAORE","Rosemonde").dateNaiss(1992,03,18).numT(new ContactsPersonnels("06110203","78567890","5345676788")).build();
		personne1 = new PersonnelsUnchangeable.Builder("Sanou","Yann").dateNaiss(1997,02,12).numT(new ContactsPersonnels("06110203","84567890","7345676788")).build();
		personne2 = new PersonnelsUnchangeable.Builder("Kalmogho","Japhet").dateNaiss(1997,06,15).numT(new ContactsPersonnels("63374028","75464768","7345656678")).build();
		personngroup = new Groupe();
		Groupe group = new Groupe();
		personngroup.addPersonnel(group);
		personngroup.addPersonnel(personne);
		
	}
	
	@Test()
	public void createConnexion(){
		connexion=new InitialisationJDBC();
	}
	
	@Test()
	public void createTable(){
		connexion=new InitialisationJDBC();
		connexion.createTables();
	}
	
	@Test()
	public void dropTable(){
		connexion=new InitialisationJDBC();
		connexion.dropCreate();
	}
	
   @Test()
	public void PersonnelsJDBCTest() {
		DAO<PersonnelsUnchangeable> ajout =AbstractDAOFactory.getFactory(DAOType.JDBC).getPersonnelDAO();
		ajout.delete(personne);
		ajout.create(personne);
		ajout.read("TRAORE");
		ajout.update(personne1);
		assertEquals(personne1, ajout.read(personne1.getNom()));
	}
	
	@Test()
	public void GroupeJDBCTest() {
		DAO<Groupe> PGJT = AbstractDAOFactory.getFactory(DAOType.JDBC).getPersonnelGroupeDAO();
		PGJT.delete(personngroup);
		PGJT.create(personngroup);
		PGJT.read("TRAORE");
		personngroup.addPersonnel(personne2);
		PGJT.update(personngroup);
		assertEquals(personngroup, PGJT.read(personngroup.getId()));
	}
	
}
