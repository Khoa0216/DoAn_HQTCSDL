package bus;

import dao.ShowtimesDAO;
import dto.ShowtimesDTO;
import java.util.ArrayList;

public class ShowtimesBUS {
    
    @SuppressWarnings("FieldMayBeFinal")
    private ShowtimesDAO showtimesDAO;
    
    public ShowtimesBUS(){
        showtimesDAO = new ShowtimesDAO();
    }
    
    public ShowtimesDTO save(ShowtimesDTO showtimes){
        showtimes.setId(showtimesDAO.save(showtimes));
        
        return findById(showtimes.getId());
    }
    
    public ArrayList<ShowtimesDTO> findAll(){
        return showtimesDAO.findAll();
    }
    
    public ShowtimesDTO findByCode(ShowtimesDTO showtimesDTO){
        return showtimesDAO.findByCode(showtimesDTO.getShowTimesCode());
    }
    
    public ShowtimesDTO findById(long id){
        ArrayList<ShowtimesDTO> listShowtimes = showtimesDAO.findById(id);
        return listShowtimes.isEmpty()? null : listShowtimes.get(0);
    }
    
    public ShowtimesDTO update(ShowtimesDTO showtimes){
        showtimesDAO.update(showtimes);
        return findById(showtimes.getId());
    }
    
    public boolean delete(ShowtimesDTO showtimes){
        return showtimesDAO.delete(showtimes.getId());
    }
    
    public ArrayList<ShowtimesDTO> findByMovieId(long movieId){
        return  showtimesDAO.findByMovieId(movieId);
    }
}
