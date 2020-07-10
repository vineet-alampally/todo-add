package todo.rest.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import io.dropwizard.*;
import io.dropwizard.setup.Environment;
import todo.rest.controller.TodoController;

public class App  extends Application<Configuration>  {
	private static final String value = "Hello %s";
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
    @Override
    public void run(Configuration appConfiguration,
                    Environment environment) throws Exception {
        //Register resource
    	System.out.println("Started");
    	CreateDB();
    	CreateTable();
    	TodoController todoController = new TodoController();
        environment.jersey().register(todoController);
    }
    
    private void CreateDB() throws Exception
    {
    	
			Class.forName("org.h2.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/my", "root","mypassword");
			System.out.println("DB Created");		    	
    }
    
    private void CreateTable() throws Exception
    {
    	
			Class.forName("org.h2.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/my", "root","mypassword");
			Statement st = con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS TODOS "
					+ "(id INTEGER auto_increment, parentid INTEGER, name VARCHAR(255), description VARCHAR(255), PRIMARY KEY (id)) ";
			st.executeUpdate(sql);			
			System.out.println("Parent Table Created");		    	
    }
        
}
