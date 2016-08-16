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
			ResultSet rs = stmt.executeQuery("SELECT * FROM lookup_species_type");
			
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM lookup_threat_type");
			
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

}
