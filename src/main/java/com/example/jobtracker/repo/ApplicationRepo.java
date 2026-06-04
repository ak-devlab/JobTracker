package com.example.jobtracker.repo;

import com.example.jobtracker.model.ApplicationEntry;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.sql.*;
public class ApplicationRepo {
	// グループ別の応募データを保持する　Map
	private final ConcurrentMap<String, ConcurrentMap<Long,ApplicationEntry>> store = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, AtomicLong> seqs = new ConcurrentHashMap<>();
	 
	// グループごとの箱を取得（無ければ新しく作る）
	private ConcurrentMap<Long,ApplicationEntry> bucket(String group){
		return store.computeIfAbsent(group, g -> new ConcurrentHashMap<>());
	}
	// グループごとのIDカウンター
	private AtomicLong seq(String group) {
		return seqs.computeIfAbsent(group, g -> new AtomicLong(1));
	}
	// 一覧取得
	public List<ApplicationEntry> list(String group){
	    var all = new ArrayList<ApplicationEntry>();
	    
	    String sql = """
	    		SELECT id, company, role, status, next_action,
	    		next_action_at, memo FROM applications 
	    		WHERE app_group = ?
	    		ORDER BY next_action_at IS NULL, next_action_at
	    		""";
	    
	     try {
	    	 Class.forName("com.mysql.cj.jdbc.Driver");
	    	 
	    	 Connection con = DriverManager.getConnection(
	    			    System.getenv("DB_URL"),
	    			    System.getenv("DB_USER"),
	    			    System.getenv("DB_PASS")
	    			);
	    	 
	    	 PreparedStatement ps = con.prepareStatement(sql);
	    	 ps.setString(1, group);
	    	 ResultSet rs = ps.executeQuery();
	    	 
	    	 while(rs.next()) {
	    		 ApplicationEntry e = new ApplicationEntry();
	    		 e.id = rs.getLong("id");
	    		 e.company = rs.getString("company");
	    		 e.role = rs.getString("role");
	    		 e.status = rs.getString("status");
	    		 e.nextAction = rs.getString("next_action");
	    		 e.note = rs.getString("memo");
	    		 
	    		 var ts = rs.getTimestamp("next_action_at");
	    		 e.nextActionAt = ts == null ? null : ts.toInstant().toEpochMilli();
	    		 
	    		 all.add(e);
	    	 }
	    	 rs.close();
	    	 ps.close();
	    	 con.close();
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
	     return all;
	}
	    
	 public List<ApplicationEntry> listAll(){
		 var all = new ArrayList<ApplicationEntry>();
		
		 String sql="""
		 		SELECT id, company, role, status,
		 		next_action, next_action_at, memo
		 		FROM applications
		 		ORDER BY next_action_at IS NULL, next_action_at
		 		""";
		 
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 
			 Connection con = DriverManager.getConnection(
					    System.getenv("DB_URL"),
					    System.getenv("DB_USER"),
					    System.getenv("DB_PASS")
					);
			 
			 PreparedStatement ps =
					 con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery();
			 
			 while(rs.next()) {
				 var e = new ApplicationEntry();
				 
				 e.id = rs.getLong("id");
				 e.company = rs.getString("company");
				 e.role = rs.getString("role");
				 e.status = rs.getString("status");
				 e.nextAction = rs.getString("next_action");
				 
				 Timestamp ts = rs.getTimestamp("next_action_at");
				 
				 e.nextActionAt = 
						 (ts == null ? null : ts.getTime());
				 
				 e.note = rs.getString("memo");
				 
				 all.add(e);
				 
			 }
			 rs.close();
			 ps.close();
			 con.close();
		 }catch(Exception e) {
			 e.printStackTrace();
			 
		 }
	       
		 return all;
	 }
	 
	public List<ApplicationEntry> search(String group,String keyword){
		
		var all = new ArrayList<ApplicationEntry>();
		
		String sql = """
				SELECT id,company,role,status,next_action,
				next_action_at,memo
				FROM applications
				WHERE app_group=? AND company LIKE ?
				ORDER BY next_action_at IS NULL, next_action_at
				""";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(
				    System.getenv("DB_URL"),
				    System.getenv("DB_USER"),
				    System.getenv("DB_PASS")
				);
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,group);
			ps.setString(2,"%" + keyword + "%");
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				ApplicationEntry e = new ApplicationEntry();
				
				e.id = rs.getLong("id");
				e.company = rs.getString("company");
				e.role = rs.getString("role");
				e.status = rs.getString("status");
				e.nextAction = rs.getString("next_action_at");
				
