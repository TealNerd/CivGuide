package com.biggestnerd.civguide.database;

public class DAOManager {
	
	private static DismissalDatabase dao;

	public static DismissalDAO getInstance() {
		if(dao == null) {
			return dao = new DismissalDatabase();
		}
		if(!dao.valid()) {
			dao.initializeDatabase();
		}
		return dao;
	}
}
