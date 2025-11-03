package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	static public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "gdj95",
                "java1234"
            );
	}
}
