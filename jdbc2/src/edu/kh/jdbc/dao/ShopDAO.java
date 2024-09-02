package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.ShopMember;

public class ShopDAO {
	
	private PreparedStatement pstmt = null;
	private ResultSet rs = null; 
	
	public ShopMember selectMember(Connection conn, String memberId) {
		
		
		ShopMember sm = null;
		
		try {
			
			String sql = "SELECT * FROM SHOP_MEMBER WHERE MEMBER_ID = ?";
			pstmt = conn.prepareStatement(sql);
			
			 
			
			if(rs.next()) {
				String id = rs.getString("MEMBER_ID");
				String pw = rs.getString("MEMBER_PW");
				String phone = rs.getString("PHONE");
				String gender = rs.getString("성별");
				
				sm = new ShopMember(id, pw, phone, gender);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return sm;
		
	}
	
}