				var ts = rs.getTimestamp("next_action_at");
				
				e.nextActionAt =
						ts == null ? null :
				        ts.toInstant().toEpochMilli();
				e.note = rs.getString("memo");
				
				all.add(e);
			}
			
			rs.close();
			ps.close();
			con.close();
				
	}catch(Exception ex) {
		ex.printStackTrace();
	}
		return all;
	}
	public List<ApplicationEntry> filterByStatus(String group, String status){
		var out = new ArrayList<ApplicationEntry>();
		
		String sql = """
				SELECT id, company, role, status,
				next_action, next_action_at, memo
				FROM applications
				WHERE app_group = ?
				AND status = ?
				ORDER BY next_action_at IS NULL, next_action_at
				""";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(
				    System.getenv("DB_URL"),
				    System.getenv("DB_USER"),
				    System.getenv("DB_PASS")
				);
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, group);
			ps.setString(2, status);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ApplicationEntry e = new ApplicationEntry();
				
				e.id = rs.getLong("id");
				e.company = rs.getString("company");
				e.role = rs.getString("role");
				e.status = rs.getString("status");
				e.nextAction = rs.getString("next_action");
				
				var ts = rs.getTimestamp("next_action_at");
				
				e.nextActionAt =
						ts == null ? null : ts.toInstant().toEpochMilli();
				
				e.note = rs.getString("memo");
				
				out.add(e);
			}
			
			 rs.close();
			 ps.close();
			 con.close();
		}catch(Exception ex) {
			ex.printStackTrace();		
			}
	
	return out;
}
	
	// 追加
	public long add(String group, ApplicationEntry e) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(
				    System.getenv("DB_URL"),
				    System.getenv("DB_USER"),
				    System.getenv("DB_PASS")
				);
			String sql = """
					INSERT INTO applications
					(app_group, company, role, status, next_action, next_action_at,memo)
					VALUES(?,?,?,?,?,?,?)
					""";
			
			PreparedStatement ps = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, group);
			ps.setString(2, e.company);
			ps.setString(3, e.role);
			ps.setString(4, e.status);
			ps.setString(5, e.nextAction);
			
			if(e.nextActionAt == null) {
				ps.setTimestamp(6, null);
			}else {
				ps.setTimestamp(6, new Timestamp(e.nextActionAt));
			}
			ps.setString(7, e.note);
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			long id = 0;
			
			if(rs.next()) {
				id = rs.getLong(1);
			}
			rs.close();
			ps.close();
			con.close();
			
			return id;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	//　更新
	public void update(String group, ApplicationEntry e) {
		String sql = """
				UPDATE applications
				SET company =?,
				role=?,
				status=?,
				next_action=?,
				next_action_at=?,
				memo=?
				WHERE id=?
				""";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(
				    System.getenv("DB_URL"),
				    System.getenv("DB_USER"),
				    System.getenv("DB_PASS")
				);
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, e.company);
			ps.setString(2, e.role);
			ps.setString(3, e.status);
			ps.setString(4, e.nextAction);
			
			if(e.nextActionAt == null) {
				ps.setTimestamp(5, null);
				
			}else {
				ps.setTimestamp(5, new java.sql.Timestamp(e.nextActionAt));
			}
			ps.setString(6, e.note);
			ps.setLong(7, e.id);
			
			ps.executeUpdate();
			 
			ps.close();
			con.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	// 削除 5月19日　次やる
	public void delete(String group,long id) { 
		String sql = "DELETE FROM applications WHERE id=?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(
				    System.getenv("DB_URL"),
				    System.getenv("DB_USER"),
				    System.getenv("DB_PASS")
				);
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, id);
			ps.executeUpdate();
			con.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	// 検索
	public ApplicationEntry find(String group, long id) {
		
		String sql = "SELECT * FROM applications WHERE id=?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(
				    System.getenv("DB_URL"),
				    System.getenv("DB_USER"),
				    System.getenv("DB_PASS")
				);
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				ApplicationEntry e = new ApplicationEntry();
				
				e.id = rs.getLong("id");
				e.company = rs.getString("company");
				e.role = rs.getString("role");
				e.status = rs.getString("status");
				e.nextAction = rs.getString("next_action");
				
				var ts = rs.getTimestamp("next_action_at");
				e.nextActionAt = ts == null ? null : ts.toInstant().toEpochMilli();
				
				e.note = rs.getString("memo");
				
				rs.close();
				ps.close();
				con.close();
				
				return e;
			}
			
			rs.close();
			ps.close();
			con.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
