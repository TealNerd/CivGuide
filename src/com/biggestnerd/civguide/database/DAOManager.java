package com.biggestnerd.civguide.database;

public class DAOManager {
	
	private static DismissalDAO dao;

	public static IDismissalDAO getInstance() {
		if(dao == null) {
			return dao = new DismissalDAO();
		}
		if(!dao.valid()) {
			dao.initializeDatabase();
		}
		return dao;
	}
}
