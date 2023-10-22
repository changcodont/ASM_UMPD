/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package EduSys.Poly.UI;

import EduSys.entity.ChuyenDe;
import EduSys.entity.KhoaHoc;
import EduSys.utils.Auth;
import EduSys.utils.MsgBox;
import EduSys.utils.XDate;
import EduSysDAO.ChuyenDeDAO;
import EduSysDAO.KhoaHocDAO;
import java.awt.HeadlessException;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class KhoaHocJDialog extends javax.swing.JDialog {

    ChuyenDeDAO cddao = new ChuyenDeDAO();
    KhoaHocDAO dao = new KhoaHocDAO();
    int row = -1;

    /**
     * Creates new form QuanLyKhoaHocJDialog
     */
    public KhoaHocJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         setLocationRelativeTo(null);
        txtHocPhi.setEnabled(false);
         txtNguoiTao.setEnabled(false);
        txtThoiLuong.setEnabled(false);
        this.fillComboBoxChuyenDe();

        this.updateStatus();
    }
    
    
     void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
        model.setRowCount(0);
        try {
            ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
            List<KhoaHoc> list = dao.selectByChuyenDe(chuyenDe.getMaCD());
            for (KhoaHoc kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getThoiLuong(),
                    kh.getHocPhi(),
                    XDate.toString(kh.getNgayKG(), "MM/dd/yyyy"),
                    kh.getMaNV(),
                    XDate.toString(kh.getNgayTao(), "MM/dd/yyyy"),
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
          String maSV = txtChuyenDe.getText();

        // Kiểm tra xem có dữ liệu cần xóa đã được chọn hay chưa
        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu để thêm!");
            return;
        }
        KhoaHoc model = getForm();
      
        model.setNgayTao(new Date());
        try {
            dao.insert(model);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (HeadlessException e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
        }
    }

    void update() {
          String maSV = txtChuyenDe.getText();

        // Kiểm tra xem có dữ liệu cần xóa đã được chọn hay chưa
        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu để sửa !");
            return;
        }
        KhoaHoc model = getForm();
        try {
            dao.update(model);
            this.fillTable();
             this.clearForm();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
            e.printStackTrace();
        }
    }

    void delete() {
          String maSV = txtChuyenDe.getText();

        // Kiểm tra xem có dữ liệu cần xóa đã được chọn hay chưa
        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu để xóa!");
            return;
        }
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa khóa học!");
        } else {
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa khóa học này?")) {
                Integer makh = Integer.valueOf(cboChuyenDe.getToolTipText());
                try {
                    dao.delete(String.valueOf(makh));
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    void clearFormchinh() {
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        KhoaHoc kh = new KhoaHoc();
        kh.setMaCD(chuyenDe.getMaCD());
        kh.setMaNV(Auth.user.getMaNV());
        kh.setNgayKG(XDate.addDay(new Date(), 30));
        kh.setNgayTao(new Date());
        this.setForm(kh);
        this.row = -1;
        this.updateStatus();
    }
    
    void clearForm() {
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        KhoaHoc kh = new KhoaHoc();
        kh.setMaCD(chuyenDe.getMaCD());
        kh.setMaNV(Auth.user.getMaNV());
        kh.setNgayKG(XDate.addDay(new Date(), 30));
        kh.setNgayTao(new Date());
        this.setForm(kh);
        this.row = -1;
        this.updateStatus();
    }

    void edit() {
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        Integer makh = (Integer) tblKhoaHoc.getValueAt(this.row, 0);
        KhoaHoc kh = dao.selectById(String.valueOf(makh));
        this.setForm(kh);
        this.updateStatus();
        jTabbedPane1.setSelectedIndex(0);
        lblChuyenDe.setText(chuyenDe.getTenCD());
    }

    void setForm(KhoaHoc kh) {
        cboChuyenDe.setToolTipText(String.valueOf(kh.getMaKH()));
        cboChuyenDe.setSelectedItem(cddao.selectById(kh.getMaCD()));;
        txtKhaiGiang.setText(XDate.toString(kh.getNgayKG(), "MM/dd/yyyy"));
        txtHocPhi.setText(String.valueOf(kh.getHocPhi()));
        txtThoiLuong.setText(String.valueOf(kh.getThoiLuong()));
        txtNguoiTao.setText(kh.getMaNV());
        txtNgayTao.setText(XDate.toString(kh.getNgayTao(), "MM/dd/yyyy"));
        txtGhiChu.setText(kh.getGhiChu());
    }

 KhoaHoc getForm() {
    KhoaHoc kh = new KhoaHoc();
     ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
     if (txtNguoiTao.getText().equals("")) {
         kh.setMaNV(Auth.user.getMaNV());
     } else {
         kh.setMaNV(txtNguoiTao.getText());
     }
     kh.setMaCD(cd.getMaCD());
     kh.setThoiLuong(Integer.parseInt(txtThoiLuong.getText()));
     kh.setNgayKG(XDate.toDate(txtKhaiGiang.getText(), "MM/dd/yyyy"));
     
     kh.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
     kh.setGhiChu(txtGhiChu.getText());
     kh.setNgayTao(XDate.toDate(txtNgayTao.getText(), "MM/dd/yyyy"));
     kh.setMaKH(Integer.parseInt(cboChuyenDe.getToolTipText()));
     return kh;
}


    void first() {
        this.row = 0;
        this.edit();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblKhoaHoc.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblKhoaHoc.getRowCount() - 1;
        this.edit();
    }

    
    void updateStatus() {
   
        
        
           boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblKhoaHoc.getRowCount() - 1);
        txtChuyenDe.setEnabled(edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(!edit);
        btnXoa.setEnabled(!edit);
        btnFirst.setEnabled(!edit && !first);
        btnPre.setEnabled(!edit && !first);
        btnNext.setEnabled(!edit && !last);
        btnLast.setEnabled(!edit && !last);
    }

    void fillComboBoxChuyenDe() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
        model.removeAllElements();
        List<ChuyenDe> list = cddao.selectAll();
        for (ChuyenDe cd : list) {
            model.addElement(cd);
        }
    }

    void chonChuyenDe() {
        ChuyenDe chuyende = (ChuyenDe) cboChuyenDe.getSelectedItem();
        if (chuyende != null) {
            txtThoiLuong.setText(String.valueOf(chuyende.getThoiLuong()));
            txtHocPhi.setText(String.valueOf(chuyende.getHocPhi()));
            txtChuyenDe.setText(chuyende.getTenCD());
            txtGhiChu.setText(chuyende.getTenCD());

            this.fillTable();
            this.row = -1;
            this.updateStatus();
            jTabbedPane1.setSelectedIndex(1);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblChuyenDe = new javax.swing.JLabel();
        lblKhaiGiang = new javax.swing.JLabel();
        txtChuyenDe = new javax.swing.JTextField();
        txtKhaiGiang = new javax.swing.JTextField();
        lblHocPhi = new javax.swing.JLabel();
        lblThoiLuong = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        txtThoiLuong = new javax.swing.JTextField();
        lblNguoiTao = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        lblGhiChu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnNew = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhoaHoc = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cboChuyenDe = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EduSys-Quản lý Khóa học");

        lblChuyenDe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblChuyenDe.setText("Chuyên đề");

        lblKhaiGiang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKhaiGiang.setText("Khai giảng");

        lblHocPhi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHocPhi.setText("Học phí");

        lblThoiLuong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThoiLuong.setText("Thời Lượng(Giờ)");

        lblNguoiTao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNguoiTao.setText("Người tạo");

        lblNgayTao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNgayTao.setText("Ngày tạo");

        lblGhiChu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGhiChu.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Refresh.png"))); // NOI18N
        btnNew.setText("Làm mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(153, 102, 255), new java.awt.Color(204, 204, 255))));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPre.setText("<<");
        btnPre.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 255, 255), new java.awt.Color(153, 153, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(153, 153, 255))));
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(204, 102, 255))));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 204, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(153, 153, 255))));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addGap(53, 53, 53)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104)))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHocPhi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(txtChuyenDe, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblChuyenDe)
                            .addComponent(lblHocPhi)
                            .addComponent(lblNguoiTao)
                            .addComponent(txtNguoiTao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNgayTao)
                            .addComponent(txtKhaiGiang, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                            .addComponent(lblKhaiGiang)
                            .addComponent(lblThoiLuong)
                            .addComponent(txtThoiLuong)
                            .addComponent(txtNgayTao)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblGhiChu)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChuyenDe)
                    .addComponent(lblKhaiGiang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKhaiGiang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHocPhi)
                    .addComponent(lblThoiLuong))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNguoiTao)
                    .addComponent(lblNgayTao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblGhiChu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNew)
                        .addComponent(btnXoa)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        jTabbedPane1.addTab("Cập nhật", jPanel1);

        tblKhoaHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MaKH", "Thời Lượng", "Học Phí", "Khai giảng", "Tạo bởi", "Ngày tạo"
            }
        ));
        tblKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKhoaHocMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhoaHoc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách", jPanel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("CHUYÊN ĐỀ");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cboChuyenDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lập trình java cơ bản", "Lập trình java nâng cao", "lập trình java nâng cao" }));
        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Exit.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("EduSys/Poly/UI/Bundle"); // NOI18N
        jButton2.setText(bundle.getString("GioiThieuJDialog.jButton1.text")); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 255, 255), new java.awt.Color(102, 204, 255)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jTabbedPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblKhoaHocMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoaHocMousePressed
        if (evt.getClickCount() == 2) {
            this.row = tblKhoaHoc.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblKhoaHocMousePressed

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        this.chonChuyenDe();
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearFormchinh();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        prev();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KhoaHocJDialog dialog = new KhoaHocJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblChuyenDe;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblHocPhi;
    private javax.swing.JLabel lblKhaiGiang;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JLabel lblThoiLuong;
    private javax.swing.JTable tblKhoaHoc;
    private javax.swing.JTextField txtChuyenDe;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtKhaiGiang;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables


   
}
