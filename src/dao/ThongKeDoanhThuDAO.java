package dao;

import dto.ThongKeResult;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ThongKeDoanhThuDAO extends AbstractDAO<ThongKeResult> {

    public ThongKeResult thongKeDoanhThu(int thang, int nam) throws SQLException {
        try (Connection conn = getConnection()) {
            CallableStatement cs = conn.prepareCall("{call thong_ke_doanh_thu(?, ?, ?, ?)}");
            cs.setInt(1, thang);
            cs.setInt(2, nam);
            cs.registerOutParameter(3, java.sql.Types.INTEGER);
            cs.registerOutParameter(4, java.sql.Types.DOUBLE);

            cs.execute();

            int soLuongVe = cs.getInt(3);
            double tongTien = cs.getDouble(4);

            return new ThongKeResult(soLuongVe, tongTien);
        }
    }
}
