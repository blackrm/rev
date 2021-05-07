package util;
import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDBCConnection {
	
	private static final Logger logger = LogManager.getLogger(JDBCConnection.class);

	static Connection conn;
	public static Connection makeConnection() {
		// TODO Auto-generated method stub
		//configure
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "postgres";
		
		//connect
		Connection conn = null;
		try {
			System.out.println("Connecting...");
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected...");
			
		}
			catch (SQLException e) {
				e.printStackTrace();
			}
		return conn;
		
		
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
