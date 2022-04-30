package fr.univrouen.rss22.client.rsshandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {

	// NE FONCTIONNE PAS ENCORE
	public static void main( String[] args ) throws SQLException {
        //create connection for a server installed in localhost, with a user "root" with no password
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", null)) {
            // create a Statement
            try (Statement stmt = conn.createStatement()) {
                //execute query
                try (ResultSet rs = stmt.executeQuery("SELECT 'Hello World!'")) {
                    //position result to first
                    rs.first();
                    System.out.println(rs.getString(1)); //result is "Hello World!"
                }
            }
        }
    }
	
}
