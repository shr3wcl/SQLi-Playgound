package com.example.demo.service;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.*;

public class DBService {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/db_web?allowMultiQueries=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    public Connection connection;
    public DBService(){
        try {
            this.connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        }catch (SQLException ignored){
            System.out.println("Không thể kết nối với database");
        }
    }

    public Boolean execute(String query) {
        try{
            Statement statement = connection.createStatement();
            return statement.execute(query);

        }catch (SQLException ignored){
            return false;
        }
    }

    public int executeUpdateQuery(String query) {
        try{
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        }catch (SQLException ignored){
            return 0;
        }
    }

    public ResultSet executeQuery(String query){
        try{
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        }catch (SQLException ignored){return null;}
    }

    public void saveLog(HttpServletRequest hsr){
        try {
            Statement statement = connection.createStatement();
            String userAgent = hsr.getHeader("User-Agent");
            String requestPath = hsr.getRequestURI();
            String method = hsr.getMethod();
            String ip = hsr.getRemoteAddr();

            String query = "INSERT INTO logger (agent, path, method, ip) values ('" + userAgent + "','" + requestPath + "','" + method + "','" + ip +"')";
            statement.execute(query);
        }catch (SQLException ignored){}
    }
}