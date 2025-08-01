package dao;

import dto.RoleDTO;
import java.util.ArrayList;

public class RoleDAO extends AbstractDAO<RoleDTO>{
    
    public RoleDTO findById(long id){
        String sql = "SELECT * FROM ROLE WHERE id = ?";
        ArrayList<RoleDTO> listRole = query(sql, RoleDTO.class, id);
        return listRole.isEmpty()? null : listRole.get(0);
    }
}
