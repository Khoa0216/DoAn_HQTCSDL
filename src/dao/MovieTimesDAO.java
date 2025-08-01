package dao;
import java.util.ArrayList;
import dto.MovieTimesDTO;
public class MovieTimesDAO extends AbstractDAO<MovieTimesDTO>{
    
    public ArrayList<MovieTimesDTO> findAll(){
        String sql = "SELECT ID AS id, macachieu AS movieTimesCode, TO_CHAR(thoigian, 'HH24:MI:SS') AS projectionTime FROM cachieu";
        return query(sql, MovieTimesDTO.class);
    }
    
    public MovieTimesDTO findByMovieTimesCode(String movieTimesCode){
        String sql = "SELECT ID AS id, macachieu AS movieTimesCode, TO_CHAR(thoigian, 'HH24:MI:SS') AS projectionTime FROM cachieu WHERE macachieu = ?";
        ArrayList<MovieTimesDTO> listMovieTimeses = query(sql, MovieTimesDTO.class, movieTimesCode);
        return listMovieTimeses.isEmpty()? null : listMovieTimeses.get(0);
    }
    
    public MovieTimesDTO findById(long id){
        String sql = "SELECT ID AS id, macachieu AS movieTimesCode, TO_CHAR(thoigian, 'HH24:MI:SS') AS projectionTime FROM cachieu WHERE id = ?";
        ArrayList<MovieTimesDTO> list = query(sql, MovieTimesDTO.class, id);
        return list.isEmpty()? null : list.get(0);
    }
    
}
