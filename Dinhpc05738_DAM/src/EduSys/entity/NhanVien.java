/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.entity;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class NhanVien implements Serializable {

    private String maNV;
    private String hoten;
    private String matKhau;
    private boolean vaiTro;

    public NhanVien(String maNV, String hoten, String matKhau, boolean vaiTro) {
        this.maNV = maNV;
        this.hoten = hoten;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public NhanVien() {
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    @Override
    public String toString() {
        return this.hoten;

    }
}
