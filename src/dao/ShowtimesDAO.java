package dao;
import java.util.ArrayList;
import dto.ShowtimesDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ShowtimesDAO extends AbstractDAO<ShowtimesDTO>{
    
    public long save(ShowtimesDTO showtimes) {
        String sql = "INSERT INTO lichchieu (malichchieu, ngaychieu, idcachieu, idphong, idphim) VALUES (?, ?, ?, ?, ?)";

        try {
            return insertWithException(sql,
                    showtimes.getShowTimesCode(),
                    new java.sql.Date(showtimes.getProjectionDate().getTime()),
                    showtimes.getMovieTimesId(),
                    showtimes.getRoomId(),
                    showtimes.getMovieId()
            );

        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { // ORA-00001: UNIQUE constraint violated
                JOptionPane.showMessageDialog(null,
                        "❌ Không thể thêm lịch chiếu.\nLịch chiếu trùng phòng, ca chiếu và ngày!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "❌ Lỗi SQL khi lưu lịch chiếu: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "❌ Lỗi không xác định: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return -1;
    }
    
    public ArrayList<ShowtimesDTO> findById(long id){
        
        String sql = "SELECT ID AS id, malichchieu AS showTimesCode, ngaychieu AS projectionDate, idcachieu AS movieTimesId, "
                + "idphong AS roomId, idphim AS movieId FROM lichchieu WHERE id = ?";
        return query(sql, ShowtimesDTO.class, id);
    }
    
    public ArrayList<ShowtimesDTO> findAll(){
        String sql = "SELECT ID AS id, malichchieu AS showTimesCode, ngaychieu AS projectionDate, idcachieu AS movieTimesId, "
                + "idphong AS roomId, idphim AS movieId FROM lichchieu ";
        return query(sql, ShowtimesDTO.class);
    }
    
    public ArrayList<ShowtimesDTO> findByMovieId(long movieId){
        String sql = "SELECT ID AS id, malichchieu AS showTimesCode, ngaychieu AS projectionDate, idcachieu AS movieTimesId, "
                + "idphong AS roomId, idphim AS movieId FROM lichchieu WHERE idphim = ?";
        return query(sql, ShowtimesDTO.class, movieId);
    }
    
    public ShowtimesDTO findByCode(String showtimesCode){
        String sql = "SELECT ID AS id, malichchieu AS showTimesCode, ngaychieu AS projectionDate, idcachieu AS movieTimesId, "
                + "idphong AS roomId, idphim AS movieId FROM lichchieu WHERE malichchieu = ?";
        ArrayList<ShowtimesDTO> list = query(sql, ShowtimesDTO.class, showtimesCode);
        return list.isEmpty()? null : list.get(0);
    }
    
    public void update(ShowtimesDTO showtimes){
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE lichchieu SET malichchieu = ?, ngaychieu = ?,");
        sql.append("idcachieu = ?, idphong = ?, idphim = ? WHERE id = ?");
        update(sql.toString(), showtimes.getShowTimesCode(), showtimes.getProjectionDate(),
               showtimes.getMovieTimesId(),showtimes.getRoomId(),showtimes.getMovieId(),showtimes.getId());
    }
    
    public boolean delete(long id) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            String call = "{call proc_xoa_lichchieu_an_toan(?)}";
            callableStatement = connection.prepareCall(call);
            callableStatement.setLong(1, id);

            callableStatement.execute();
            
            // Sleep để giữ lock sau insert
            CallableStatement stmtSleep = connection.prepareCall("BEGIN DBMS_LOCK.SLEEP(5); END;");
            stmtSleep.execute();
            stmtSleep.close();
            
            connection.commit();

            JOptionPane.showMessageDialog(null, "Xóa lịch chiếu thành công!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Xóa lịch chiếu thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (callableStatement != null) callableStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}
