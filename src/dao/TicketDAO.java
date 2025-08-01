package dao;

import dto.TicketDTO;
import java.util.ArrayList;

public class TicketDAO extends AbstractDAO<TicketDTO>{
    
    public TicketDTO findById(long id){
        String sql = "SELECT ID AS id, idghe AS chairId, idlichchieu AS showtimesId, userid AS userId, "
                + "ngayban AS dateOfSale FROM ve WHERE id = ?";
        ArrayList<TicketDTO> listTicket = query(sql, TicketDTO.class, id);
        return listTicket.isEmpty()? null : listTicket.get(0);
    }
    
    public long save(TicketDTO ticketDTO){
        String sql = "INSERT INTO ve (idghe, idlichchieu, userid, ngayban) VALUES (?, ?, ?, ?)";
        
        
        return insert(sql, ticketDTO.getChairId(), ticketDTO.getShowtimesId(),
               ticketDTO.getUserId() , ticketDTO.getDateOfSale());
    }
    
    public ArrayList<TicketDTO> findAll(){
        String sql = "SELECT ID AS id, idghe AS chairId, idlichchieu AS showtimesId, userid AS userId, "
                + "ngayban AS dateOfSale FROM ve";
        return query(sql, TicketDTO.class);
    }
    
    public TicketDTO findByChairIdAndShowtimesId(long chairId, long showtimesId){
        String sql = "SELECT ID AS id, idghe AS chairId, idlichchieu AS showtimesId, userid AS userId, "
                + "ngayban AS dateOfSale FROM VE WHERE idghe = ? AND idlichchieu = ?";
        ArrayList<TicketDTO> tickets = query(sql, TicketDTO.class, chairId, showtimesId);
        return tickets.isEmpty()? null : tickets.get(0);
    }
    
}
