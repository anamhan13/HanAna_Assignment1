package org.ui;

import java.sql.Connection;

import org.dao.connection.ConnectionFactory;

import login.Login;

/**
 * Hello world!
 *
 */
public class App 
{
    @SuppressWarnings("unused")
	public static void main( String[] args )
    {  
    	Connection connection =  ConnectionFactory.getConnection();
    	
    	Login login = new Login();
    	Login.launch(Login.class);
    	
    	//Login.setConnection(connection);
    	
//    	PlayerRepository playerRepository = new PlayerRepository(connection);
//    	Player player = playerRepository.find("mail", "anahan@yahoo.com");
//    	
//    	UserLoginValidation userLoginValidation = new UserLoginValidation("anahan@yahoo.com", "yoewj43", connection);
//    	System.out.println(userLoginValidation.validateCredentials());
    	
    	ConnectionFactory.close(connection);

    }

}
