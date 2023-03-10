package com.example.diceheadproj;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private Connection connection;
    private final String host = "10.0.2.2";
    private final String database = "Project";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "Sova1029";
    private String url = "jdbc:postgresql://localhost:5432/Project";
    private boolean status;

    public Database()
    {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    private void connect()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Class.forName("org.postgresql.Driver");
//                    connection = DriverManager.getConnection(url, user, pass);
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "Sova1029");
                    status = true;
                    System.out.println("connected:" + status);
                }
                catch (Exception e)
                {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection()
    {
        Connection c = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return c;
    }
}



