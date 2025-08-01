package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mapper.RowMapper;

public class AbstractDAO<T> {
    
    public Connection getConnection(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String user = "na";
            String password = "na";
            String url = "jdbc:oracle:thin:@localhost:1521/QLBANVEXEMPHIM";
            Connection connection = DriverManager.getConnection(url,user,password);
            return connection;
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void setParameter(PreparedStatement statement, Object... parameters){
        try{
        int index;
        for(int i = 0; i < parameters.length; i++){
            index = i +1;
            Object parameter = parameters[i];
            statement.setObject(index, parameter);
        }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<T> query(String sql, Class<T> tClass, Object... parameters){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            resultSet = statement.executeQuery();
            RowMapper<T> rowMapper = new RowMapper<>();
            return rowMapper.mapRow(resultSet, tClass);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            try{
                if(connection != null){
                    connection.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    @SuppressWarnings({"static-access", "null"})
    public long insert(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        long id = 0;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql, new String[] { "ID" });
            setParameter(statement, parameters);
            statement.executeUpdate();

            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }

            CallableStatement stmtSleep = connection.prepareCall("BEGIN DBMS_LOCK.SLEEP(5); END;");
            stmtSleep.execute();
            stmtSleep.close();

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try { if (connection != null) connection.rollback(); } catch (SQLException ex) {}
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return id;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void update(String sql, Object... parameters){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            statement.executeUpdate();

            CallableStatement stmtSleep = connection.prepareCall("BEGIN DBMS_LOCK.SLEEP(5); END;");
            stmtSleep.execute();
            stmtSleep.close();

            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(connection != null){
                    connection.close();
                }
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    
    public void updateWithException(String sql, Object... parameters) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            statement.executeUpdate();

            CallableStatement stmtSleep = connection.prepareCall("BEGIN DBMS_LOCK.SLEEP(5); END;");
            stmtSleep.execute();
            stmtSleep.close();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }


    public long insertWithException(String sql, Object... parameters) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        long id = 0;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql, new String[]{"ID"});
            setParameter(statement, parameters);
            statement.executeUpdate();

            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }

            CallableStatement stmtSleep = connection.prepareCall("BEGIN DBMS_LOCK.SLEEP(5); END;");
            stmtSleep.execute();
            stmtSleep.close();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return id;
    }

}