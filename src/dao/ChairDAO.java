package dao;

import dto.ChairDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ChairDAO extends AbstractDAO<ChairDTO>{
    
    public ChairDTO findById(long id){
        String sql = "SELECT ID AS id, maghe AS chairCode, trangthai AS status, "
                + "idloaighe AS chairTypeId FROM ghe WHERE id = ?";
        ArrayList<ChairDTO> listChair = query(sql, ChairDTO.class, id);
        return listChair.isEmpty()? null : listChair.get(0);
    }
    
    public long save(ChairDTO chairDTO){
        String sql = "INSERT INTO ghe (maghe, trangthai, idloaighe) VALUES (?, ?, ?)";
        return insert(sql, chairDTO.getChairCode(), chairDTO.getStatus(), chairDTO.getChairTypeId());
    }
    
    public ArrayList<ChairDTO> findAll(){
        String sql = "SELECT ID AS id, maghe AS chairCode, trangthai AS status, "
                + "idloaighe AS chairTypeId FROM ghe";
        return query(sql, ChairDTO.class);
    }
    
    public ChairDTO findByCode(String chairCode){
        String sql = "SELECT ID AS id, maghe AS chairCode, trangthai AS status, "
                + "idloaighe AS chairTypeId FROM ghe WHERE maghe = ?";
        ArrayList<ChairDTO> listChair = query(sql, ChairDTO.class, chairCode);
        return listChair.isEmpty()? null : listChair.get(0);
    }
    
    
    public void update(ChairDTO chair){
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ghe SET maghe = ?, trangthai = ?, ");
        sql.append("idloaighe = ? WHERE id = ?");
        update(sql.toString(), chair.getChairCode(), chair.getStatus(),
                chair.getChairTypeId(), chair.getId());
    }
    
    public void updateWithException(ChairDTO chair) throws SQLException {
        String sql = "UPDATE ghe SET maghe = ?, trangthai = ?, idloaighe = ? WHERE id = ?";
        updateWithException(sql, chair.getChairCode(), chair.getStatus(),
                            chair.getChairTypeId(), chair.getId());
    }
    
    public boolean delete(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            // Gọi procedure
            String call = "{call proc_xoa_ghe_an_toan(?)}";
            statement = connection.prepareCall(call);
            statement.setLong(1, id);
            statement.execute();

            
            // Sleep để giữ lock sau insert
            CallableStatement stmtSleep = connection.prepareCall("BEGIN DBMS_LOCK.SLEEP(5); END;");
            stmtSleep.execute();
            stmtSleep.close();
            
            connection.commit();
            JOptionPane.showMessageDialog(null, "Xóa ghế thành công!");
            return true;
        } catch (SQLException e) {
            String errorMessage = e.getMessage();

            if (errorMessage != null && errorMessage.contains("ORA-20006")) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + errorMessage, "Không thể xóa ghế", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi hệ thống: " + errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}