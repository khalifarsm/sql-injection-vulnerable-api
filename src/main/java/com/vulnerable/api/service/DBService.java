package com.vulnerable.api.service;

import com.vulnerable.api.model.Country;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBService {

    @PostConstruct
    public void setup() {
        createUsersTable();
        createAdmin();
        createCountriesTable();
        createCountries();
    }


    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public List<Country> selectAll() {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT id, code, country FROM countries";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                countries.add(new Country()
                        .setCode(rs.getString("code"))
                        .setId(rs.getString("id"))
                        .setName(rs.getString("country")));
            }
            return countries;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return countries;
    }

    public Country select(String id) {
        String sql = "SELECT id, code, country FROM countries where id=" + id;

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                return new Country()
                        .setCode(rs.getString("code"))
                        .setId(rs.getString("id"))
                        .setName(rs.getString("country"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                "\tid integer PRIMARY KEY,\n" +
                "   \temail varchar(255) NOT NULL,\n" +
                "\tpassword varchar(255) DEFAULT 'pass'\n" +
                ");";

        execute(sql);
    }

    public void createCountriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS countries (\n" +
                "\tid integer PRIMARY KEY,\n" +
                "   \tcountry varchar(255) NOT NULL,\n" +
                "\tcode varchar(255) DEFAULT 'MA'\n" +
                ");";

        execute(sql);
    }

    public void createAdmin() {
        execute("insert into users (id,email,password) values (1,'admin@api.app','inpt-admin-tag');");
    }

    public void createCountries() {
        execute("insert into countries (id,country,code) values (1,'Maroc','MA');");
        execute("insert into countries (id,country,code) values (2,'France','FR');");
        execute("insert into countries (id,country,code) values (3,'Afghanistan','AF');");
        execute("insert into countries (id,country,code) values (4,'China','CN');");
    }

    public void execute(String q) {
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(q);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
