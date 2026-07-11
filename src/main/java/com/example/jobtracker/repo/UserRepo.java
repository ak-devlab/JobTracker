package com.example.jobtracker.repo;

import java.sql.*;
import com.example.jobtracker.model.User;


public class UserRepo {
	
	public User findOrCreate(String googleSub, String email, String name, String picture) {
		try {
			Class.forName("org.postgresql.Driver");
			
			Class.forName("org.postgresql.Driver");

			String dbUrl = System.getenv("DB_URL");
			String dbUser = System.getenv("DB_USER");
			String dbPass = System.getenv("DB_PASS");

			System.out.println("DB_URL exists: " + (dbUrl != null));
			System.out.println("DB_USER exists: " + (dbUser != null));
			System.out.println("DB_PASS exists: " + (dbPass != null));

			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			
			String selectSql = "SELECT id, google_Sub, email, name, picture FROM users WHERE google_sub = ?";
			PreparedStatement ps = con.prepareStatement(selectSql);
			ps.setString(1, googleSub);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				User u = new User();
				u.id = rs.getLong("id");
				u.googleSub = rs.getString("google_sub");
				u.email = rs.getString("email");
				u.name = rs.getString("name");
				u.picture = rs.getString("picture");
				rs.close();
				ps.close();
				con.close();
				return u;
			}
			
			rs.close();
			ps.close();
			
			String insertSql = """
					INSERT INTO users (google_sub, email, name, picture)
					VALUES(?, ?, ?, ?)
					RETURNING id
					""";
			
			PreparedStatement ips = con.prepareStatement(insertSql);
			ips.setString(1, googleSub);
			ips.setString(2, email);
			ips.setString(3, name);
			ips.setString(4, picture);
			
			ResultSet irs = ips.executeQuery();
			
			User u = new User();
			if(irs.next()) {
				u.id = irs.getLong("id");
			}
			
			u.googleSub = googleSub;
			u.email = email;
			u.name = name;
			u.picture = picture;
			
			irs.close();
			ips.close();
			con.close();
			
			return u;
			
		}catch(Exception e){
			    System.out.println("UserRepo error message: " + e.getMessage());
			    e.printStackTrace();
			    return null;
			}
					
		}
	}


