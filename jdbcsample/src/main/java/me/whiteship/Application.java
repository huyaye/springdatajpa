package me.whiteship;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Application {

	public static void main(String[] args) throws Exception {
		/*
		 * DB connection info
		 */
		String url = "jdbc:postgresql://localhost:5432/jungwook";
		String username = "jungwook";
		String password = "1234";
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Connection created : " + connection);
			/*
			 * DML
			 */
//			String sql = "CREATE TABLE ACCOUNT (id int, username varchar(255), password varchar(255))";
//			try (PreparedStatement statement = connection.prepareStatement(sql)) {
//				statement.execute();
//			}
			/*
			 * DDL
			 */
			String sql = "INSERT INTO ACCOUNT VALUES (1, 'jungwook', 'password')";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();
			}
		}

	}

}
