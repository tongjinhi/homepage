package homepage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DBManager {

	Connection conn = null;
	
	public DBManager(){
		findDriver();
		connectDB();
	}
	
	public void findDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
			System.err.println("DB driver not found.");
		}
	}
	
	public void connectDB() {
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		String id = "root";
		String pw = "";
		try {
			conn = DriverManager.getConnection(url, id, pw);
			System.err.println("DB server connection Success");
		}catch(SQLException ee) { 
			System.err.println("DB server connection Fail");
		}
	}
	
	public Vector<BoardData> selectData(String keyword) {
		Statement stmt = null;
		ResultSet rs = null;
		Vector<BoardData> bdv = new Vector<BoardData>();
		
		try{
			String query;
			if(keyword == "" || keyword == null || keyword.length() == 0)
				query = "";
			else
				query = " where content like '%" + keyword + "%'";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM homepage_tb" + query);
			
			System.out.println("[SELECT * FROM homepage_tb" + query + "]");
			
			while (rs.next()) { 
				BoardData bd = new BoardData(rs.getString("board_id"),
						                     rs.getString("user_id"),
						                     rs.getString("name"), 
						                     rs.getString("title"), 
						                     rs.getString("content"),
						                     rs.getString("w_date"));
				bdv.add(bd);
			}
			
			stmt.close();
			
		} catch (SQLException ee) {
			System.err.println("Select Fail" + ee.toString());
		}

		return bdv;
	}

	public Boolean insertData(String record[]) {
		Boolean result = false;
		PreparedStatement pstmt = null; 
		String SQL = "INSERT INTO homepage_tb VALUES (?, ?, ?, ?, ?, ?)";
		
		try{
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, record[0]); // board_id
			pstmt.setString(2, record[1]); // user_id
			pstmt.setString(3, record[2]); // name
			pstmt.setString(4, record[3]); // title
			pstmt.setString(5, record[4]); // content
			pstmt.setString(6, record[5]); // w_date
			
			int num = pstmt.executeUpdate();
			System.out.println("[" + SQL + "]");
			System.out.println(num + "Insert Success");
			
			pstmt.close();
			result = true;
			
		} catch (SQLException ee) {
			System.err.println("Insert Fail" + ee.toString());
			result = false;
		}
		
		return result;
	}
	
	public Boolean updateData(String uptRecord[]) {
		Boolean result = false;
		PreparedStatement pstmt = null; 
		String SQL = "UPDATE homepage_tb SET "
				+ "user_id = ?, "
				+ "name = ?, "
				+ "title = ?, "
				+ "content = ?, "
				+ "w_date = ? "
				+ "WHERE board_id = ?";	
		
		try{
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, uptRecord[1]); // user_id
			pstmt.setString(2, uptRecord[2]); // name
			pstmt.setString(3, uptRecord[3]); // title
			pstmt.setString(4, uptRecord[4]); // content
			pstmt.setString(5, uptRecord[5]); // w_date
			pstmt.setString(6, uptRecord[0]); // board_id
			
			int num = pstmt.executeUpdate();
			System.out.println("[" + SQL + "]");
			System.out.println(num + "Update Success");
			
			pstmt.close();
			result = true;
		} catch (SQLException ee) {
			System.err.println("Update Fail" + ee.toString());
			result = false;
		}
		
		return result;
	}
	
	public boolean deleteData(int delRow) {
		Boolean result = false;
		PreparedStatement pstmt = null; 
		String SQL = "DELETE FROM homepage_tb WHERE board_id = ?";
		
		try{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, delRow); // board_id
		
			int num = pstmt.executeUpdate();
			System.out.println("[" + SQL + "]");
			System.out.println(num + "Delete Success");
			
			pstmt.close();
			result = true;
		} catch (SQLException ee) {
			System.err.println("Delete Fail" + ee.toString());
			result = false;
		}
		
		return result;
	}
	
	public void closeConn(){
		try{
			conn.close();
		}catch (SQLException ee) {
			System.err.println("Connection close Fail" + ee.toString());
		}
	}
	
	public static void main(String[] args) {
		Vector<BoardData> bdv;
		DBManager bdb = new DBManager();
		bdv = bdb.selectData("");
		for(BoardData bd : bdv){
			System.out.println(bd.getBoardId() + "|" + 
					           bd.getUserId()  + "|" + 
					           bd.getName()    + "|" +
					           bd.getTitle()   + "|" +
					           bd.getContent() + "|" + 
					           bd.getDate());
		}
		bdb.closeConn();
	}
}
