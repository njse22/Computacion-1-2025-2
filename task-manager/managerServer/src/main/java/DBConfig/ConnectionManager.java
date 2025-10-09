package DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    private String url;
    private String user;
    private String password;

    private static ConnectionManager manager;

    public static ConnectionManager getInstance(){
        if(manager == null){
            manager = new ConnectionManager();
        }
        return manager;
    }

    public static ConnectionManager getInstance(String url, String user, String password){
        if(manager == null){
            manager = new ConnectionManager(url, user, password);
        }
        return manager;
    }

    private ConnectionManager(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private ConnectionManager(){
        this.url = System.getenv("url");
        this.user = System.getenv("user");
        this.password = System.getenv("password");
    }

    public Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    
}
