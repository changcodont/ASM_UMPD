/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSysDAO;

import DaoTao.db.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getBangdiem(Integer makh) {
        String sql = "{CALL sp_BangDiem (?)}";
        String[] cols = {"MaNH", "HoTen", "Diem"};
        return getListOfArray(sql, cols, makh);
    }

    public List<Object[]> getLuongNguoiHoc() {
        String sql = "{CALL sp_ThongKeNguoiHoc}";
        String[] cols = {"Nam", "SoLuong", "DauTien", "CuoiCung"};
        return getListOfArray(sql, cols);
    }

    public List<Object[]> getDiemChuyenDe() {
        String sql = "{CALL sp_ThongKeDiem}";
        String[] cols = {"ChuyenDe", "SoHV", "ThapNhat", "CaoNhat", "TrungBinh"};
        return getListOfArray(sql, cols);
    }

    public List<Object[]> getDoanhThu(int nam) {
        String sql = "{CALL sp_ThongKeDoanhThu (?)}";
        String[] cols = {"ChuyenDe", "SoKH", "DoanhThu", "ThapNhat", "CaoNhat", "TrungBinh"};
        return getListOfArray(sql, cols, nam);
    }
}
