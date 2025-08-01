package bus;

import dao.UserDAO;
import dto.UserDTO;
import java.util.ArrayList;

public class UserBUS {
    
    @SuppressWarnings("FieldMayBeFinal")
    private UserDAO userDAO;
    
    public UserBUS(){
        userDAO = new UserDAO();
    }
    
    public UserDTO findByUserNameAndPassword(UserDTO userDTO){

        return userDAO.findByUserNameAndPassword(userDTO.getUserName(), userDTO.getPassword());
    }
    
    public UserDTO save(UserDTO userDTO){
        return userDAO.findById(userDAO.save(userDTO));
    }
    
    public ArrayList<UserDTO> findAll(){
        return userDAO.findAll();
    }
    
    public UserDTO update(UserDTO user){
        userDAO.update(user);
        return userDAO.findById(user.getId());
    }
    
    public void delete(UserDTO user){
        userDAO.delete(user.getId());
    }
    
    public UserDTO findById(long id){
        return userDAO.findById(id);
    }
}
