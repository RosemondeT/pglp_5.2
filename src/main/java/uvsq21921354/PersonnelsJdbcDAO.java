package uvsq21921354;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 
 * Classe PersonnelsJdbcDAO qui implémente le DAO
 *
 */





public class PersonnelsJdbcDAO implements DAO<PersonnelsUnchangeable>{
	
	private static String db = InitialisationJDBC.db;
	@Override
	public PersonnelsUnchangeable create(PersonnelsUnchangeable objet){
		try (Connection connect = DriverManager.getConnection(db)){
			PreparedStatement prepare = connect.prepareStatement("INSERT INTO personnels (nom, prenom)" +"VALUES (?, ?)");
			prepare.setString(1, objet.getNom());
			prepare.setString(2, objet.getPrenom());
			System.out.println("Creation reussie " + objet);
			int result = prepare.executeUpdate();
			assert result == 1; 
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return objet;
	}

	@Override
	public PersonnelsUnchangeable read(String id){
		PersonnelsUnchangeable p = null;
		try (Connection connect = DriverManager.getConnection(db)){
			System.out.println(" Recherche " + id);
			PreparedStatement prepare = connect.prepareStatement("SELECT * FROM personnels WHERE nom = ?");
			prepare.setString(1, id);
			ResultSet result = prepare.executeQuery();
			if(result.next()){
				p = new PersonnelsUnchangeable.Builder(result.getString("nom"),result.getString("prenom")).build();
				result.close();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public PersonnelsUnchangeable update(PersonnelsUnchangeable objet){
		try (Connection connect = DriverManager.getConnection(db)) {
			PreparedStatement prepare = connect.prepareStatement("UPDATE personnels "+ "SET prenom = ?"+ "WHERE nom = ?");
			prepare.setString(1, objet.getPrenom());
			prepare.setString(3, objet.getNom());
			int result = prepare.executeUpdate();
			assert result == 1;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		System.out.println("Mise A Jour " + objet);
		return objet;
	}

	@Override
	public void delete(PersonnelsUnchangeable objet){
		try (Connection connect = DriverManager.getConnection(db)){
			PreparedStatement prepare = connect.prepareStatement("DELETE FROM personnels "+ "WHERE nom = ?");
			prepare.setString(1, objet.getNom());
			int result = prepare.executeUpdate();
			assert result == 1;
			System.out.println("Suppression reussie " + objet);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

}
