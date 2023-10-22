/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSysDAO;

import EduSys.entity.HocVien;
import EduSys.entity.ChuyenDe;
import DaoTao.db.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HocVienDAO extends EduSysDAO<HocVien, String> {

    String INSERT_SQL = "INSERT INTO HocVien  VALUES (?, ?, ? )";
    String UPDATE_SQL = "UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
    String DELETE_SQL = "DELETE FROM HocVien WHERE MaHV=?";
    String SELECT_ALL_SQL = "SELECT * FROM HocVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM HocVien WHERE MaHV=?";
    String SELECT_BY_KHOA_HOC_SQL = "select * from HocVien where MaKH = ?";

    @Override
    public void insert(HocVien entity) {
        DBHelper.update(INSERT_SQL,
                entity.getMaKH(),
                entity.getMaNH(),
                entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        DBHelper.update(UPDATE_SQL,
                entity.getMaKH(),
                entity.getMaNH(),
                entity.getDiem(),
                entity.getMaHV());
    }

    @Override
    public void delete(String id) {
        DBHelper.update(DELETE_SQL, id);
    }

    @Override
    public HocVien selectById(String id) {
        List<HocVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = (ResultSet) DBHelper.query(sql, args);
            while (rs.next()) {
                HocVien model = new HocVien();
                model.setMaHV(rs.getInt("MaHV"));
                model.setMaKH(rs.getInt("MaKH"));
                model.setMaNH(rs.getString("MaNH"));
                model.setDiem(rs.getDouble("Diem"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HocVien> selectByKhoaHoc(int makh) {
        return selectBySql(SELECT_BY_KHOA_HOC_SQL, makh);
    }

}
