package uvsq21921354;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;



/**
 * Classe GroupeJdbcDAO implémentant DAO
 *
 *
 */


public class GroupeJdbcDAO implements DAO<Groupe>{
	private static String db = InitialisationJDBC.db;
	@Override
	public Groupe create(Groupe objet){
		try (Connection connect = DriverManager.getConnection(db)) {
			PreparedStatement prepare = connect.prepareStatement("INSERT INTO Groupes (id)" +"VALUES (?)");
			prepare.setString(1, objet.getId());
			int result = prepare.executeUpdate();
			assert result == 1; 
			List<PersonnelsUnchangeable> lp = objet.getAllPersonnel().stream().filter(e -> !e.isGroupe()).map(e -> (PersonnelsUnchangeable) e).collect(Collectors.toList());
			for (PersonnelsUnchangeable p : lp) {
				prepare = connect.prepareStatement("INSERT INTO appartient "+ "VALUES (?, ?)");
				prepare.setString(1, objet.getId());
				prepare.setString(2, p.getNom());
				prepare.addBatch();
			}
			prepare.executeBatch();
			System.out.println("Creation " + objet);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return objet;
	}
	
	@Override
	public Groupe read(String id){
		Groupe pg = null;

		try (Connection connect = DriverManager.getConnection(db)){
			System.out.println("Recherche " + id);
			PreparedStatement prepare = connect.prepareStatement("SELECT * FROM appartient WHERE id = ?");
			prepare.setString(1, id);
			pg = new Groupe(id);
			ResultSet result = prepare.executeQuery();
			PersonnelsJdbcDAO pjd = new PersonnelsJdbcDAO();
			while (result.next()) {
		        pg.addPersonnel(pjd.read(result.getString("nom")));
		    }			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return pg;
	}

	@Override
	public Groupe update(Groupe obj){
	try (Connection connect = DriverManager.getConnection(db)){
		List<PersonnelsUnchangeable> lp = obj.getAllPersonnel().stream()
			.filter(e -> !e.isGroupe())
			.map(e -> (PersonnelsUnchangeable) e)
			.collect(Collectors.toList());
		PreparedStatement prepare = connect.prepareStatement("DELETE FROM appartient"+ "WHERE id = ?");
		prepare.setString(1, obj.getId());
		for (PersonnelsUnchangeable p : lp){
			prepare = connect.prepareStatement("INSERT INTO appartient "+ "VALUES (?, ?)");
			prepare.setString(1, obj.getId());
			prepare.setString(2, p.getNom());
			prepare.addBatch();
		}
		prepare.executeBatch();
	}
	catch (SQLException e){
		e.printStackTrace();
	}
	System.out.println("Mise A Jour " + obj);
	return obj;
	}

	@Override
	public void delete(Groupe objet) {
		try (Connection connect = DriverManager.getConnection(db)) {
			PreparedStatement prepare = connect.prepareStatement("DELETE FROM personnelGroupes "+ "WHERE id = ?");
			prepare.setString(1, objet.getId());
			int result = prepare.executeUpdate();
			assert result == 1;
			System.out.println("Suppression terminee " + objet);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}		
}
