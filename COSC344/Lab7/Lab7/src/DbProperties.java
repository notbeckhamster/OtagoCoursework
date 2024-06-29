/*
    File:  DbProperties.java
    March 2024
*/

import java.io.*;
import java.util.*;

/**
 * Reads database connection information from a Java properties file.
 * Default filename database.properties; use the constructor with the
 * filename parameter to change it.
 *
 * @author Nigel Stanger
 */

public class DbProperties {

    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    /**
     * Use the default properties filename.
     */
    public DbProperties() {
        this("database.properties");
    }

    /**
     * Use a non-default properties filename.
     * @param filename
     */
    public DbProperties(String filename) {
        try (FileInputStream propertiesFile = new FileInputStream(filename);) {
            PropertyResourceBundle databaseProperties = new PropertyResourceBundle(propertiesFile);
            this.dbHost = databaseProperties.getString("HOST");
            this.dbPort = databaseProperties.getString("PORT");
            this.dbName = databaseProperties.getString("DATABASE");
            this.dbUser = databaseProperties.getString("USER");
            this.dbPassword = databaseProperties.getString("PASSWORD");
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public String getDbHost() {
        return this.dbHost;
    }

    public String getDbPort() {
        return this.dbPort;
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getDbUser() {
        return this.dbUser;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

    public void setDbHost(String host) {
        this.dbHost = host;
    }

    public void setDbPort(String port) {
        this.dbPort = port;
    }

    public void setDbName(String name) {
        this.dbName = name;
    }

    public void setDbUser(String user) {
        this.dbUser = user;
    }

    public void setDbPassword(String password) {
        this.dbPassword = password;
    }

} // end class UserPass
