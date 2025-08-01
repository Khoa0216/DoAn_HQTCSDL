package dao;

import dto.UserDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends AbstractDAO<UserDTO>{
    
    public UserDTO findById(long id){
        String sql = "SELECT * FROM users WHERE id = ?";
        ArrayList<UserDTO> listUser = query(sql, UserDTO.class, id);
        return listUser.isEmpty()? null : listUser.get(0);
    }
    
    public UserDTO findByUserNameAndPassword(String userName, String password){
        String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
        ArrayList<UserDTO> listUser = query(sql, UserDTO.class, userName, password);
        return listUser.isEmpty()? null : listUser.get(0);
    }
    
    public long save(UserDTO userDTO){

        String sql = "INSERT INTO users (roleid, username, password, fullname, dateofbirth, sex, address, phonenumber, usercode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insert(sql, userDTO.getRoleId(), userDTO.getUserName(), userDTO.getPassword(),
                userDTO.getFullName(), userDTO.getDateOfBirth(), userDTO.getSex(), 
                userDTO.getAddress(), userDTO.getPhoneNumber(), userDTO.getUserCode());
    }
    
    public ArrayList<UserDTO> findAll(){
        String sql = "SELECT * FROM users";
        return query(sql, UserDTO.class);
    }
    
    
    public void update(UserDTO user){
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE users SET usercode = ?, fullname = ?, dateofbirth = ?, ");
        sql.append("sex = ?, address = ?, phonenumber = ? WHERE id = ?");
        update(sql.toString(), user.getUserCode(), user.getFullName(), user.getDateOfBirth(),
                user.getSex(), user.getAddress(), user.getPhoneNumber(), user.getId());
    }
    
    public void delete(long id) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        CallableStatement sleepStatement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);  // tắt auto-commit để giữ transaction mở

            // Gọi procedure xóa
            callableStatement = connection.prepareCall("{call proc_delete_user(?)}");
            callableStatement.setLong(1, id);
            callableStatement.execute();

            // Giữ transaction "ngủ" 20 giây
            sleepStatement = connection.prepareCall("BEGIN DBMS_LOCK.SLEEP(5); END;");
            sleepStatement.execute();

            connection.commit();  // commit sau khi sleep xong
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (sleepStatement != null) sleepStatement.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
