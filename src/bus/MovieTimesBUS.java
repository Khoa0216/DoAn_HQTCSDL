package bus;

import dao.MovieTimesDAO;
import dto.MovieTimesDTO;
import java.util.ArrayList;

public class MovieTimesBUS {
    
    @SuppressWarnings("FieldMayBeFinal")
    private MovieTimesDAO movieTimesDAO;

    public MovieTimesBUS() {
        movieTimesDAO = new MovieTimesDAO();
    }
    
    public long findIdByCode(String movieTimesCode){
        return movieTimesDAO.findByMovieTimesCode(movieTimesCode).getId();
    }
    
    public MovieTimesDTO findById(long id){
        return movieTimesDAO.findById(id);
    }
    
    public ArrayList<MovieTimesDTO> findAll(){
        return movieTimesDAO.findAll();
    }
}
