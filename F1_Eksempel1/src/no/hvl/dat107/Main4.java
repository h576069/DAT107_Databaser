package no.hvl.dat107;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main4 {

	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://data1.hib.no:5433/h576069";
	static final String USER = "h576069";
	static final String PASS = "2*Mm7%Xk";

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Person p = finnPersonMedId(1001);
		System.out.println(p);
	}

	public static Person finnPersonMedId(int id) {
		String sql = "SELECT id, navn FROM hello_jpa.person WHERE id=" + id;
		Person p = null;

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			if (rs.next()) {
				p = new Person();
				p.setId(rs.getInt("id"));
				p.setNavn(rs.getString("navn"));
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
