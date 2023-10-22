/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSysDAO;

import DaoTao.db.DBHelper;
import EduSys.entity.NhanVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String> {

    final String INSERT_SQL = "INSERT into NhanVien(MaNV,MatKhau,Hoten,VaiTro)values(?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NhanVien SET MatKhau=?,Hoten=?,VaiTro=? where MaNV=?";
    final String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    final String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien where MaNV=?";

    @Override
    public void insert(NhanVien entity) {
        DBHelper.update(INSERT_SQL, entity.getMaNV(), entity.getMatKhau(), entity.getHoten(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        DBHelper.update(UPDATE_SQL, entity.getMatKhau(), entity.getHoten(), entity.isVaiTro(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        DBHelper.update(DELETE_SQL, id);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<NhanVien>();
        try {
            ResultSet rs = (ResultSet) DBHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setHoten(rs.getString("HoTen"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

}
