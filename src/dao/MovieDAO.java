package dao;
import dto.MovieDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MovieDAO extends AbstractDAO<MovieDTO>{
    
    public MovieDTO findById(long id){
        String sql = "SELECT ID AS id, MAPHIM AS movieCode, TENPHIM AS movieName, THOILUONG AS time, "
                + "HANGSX AS producer, THELOAI AS category, MOTANGAN AS shortDescription FROM phim WHERE id = ?";

        
        System.out.println(id);
        
        ArrayList<MovieDTO> listMovie = query(sql, MovieDTO.class, id);
        return listMovie.isEmpty()? null : listMovie.get(0);
    }
    
    public long save(MovieDTO movie){
        String sql = "INSERT INTO phim (maphim, tenphim, thoiluong, hangsx, theloai, motangan) VALUES (?, ?, ?, ?, ?, ?)";
        return insert(sql, movie.getMovieCode(), movie.getMovieName(), movie.getTime(),
              movie.getProducer(), movie.getCategory(), movie.getShortDescription());
    }
    
    public ArrayList<MovieDTO> findAll(){
        String sql = "SELECT ID AS id, MAPHIM AS movieCode, TENPHIM AS movieName, THOILUONG AS time, HANGSX AS producer, THELOAI AS category, MOTANGAN AS shortDescription FROM phim";
        return query(sql, MovieDTO.class);
    }
    
    public MovieDTO findByCode(String moviecode){
        String sql = "SELECT ID AS id, MAPHIM AS movieCode, TENPHIM AS movieName, THOILUONG AS time, HANGSX AS producer, THELOAI AS category, "
                + "MOTANGAN AS shortDescription FROM phim WHERE MAPHIM = ?";
        ArrayList<MovieDTO> listMovie = query(sql, MovieDTO.class, moviecode);
        return listMovie.isEmpty()? null : listMovie.get(0);
    }
    
    public void update(MovieDTO movie){
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE phim SET maphim = ?, tenphim = ?, thoiluong = ?,");
        sql.append("hangsx = ?, theloai = ?, motangan = ? WHERE id = ?");
        update(sql.toString(), movie.getMovieCode(), movie.getMovieName(), movie.getTime(),
                movie.getProducer(), movie.getCategory(), movie.getShortDescription(), movie.getId());
    }
    
    public boolean delete(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            // Gọi procedure an toàn (có kiểm tra ràng buộc nghiệp vụ)
            String call = "{call proc_xoa_lichchieu_an_toan(?)}";
            statement = connection.prepareCall(call);
            statement.setLong(1, id);
            statement.execute();

            // Sleep để giữ lock sau insert
            CallableStatement stmtSleep = connection.prepareCall("BEGIN DBMS_LOCK.SLEEP(15); END;");
            stmtSleep.execute();
            stmtSleep.close();
            
            connection.commit();
            JOptionPane.showMessageDialog(null, "Xóa lịch chiếu thành công!");
            return true;

        } catch (SQLException e) {
            String errorMessage = e.getMessage();

            if (errorMessage != null && errorMessage.contains("ORA-20005")) {
                // Lỗi do chưa đến thời gian chiếu hoặc đã bán vé
                JOptionPane.showMessageDialog(null, "Không thể xóa lịch chiếu: " + errorMessage,
                                              "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                // Các lỗi khác
                JOptionPane.showMessageDialog(null, "Lỗi hệ thống: " + errorMessage,
                                              "Lỗi", JOptionPane.ERROR_MESSAGE);
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
