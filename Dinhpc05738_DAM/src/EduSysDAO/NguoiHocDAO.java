/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSysDAO;

import EduSys.entity.NguoiHoc;
import EduSys.entity.NhanVien;
import DaoTao.db.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String> {

        String INSERT_SQL = "insert into NguoiHoc(MaNH,HoTen,GioiTinh,NgaySinh,DienThoai,Email,GhiChu,MaNV,NgayDK) values (?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=? , NgayDK=? WHERE MaNH=?";
    String DELETE_SQL = "DELETE FROM NguoiHoc WHERE MaNH=?";
    String SELECT_ALL_SQL = "SELECT * FROM NguoiHoc";
    String SELECT_BY_ID_SQL = "SELECT * FROM NguoiHoc WHERE MaNH=?";
    String SELECT_NOT_IN_COURSE_SQL = "select *from NguoiHoc where HoTen like ? and MaNH not in (select MaNH from HocVien where MaKH = ?)";

    @Override
    public void insert(NguoiHoc entity) {
         DBHelper.update(INSERT_SQL, entity.getMaNH(),entity.getHoTen(),entity.isGioiTinh(),entity.getNgaySinh(),entity.getDienThoai()
                ,entity.getEmail(),entity.getGhiChu(),entity.getMaNV(),entity.getNgayDK());
        
    }

    @Override
    public void update(NguoiHoc entity) {
        DBHelper.update(UPDATE_SQL,
                entity.getHoTen(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.getDienThoai(),
                entity.getEmail(),
                entity.getGhiChu(),
                entity.getMaNV(),
                entity.getNgayDK(),
                entity.getMaNH()
        );
    }

    @Override
    public void delete(String id) {
        DBHelper.update(DELETE_SQL, id);
    }

    @Override
    public NguoiHoc selectById(String id) {
        List<NguoiHoc> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = (ResultSet) DBHelper.query(sql, args);
            while (rs.next()) {
                NguoiHoc model = new NguoiHoc();
                model.setMaNH(rs.getString("MaNH"));
                model.setHoTen(rs.getString("HoTen"));
                model.setNgaySinh(rs.getDate("NgaySinh"));
                model.setGioiTinh(rs.getBoolean("GioiTinh"));
                model.setDienThoai(rs.getString("DienThoai"));
                model.setEmail(rs.getString("Email"));
                model.setGhiChu(rs.getString("GhiChu"));
                model.setMaNV(rs.getString("MaNV"));
                model.setNgayDK(rs.getDate("NgayDK"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NguoiHoc> selectByKeyword(String keyword) {
        String sql = "select * from NguoiHoc where HoTen like ?";
        return this.selectBySql(sql,"%"+keyword+"%");
    }
      public List<NguoiHoc> selectNotInCourse(int makh, String keyword){
        return selectBySql(SELECT_NOT_IN_COURSE_SQL, "%"+ keyword +"%", makh);   
    }

}
