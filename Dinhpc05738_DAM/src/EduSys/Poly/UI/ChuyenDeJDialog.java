/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package EduSys.Poly.UI;

import EduSys.entity.ChuyenDe;
import EduSys.entity.NguoiHoc;
import EduSys.entity.NhanVien;
import EduSys.utils.MsgBox;
import EduSys.utils.XImage;
import EduSysDAO.ChuyenDeDAO;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Admin
 */
public class ChuyenDeJDialog extends javax.swing.JDialog {

    JFileChooser fileChooser = new JFileChooser();
    ChuyenDeDAO dao = new ChuyenDeDAO();

    DefaultTableModel model;
    int row = 0;
    String linkAnh = "C:\\Users\\84934\\Desktop\\fill_de_thi_DA\\Dinhpc05738_DAM\\src\\Icons\\";

    /**
     * Creates new form QuanLyChuyenDe
     */
    public ChuyenDeJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fillTable();
        init();
    }

     void init() {
        setLocationRelativeTo(null);
        setTitle("EduSys QUẢN LÝ CHUYÊN ĐỀ");
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblChuyende.getModel();
        model.setRowCount(0);
        try {
            List<ChuyenDe> list = dao.selectAll();
            for (ChuyenDe cd : list) {
                Object[] row = {
                    cd.getMaCD(),
                    cd.getTenCD(),
                    cd.getHocPhi(),
                    cd.getThoiLuong(),
                    cd.getHinh(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setForm(ChuyenDe cd) {
        txtMaCD.setText(cd.getMaCD());
        txtTenCD.setText(cd.getTenCD());
        txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
        txtThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
        txtMoTa.setText(cd.getMoTa());
        String hinhAnh = cd.getHinh();
        updateHinh(hinhAnh);
    }

    ChuyenDe getForm() {
        ChuyenDe cd = new ChuyenDe();

        // Kiểm tra và hiển thị thông báo lỗi nếu người dùng không nhập mã chuyên đề hoặc mã không hợp lệ
        String maCD = txtMaCD.getText().trim();
        if (maCD.isEmpty()) {
            // Hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã chuyên đề!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaCD.requestFocus();
            return cd;
        } else if (!maCD.matches("^CD\\d{3}$")) {
            // Hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Mã chuyên đề phải bắt đầu bằng chuỗi 'CD' và có đúng 5 ký tự số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaCD.requestFocus();
            return cd;
        } else {
            cd.setMaCD(maCD);
        }

        // Kiểm tra và hiển thị thông báo lỗi nếu người dùng không nhập tên chuyên đề hoặc tên không hợp lệ
        String tenCD = txtTenCD.getText().trim();
        if (tenCD.isEmpty()) {
            // Hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên chuyên đề!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtTenCD.requestFocus();
            return cd;
        } else if (!tenCD.matches("^[a-zA-Z\\p{L}\\s]+$")) {
            // Hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Tên chuyên đề chỉ được chứa các ký tự chữ cái và chữ cái tiếng Việt!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtTenCD.requestFocus();
            return cd;
        } else {
            cd.setTenCD(tenCD);
        }
        // Kiểm tra và hiển thị thông báo lỗi nếu người dùng không nhập thời lượng hoặc thời lượng không phải là số
        try {
            int thoiLuong = Integer.parseInt(txtThoiLuong.getText());
            if (thoiLuong <= 0) {
                // Hiển thị thông báo lỗi
                JOptionPane.showMessageDialog(this, "Thời lượng phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtThoiLuong.requestFocus();
                return cd;
            } else {
                cd.setThoiLuong(thoiLuong);
            }
        } catch (NumberFormatException e) {
            // Hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Thời lượng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtThoiLuong.requestFocus();
            return cd;
        }

        // Kiểm tra và hiển thị thông báo lỗi nếu người dùng không nhập học phí hoặc học phí là số âm
        try {
            double hocPhi = Double.parseDouble(txtHocPhi.getText());
            if (hocPhi <= 0) {
                // Hiển thị thông báo lỗi
                JOptionPane.showMessageDialog(this, "Học phí phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtHocPhi.requestFocus();
                return cd;
            } else {
                cd.setHocPhi(hocPhi);
            }
        } catch (NumberFormatException e) {
            // Hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Học phí phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtHocPhi.requestFocus();
            return cd;
        }

        // Kiểm tra và hiển thị thông báo lỗi nếu người dùng không nhập mô tả
        if (txtMoTa.getText().isEmpty()) {
            // Hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMoTa.requestFocus();
            return cd;
        } else {
            cd.setMoTa(txtMoTa.getText());
        }
        if (lblHinh.getToolTipText() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hình ảnh!");
            return cd;

        } else {
            cd.setHinh(lblHinh.getToolTipText());
        }

        return cd;
    }

    void clearFormgoc() {
        int chon = JOptionPane.showConfirmDialog(this, "Bạn chắc muốn làm mới không", "Làm mới môn học", JOptionPane.YES_NO_OPTION);

        //Chọn có làm mới còn không dữ nguyên ban đầu
        if (chon == JOptionPane.YES_OPTION) {
            ChuyenDe cd = new ChuyenDe();
            this.setForm(cd);
            this.row = -1;

            this.updateStatus();
        }

    }

    void clearForm() {

        //Chọn có làm mới còn không dữ nguyên ban đầu
        ChuyenDe cd = new ChuyenDe();
        this.setForm(cd);
        this.row = -1;

        this.updateStatus();

    }

    void insert() {
        String maSV = txtMaCD.getText();

        // Kiểm tra xem có dữ liệu cần xóa đã được chọn hay chưa
        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu để thêm!");
            return;
        }

        try {
            String maCD = txtMaCD.getText();
            ChuyenDe nh = dao.selectById(maCD);
            if (nh != null) {
                MsgBox.alert(this, "Mã chuyên đề đã tồn tại!");
                return;
            }

            ChuyenDe cd = getForm();
            dao.insert(cd);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            System.out.print(e);

            MsgBox.alert(this, "Thêm mới  thất bại!");
        }

    }

    void update() {
        String maSV = txtMaCD.getText();

        // Kiểm tra xem có dữ liệu cần xóa đã được chọn hay chưa
        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu để chỉnh sửa!");
            return;
        }
        ChuyenDe cd = getForm();
        try {
            dao.update(cd);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            System.out.print(e);

            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        String maSV = txtMaCD.getText();

        // Kiểm tra xem có dữ liệu cần xóa đã được chọn hay chưa
        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu để xóa!");
            return;
        }
        String maCD = txtMaCD.getText();
        try {
            dao.delete(maCD);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Xóa thành công!");
        } catch (Exception e) {
            System.out.print(e);

            MsgBox.alert(this, "Xóa thất bại!");
        }
    }

    void edit() {
        try {
            String maCD = (String) tblChuyende.getValueAt(this.row, 0);
            ChuyenDe cd = dao.selectById(maCD);
            if (cd != null) {
                this.setForm(cd);
                tabs.setSelectedIndex(0);
                this.updateStatus();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
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
        if (this.row < tblChuyende.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblChuyende.getRowCount() - 1;
        this.edit();
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblChuyende.getRowCount() - 1);
        txtMaCD.setEnabled(!edit);
        btnthemm.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPre.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    public void updateHinh(String image) {
        ImageIcon imageIcon = new ImageIcon(linkAnh + image); // Tạo đối tượng ImageIcon
        Image img = imageIcon.getImage(); // Lấy Image từ ImageIcon
        Image scaledImage = img.getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), Image.SCALE_SMOOTH); // Thay đổi kích thước hình ảnh
        ImageIcon icon = new ImageIcon(scaledImage); // Tạo mới ImageIcon với hình ảnh đã được thay đổi kích thước
        lblHinh.setIcon(icon); // Thiết lập hình ảnh cho JLabel
    }

    //tìm kiếm
    public void Search(String str) {

        model = (DefaultTableModel) tblChuyende.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblChuyende.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        txtMaCD = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTenCD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnthemm = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChuyende = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");

        lblHinh.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(204, 153, 255), new java.awt.Color(153, 153, 255))));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhMousePressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Mã chuyên đề");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Tên chuyên đề");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Thời lượng( giờ)");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Học phí");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Mô tả chuyên đề");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

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

        btnthemm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Add.png"))); // NOI18N
        btnthemm.setText("Thêm");
        btnthemm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemmActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 204, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(153, 153, 255))));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtThoiLuong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                .addComponent(txtTenCD, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaCD, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addGap(31, 31, 31)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(btnthemm, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnSua)
                    .addComponent(btnthemm)
                    .addComponent(btnXoa))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblChuyende.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã CD", "Ten CD", "Học Phí", "Thời lượng", "Hình ảnh"
            }
        ));
        tblChuyende.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChuyendeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblChuyendeMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblChuyende);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm"));

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                .addGap(22, 22, 22))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // Mở cửa sổ
        JFileChooser fc = new JFileChooser(linkAnh);
        int rs = fc.showOpenDialog(null);
        if (rs == JFileChooser.APPROVE_OPTION) {
            try {
                // Lấy hình ảnh
                File selectedFile = fc.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedFile.getPath());

                // Điều chỉnh kích thước hình ảnh
                Image image = icon.getImage();
                int lblWidth = lblHinh.getWidth();
                int lblHeight = lblHinh.getHeight();
                Image resizedImage = image.getScaledInstance(lblWidth, lblHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                // Hiển thị hình ảnh trong lblHinhAnh
                lblHinh.setIcon(resizedIcon);
                lblHinh.setToolTipText(selectedFile.getName());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_lblHinhMouseClicked

    private void tblChuyendeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuyendeMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblChuyende.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblChuyendeMouseClicked

    private void lblHinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMousePressed

    }//GEN-LAST:event_lblHinhMousePressed

    private void tblChuyendeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuyendeMousePressed
        if (evt.getClickCount() == 2) {
            this.row = tblChuyende.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblChuyendeMousePressed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearFormgoc();
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

    private void btnthemmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemmActionPerformed
        insert();
    }//GEN-LAST:event_btnthemmActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        String searchString = txtTimKiem.getText();
        Search(searchString);
        if (tblChuyende.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy kết quả nào!");

        } else {
            tblChuyende.setRowSelectionInterval(0, 0);
            row = tblChuyende.getSelectedRow();
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed

    }//GEN-LAST:event_btnTimKiemActionPerformed

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
            java.util.logging.Logger.getLogger(ChuyenDeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChuyenDeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChuyenDeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChuyenDeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChuyenDeJDialog dialog = new ChuyenDeJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnthemm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChuyende;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaCD;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenCD;
    private javax.swing.JTextField txtThoiLuong;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
   
}
