package models.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import controllers.DatabaseConnection.*;
import enums.SpeciesEnum;
import models.Patroller;
//import utils.DatabaseUtilities;
import models.SpeciesType;
import models.ThreatType;
import models.dao.LookupDao;

@Singleton
public class LookupDaoImpl implements LookupDao {

	private Connection connection;
	
	@Inject
	public LookupDaoImpl(DatabaseUtilities databaseUtilities) {
		connection = databaseUtilities.getConnection();
	}
	
	@Override
	public List<SpeciesType> getSpeciestypes() {
		try {
			List<SpeciesType> speciesTypes = new ArrayList<>();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM lookup_species_type WHERE active = 1");
			
			while(rs.next()) {
				SpeciesType speciesType = new SpeciesType();
				speciesType.setId(rs.getLong("idlookup_species_type_id"));
				speciesType.setName(rs.getString("name"));
				speciesType.setSpecies(SpeciesEnum.valueOf(rs.getString("species")));
				speciesTypes.add(speciesType);
			}
			
			return speciesTypes;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ThreatType> getThreatTypes() {
		try {
			List<ThreatType> threatTypes = new ArrayList<>();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM lookup_threat_type WHERE active = 1");
			
			while(rs.next()) {
				ThreatType threatType = new ThreatType();
				threatType.setId(rs.getLong("lookup_threat_type_id"));
				threatType.setName(rs.getString("name"));
				threatTypes.add(threatType);
			}
			
			return threatTypes;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addThreatType(String name) {
		try {
			
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("INSERT INTO lookup_threat_type (name) VALUES ('" + name + "')");
			
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}
		
	}

	@Override
	public ThreatType getThreatByName(String name) {
		try {
			ThreatType threatType = null;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM lookup_threat_type where name ='" + name + "'");
			
			while(rs.next()) {
				
				long id = rs.getLong("lookup_threat_type_id");
				threatType = new ThreatType();
				threatType.setId(id);
				threatType.setName(name);
				
				
			}
			
			return threatType;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateThreatType(ThreatType threatType) {
		try {
			
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("UPDATE lookup_threat_type SET name = '" + threatType.getName() +
					"'WHERE lookup_threat_type_id = " + threatType.getId());
			
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}
		
	}

	@Override
	public boolean deleteThreatType(long id) {
		try {
			
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate("UPDATE lookup_threat_type SET active=0 WHERE lookup_threat_type_id = " + id);
		
			if(rows == 1){
				
				return true;
			}else{
				
				return false;
			}
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}
	}

	@Override
	public void addSpeciesType(SpeciesType speciesType) {
		try {
			String name = speciesType.getName();
			SpeciesEnum speciesEnum = speciesType.getSpecies();
			String species = speciesEnum.name();
			
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("INSERT INTO lookup_species_type (name,species)" +
							"VALUES ('" +  name + "','"
									+ species +"')");
			
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}
		
	}

	@Override
	public SpeciesType getSpeciesByName(SpeciesType speciesType) {
		try {
			String name = speciesType.getName();
			SpeciesEnum speciesEnum = speciesType.getSpecies();
			String species = speciesEnum.name();
			
			SpeciesType speciesType1 = null;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM lookup_species_type where name ='" + name + "' "
					+ " AND species = '" + species +"'");
			
			while(rs.next()) {
				
				long id1 = rs.getLong("idlookup_species_type_id");
				String name1 = rs.getString("name");
				String species1 = rs.getString("species");
				SpeciesEnum speciesEnum1 = SpeciesEnum.valueOf(species1);
				
				speciesType1 = new SpeciesType();
				speciesType1.setId(id1);
				speciesType1.setName(name1);
				speciesType1.setSpecies(speciesEnum1);
				
				
				
				
			}
			
			return speciesType1;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateSpeciesType(SpeciesType speciesType) {
		try {
			long id = speciesType.getId();
			String name = speciesType.getName();
			SpeciesEnum speciesEnum = speciesType.getSpecies();
			String species = speciesEnum.name();
			
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("UPDATE lookup_species_type SET name = '" + name +
					"', species = '" + species + "'  WHERE idlookup_species_type_id = " + id);
			
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}
		
	}

	@Override
	public boolean deleteSpeciesType(long id) {
		try {
			
			Statement stmt = connection.createStatement();
			int rows = stmt.executeUpdate("UPDATE lookup_species_type SET active = 0 WHERE idlookup_species_type_id = " + id);
		
			if(rows == 1){
				
				return true;
			}else{
				
				return false;
			}
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		
		}
		
	}

}
