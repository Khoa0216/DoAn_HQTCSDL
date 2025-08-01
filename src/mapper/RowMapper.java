package mapper;

import dao.RoleDAO;
import dto.RoleDTO;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.beanutils.BeanUtils;

public class RowMapper<T> {

    @SuppressWarnings("CallToPrintStackTrace")
    public ArrayList<T> mapRow(ResultSet resultSet, Class tClass) {
        ArrayList<T> listT = new ArrayList<>();

        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            Field[] fields = tClass.getDeclaredFields();

            while (resultSet.next()) {
                T object = (T) tClass.getDeclaredConstructor().newInstance();

                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String columnName = rsmd.getColumnName(i + 1).toLowerCase();
                    Object columnValue = resultSet.getObject(i + 1);

                    if (columnValue == null) continue;

                    if (tClass.getSimpleName().equals("UserDTO") && columnName.equals("role_id")) {
                        RoleDAO roleDAO = new RoleDAO();
                        RoleDTO role = roleDAO.findById(Long.parseLong(columnValue.toString()));
                        BeanUtils.setProperty(object, "roleDTO", role);
                        continue;
                    }

                    for (Field field : fields) {
                        if (field.getName().toLowerCase().equals(columnName)) {
                            BeanUtils.setProperty(object, field.getName(), columnValue);
                            break;
                        }
                    }
                }

                listT.add(object);
            }

        } catch (SQLException | InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return listT;
    }
}
