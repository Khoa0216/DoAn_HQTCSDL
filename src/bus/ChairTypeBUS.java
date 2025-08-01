package bus;

import dao.ChairTypeDAO;
import dto.ChairTypeDTO;
import java.util.ArrayList;

public class ChairTypeBUS {
    
    @SuppressWarnings("FieldMayBeFinal")
    private ChairTypeDAO chairTypeDAO;
    
    public ChairTypeBUS(){
        chairTypeDAO = new ChairTypeDAO();
    }
    
     public ChairTypeDTO findById(long id){
        return chairTypeDAO.findById(id);
    }
    
    public ArrayList<ChairTypeDTO> findAll(){
        return chairTypeDAO.findAll();
    }
    
    public long findIdByCode(String chairTypeCode){
        return chairTypeDAO.findByCode(chairTypeCode).getId();
    }
}
