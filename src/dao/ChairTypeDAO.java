package dao;

import dto.ChairTypeDTO;
import java.util.ArrayList;

public class ChairTypeDAO extends AbstractDAO<ChairTypeDTO>{
    
    public ChairTypeDTO findById(long id){
        String sql = "SELECT ID AS id, maloaighe AS chairTypeCode, giave AS costOfTicket FROM loaighe WHERE id = ?";
        return query(sql, ChairTypeDTO.class, id).get(0);
    }
    
    public ArrayList<ChairTypeDTO> findAll(){
        String sql = "SELECT ID AS id, maloaighe AS chairTypeCode, giave AS costOfTicket FROM loaighe";
        return query(sql, ChairTypeDTO.class);
    }
    
    public ChairTypeDTO findByCode(String chairTypeCode){
        String sql = "SELECT ID AS id, maloaighe AS chairTypeCode, giave AS costOfTicket FROM loaighe WHERE maloaighe = ?";
        ArrayList<ChairTypeDTO> listChairType = query(sql, ChairTypeDTO.class, chairTypeCode);
        return listChairType.isEmpty()? null : listChairType.get(0);
    }
}
