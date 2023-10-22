/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSysDAO;

import EduSys.entity.ChuyenDe;
import EduSys.entity.NhanVien;
import DaoTao.db.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {

    public static boolean isTenCDExists(String tenCD) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String INSERT_SQL = "INSERT INTO ChuyenDe  VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE chuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
    String DELETE_SQL = "DELETE FROM chuyenDe WHERE MaCD=?";
    String SELECT_ALL_SQL = "SELECT * FROM ChuyenDe";
    String SELECT_BY_ID_SQL = "SELECT * FROM chuyenDe WHERE MaCD=?";

    @Override
    public void insert(ChuyenDe entity) {
        DBHelper.update(INSERT_SQL,
                entity.getMaCD(),
                entity.getTenCD(),
                entity.getHocPhi(),
                entity.getThoiLuong(),
                entity.getHinh(),
                entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        DBHelper.update(UPDATE_SQL,
                entity.getTenCD(),
                entity.getHocPhi(),
                entity.getThoiLuong(),
                entity.getHinh(),
                entity.getMoTa(),
                entity.getMaCD());
    }

    @Override
    public void delete(String id) {
        DBHelper.update(DELETE_SQL, id);
    }

    @Override
    public ChuyenDe selectById(String id) {
        List<ChuyenDe> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = (ResultSet) DBHelper.query(sql, args);
            while (rs.next()) {
                ChuyenDe model = new ChuyenDe();
                model.setMaCD(rs.getString("MaCD"));
                model.setTenCD(rs.getString("TenCD"));
                model.setHocPhi(rs.getDouble("HocPhi"));
                model.setThoiLuong(rs.getInt("ThoiLuong"));
                model.setHinh(rs.getString("hinh"));
                model.setMoTa(rs.getString("MoTa"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
