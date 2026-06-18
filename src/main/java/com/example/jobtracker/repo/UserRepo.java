package com.example.jobtracker.repo;

import java.sql.*;
import com.example.jobtracker.model.User;


public class UserRepo {
	
	public User findOrCreate(String googleSub, String email, String name, String picture) {
		try {
			Class.forName("org.postgresql.Driver");
			
			Connection con = DriverManager.getConnection(
					System.getenv("DB_URL"),
					System.getenv("DB_USER"),
					System.getenv("DB_PASS")
					
					
					);
			
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
			e.printStackTrace();
			return null;
					
		}
	}

}
