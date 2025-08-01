package bus;

import dao.RoleDAO;
import dto.RoleDTO;

public class RoleBUS {
    
    @SuppressWarnings("FieldMayBeFinal")
    private RoleDAO roleDAO;
    
    public RoleBUS(){
        roleDAO = new RoleDAO();
    }
    
    public RoleDTO findById(long id){
        return roleDAO.findById(id);
    }
    
}
