package bus;

import dao.TicketDAO;
import dto.TicketDTO;
import java.util.ArrayList;

public class TicketBUS {
    
    @SuppressWarnings("FieldMayBeFinal")
    private TicketDAO ticketDAO;
    
    public TicketBUS(){
        ticketDAO = new TicketDAO();
    }
    
    public TicketDTO save(TicketDTO ticketDTO){
        
        long id = ticketDAO.save(ticketDTO);
        
        
        return ticketDAO.findById(id);
    }
    
    public ArrayList<TicketDTO> findAll(){
        return ticketDAO.findAll();
    }
    
    public TicketDTO findByChairIdAndShowtimesId(TicketDTO ticket){
        return ticketDAO.findByChairIdAndShowtimesId(ticket.getChairId(), ticket.getShowtimesId());
    }
    
}
