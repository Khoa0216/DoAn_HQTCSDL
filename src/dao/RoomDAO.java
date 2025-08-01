package dao;

import java.util.ArrayList;
import dto.RoomDTO;

public class RoomDAO extends AbstractDAO<RoomDTO>{
    
        public ArrayList<RoomDTO> findAll(){
        String sql = "SELECT ID AS id, maphong AS roomCode, tenphong AS roomName, soghe1day AS numberOfChair1Row, " +
"                + soluongday AS numberOfRow FROM phong";
        return query(sql, RoomDTO.class);
    }

        public RoomDTO findByCode(String roomCode){
        String sql = "SELECT ID AS id, maphong AS roomCode, tenphong AS roomName, soghe1day AS numberOfChair1Row, " +
"                + soluongday AS numberOfRow FROM phong WHERE maphong = ?";
        ArrayList<RoomDTO> listRoom = query(sql, RoomDTO.class, roomCode);
        return listRoom.isEmpty()? null : listRoom.get(0);
    }
    
    public RoomDTO findById(long id){
        String sql = "SELECT ID AS id, maphong AS roomCode, tenphong AS roomName, soghe1day AS numberOfChair1Row, " +
"                + soluongday AS numberOfRow FROM phong WHERE id = ?";
        ArrayList<RoomDTO> listRooms = query(sql, RoomDTO.class, id);
        return listRooms.isEmpty()? null : listRooms.get(0);
    }
}
