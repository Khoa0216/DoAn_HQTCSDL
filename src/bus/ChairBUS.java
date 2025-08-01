package bus;

import dao.ChairDAO;
import dto.ChairDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ChairBUS {
    
    @SuppressWarnings("FieldMayBeFinal")
    private ChairDAO chairDAO;
    
    public ChairBUS(){
        chairDAO = new ChairDAO();
    }
    
    public ChairDTO save(ChairDTO chairDTO){
        long id = chairDAO.save(chairDTO);
        return chairDAO.findById(id);
    }
    
    public ArrayList<ChairDTO> findAll(){
        return chairDAO.findAll();
    }
    
    public ChairDTO findByCode(ChairDTO chairDTO){
        return chairDAO.findByCode(chairDTO.getChairCode());
    }
    
    public ChairDTO findById(long id){
        return chairDAO.findById(id);
    }
    
    public ChairDTO update(ChairDTO chair) {
        try {
            chairDAO.updateWithException(chair); // dùng hàm có throw SQLException
            return chairDAO.findById(chair.getId());
        } catch (SQLException e) {
            if (e.getErrorCode() == 20001) {
                // Hiển thị thông báo lỗi cho người dùng
                JOptionPane.showMessageDialog(null, 
                    "Không thể chuyển ghế sang 'Không sử dụng được' do vẫn còn vé hiệu lực.", 
                    "Lỗi cập nhật ghế", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Cập nhật ghế thất bại: " + e.getMessage(), 
                    "Lỗi cập nhật ghế", 
                    JOptionPane.ERROR_MESSAGE);
            }
            return null; // hoặc xử lý trả về phù hợp khi cập nhật thất bại
        }
    }

    
    public boolean delete(long id){
        return chairDAO.delete(id);
    }
}
