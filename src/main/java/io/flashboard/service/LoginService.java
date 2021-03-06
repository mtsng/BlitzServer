package io.flashboard.service;

import io.flashboard.beans.users.User;
import io.flashboard.dao.UserDaoImpl;
import io.flashboard.jsonbeans.AuthData;

public class LoginService {

	/**
	 * Takes a username and password to check against a database entry.
	 * Validates a user when logging in
	 * 
	 * @param username
	 * @param password
	 * @return true for correct credentials, false for incorrect credentials
	 */
	public static boolean validate(String username, String password) {
		UserDaoImpl ud = new UserDaoImpl();
		User user = ud.getUserByUsername(username);
		
		//user not found
		if(user == null) {
			return false;
		}
		
		//incorrect password
		if(!user.getPassword().equals(password)) {
			return false;
		}
		
		//user not approved, blacklisted
		if(!user.getApproved() || user.getBlacklisted()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Takes a username and password to check against a database entry.
	 * Validates a user when logging in, 
	 * and provides authorization token and userRole to be returned client-side
	 * 
	 * @param username
	 * @param password
	 * @return AuthData for correct credentials, null for incorrect credentials
	 */
	public static AuthData authentication(String username, String password) {
		UserDaoImpl ud = new UserDaoImpl();
		User user = ud.getUserByUsername(username);
		
		if(user == null) {
			return null;
		}
		
		if(!user.getPassword().equals(password)) {
			return null;
		}
		
		//if user is blacklisted or not approved, they can't login
		if(!user.getApproved() || user.getBlacklisted()) {
			return null;
		}
		
		return new AuthData(user);
		
	}
}
