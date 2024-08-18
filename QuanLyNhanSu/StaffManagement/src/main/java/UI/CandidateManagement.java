/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package UI;

import CLASS.ChucVu;
import CLASS.UngVien;
import DAO.ChucVuDAO;
import DAO.UngVienDAO;
import UTILS.Msgbox;
import UTILS.XDate;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zander
 */
public class CandidateManagement extends javax.swing.JInternalFrame {

    /**
     * Creates new form EmployeeManagement
     */
    private JDesktopPane Desktop;
    public CandidateManagement() {
        initComponents();
        setTitle("Quản lý ứng viên");
        this.fillTable();
        this.fillComboBoxChucVu();
        this.row=0;
        this.edit();
        
        
        this.updateStatus();
    }
    

    int row = -1;
    UngVienDAO uvDAO = new UngVienDAO();
    ChucVuDAO cvdao = new ChucVuDAO();
    static String maUngVien,tenUv,ngaySinhUv,emailUv,sdtUv,dcUv,cvUv;
    static boolean gioiTinhUv;
    
    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblUngVien.getModel();
        model.setRowCount(0);
        try {
            List<UngVien> list = uvDAO.selectAll();
            for (UngVien uv : list) {
                Object[] row = {
                    uv.getMaUngVien(),
                    uv.getHoTen(),
                    uv.isGioiTinh() ? "Nam" : "Nữ",
                    uv.getNgaySinh(),
                    uv.getSDT(),
                    uv.getEmail(),
                    uv.getDiaChi(),
                    uv.getMaChucVu(),
                    uv.getMoTa()
                };
                model.addRow(row);
                txtMaUngVien.setEditable(false);
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillComboBoxChucVu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaChucVu.getModel();
        model.removeAllElements();
        try {
            List<ChucVu> list = cvdao.selectAll();
            for (ChucVu cv: list) {
                model.addElement(cv.toTenCv());
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }

    void setForm(UngVien uv) {
        txtMaUngVien.setText(uv.getMaUngVien());
        txtHoTen.setText(uv.getHoTen());
        if (uv.getNgaySinh() != null) {
            txtNgaySinh.setText(XDate.toString(uv.getNgaySinh(), "dd-MM-yyyy"));
        } else {
            txtNgaySinh.setText("");
        }
        txtSdt.setText(uv.getSDT());
        txtDiaChi.setText(uv.getDiaChi());
        txtMoTa.setText(uv.getMoTa());
        txtEmail.setText(uv.getEmail());
        rdoNam.setSelected(uv.isGioiTinh());
        rdoNu.setSelected(!uv.isGioiTinh());
        cboMaChucVu.setSelectedItem(uvDAO.tenCv(uv.getMaUngVien(),uv.getMaChucVu()));
    }

    UngVien getForm() {
        UngVien uv = new UngVien();
        
        // Kiểm tra Họ tên
        String ten = txtHoTen.getText();
        if (ten.isEmpty()) {
            Msgbox.alert(this, "Họ tên không được để trống!");
            return null;
        }
        
        if (ten.length() > 100) {
            Msgbox.alert(this, "Họ tên không được vượt quá 100 kí tự!");
            return null;
        }
        uv.setHoTen(ten);
        
        // Kiểm tra Ngày sinh
        
        if (txtNgaySinh.getText().isEmpty()) {
            Msgbox.alert(this, "Không được để trống ngày sinh!");
            return null;
        } else {
            try {
                String ngaySinhStr = txtNgaySinh.getText();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate ngaySinh = LocalDate.parse(ngaySinhStr, dtf);
                LocalDate startDate = LocalDate.of(1969, 1, 1);
                LocalDate endDate = LocalDate.of(2006, 12, 31);
                if (ngaySinh.isBefore(startDate) || ngaySinh.isAfter(endDate)) {
                    Msgbox.alert(this, "Ngày sinh phải trong khoảng từ năm 1969 đến 2006!");
                    return null;
                }
                uv.setNgaySinh(java.sql.Date.valueOf(ngaySinh));
            } catch (DateTimeParseException e) {
                Msgbox.alert(this, "Định dạng ngày sinh không hợp lệ!");
                return null;
            }
        }
        
        // Kiểm tra Số điện thoại
        String sdt = txtSdt.getText();
        if (sdt.isEmpty()) {
            Msgbox.alert(this, "Số điện thoại không được để trống!");
            return null;
        }
        if (sdt.length() > 10) {
            Msgbox.alert(this, "Số điện thoại phải dưới 10 kí tự!");
            return null;
        }
         {
            String PHONE_PATTERN = "^(0|\\+84)(\\d{9,10})$";
            if (!sdt.matches(PHONE_PATTERN)) {
                Msgbox.alert(this, "Không đúng định dạng số điện thoại");
                return null;
            }
            uv.setSDT(sdt);
        }

        // Kiểm tra Email
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            Msgbox.alert(this, "Không được để trống email");
            return null;
        }else {
            String emailRegex = "\\w+@\\w+(\\.\\w+){1,4}";
            if (!email.matches(emailRegex)) {
                Msgbox.alert(this, "Không đúng định dạng email");
                return null;
            } else if (email.length() > 100) {
                Msgbox.alert(this, "Email không được vượt quá 100 ký tự");
                return null;
            }
            
            uv.setEmail(email);
        }
        
        //Giới tính
        uv.setGioiTinh(rdoNam.isSelected());
        

        //địa chỉ
        String diaChi = txtDiaChi.getText().trim();
        if (diaChi.isEmpty()) {
            Msgbox.alert(this, "Không được để trống địa chỉ");
            return null;
        }
        uv.setDiaChi(diaChi);
        
        //mã chức vụ
        uv.setMaChucVu(uvDAO.maCv((String) cboMaChucVu.getSelectedItem()));
        
        //mô tả
        uv.setMoTa(txtMoTa.getText());
        
        return uv;
    }

    void updateStatus() {
    boolean edit = this.row >= 0;
    boolean first = this.row == 0;
    boolean last = this.row == tblUngVien.getRowCount() - 1;
    // trạng thái form
    txtMaUngVien.setEditable(false);
    btnThem.setEnabled(!edit);
    btnThemNhanVien.setEnabled(edit);
    btnSua.setEnabled(edit);
    btnXoa.setEnabled(edit);
    btnFirst.setEnabled(edit && !first);
    btnPrev.setEnabled(edit && !first);
    btnNext.setEnabled(edit && !last);
    btnLast.setEnabled(edit && !last);
}


    void edit() {
            String MaUngVien = (String) tblUngVien.getValueAt(this.row, 0);
            UngVien model = uvDAO.selectById(MaUngVien);
                this.setForm(model);
                this.updateStatus();
                tabs.setSelectedIndex(0);
           
    }

    void clearForm() {
        String nextMaUngVien = uvDAO.getNextMaUngVien();
    txtMaUngVien.setText(nextMaUngVien);
    txtHoTen.setText("");
    txtSdt.setText("");
    txtNgaySinh.setText("");
    txtMoTa.setText("");
    txtEmail.setText("");
    txtDiaChi.setText("");
    rdoNam.setSelected(true);
    this.row = -1;
    this.updateStatus();
    txtMaUngVien.setEditable(false);
}


    void insert() throws SQLException {
    UngVien uv = getForm();   
    if(uv==null){
        return;
    }
String phone = txtSdt.getText().trim();
                if (uvDAO.isPhoneExists(phone)) {
                    JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate email
                String email = txtEmail.getText().trim();
                if (uvDAO.isEmailExists(email)) {
                    JOptionPane.showMessageDialog(this, "Email đã tồn tại!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                uv.setMaUngVien(uvDAO.getNextMaUngVien());
    // Tiến hành thêm mới
    if(Msgbox.confirm(this,"Bạn muốn thêm ứng viên này?")){

            try {
                // Lấy mã ứng viên tiếp theo từ DAO
                // Thiết lập mã ứng viên tự động tăng

                
                this.uvDAO.insert(uv);
                this.fillTable();
                this.clearForm();
                Msgbox.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                Msgbox.alert(this, "Thêm mới thất bại!");
                System.out.print(e);
            }
        }

    
}


    void update() {
        UngVien uv = getForm();
        if(uv==null){
            return;
        }
        if(Msgbox.confirm(this,"Bạn muốn cập nhật ứng viên này?")){
        // Tiến hành cập nhật
        try {
            uvDAO.update(uv);
            fillTable();
            clearForm();
            Msgbox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            Msgbox.alert(this, "Cập nhật thất bại!");
        }}
    }

    void delete() {
        if (Msgbox.confirm(this, "Bạn thực sự muốn xóa ứng viên này?")) {
            String MaUngVien = txtMaUngVien.getText();
            try {
                uvDAO.delete(MaUngVien);
                this.fillTable();
                
                Msgbox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                Msgbox.alert(this, "Xóa thất bại!");
                System.out.println(e);
            }
            if(row>=tblUngVien.getRowCount()){
            row = tblUngVien.getRowCount()-1;
            this.edit();
        }
        else{
            this.edit();
        }
        }
    }
    
    private void timKiem() {
        this.fillFind();
        this.clearForm();
        this.row = -1;
        updateStatus();
    }
    
    private void fillFind() {
        DefaultTableModel model = (DefaultTableModel) tblUngVien.getModel();
        model.setRowCount(0);

        String keyword = txtFind.getText().trim();
        try {
            List<UngVien> dsUV = new ArrayList<>();
            switch (cboFind.getSelectedIndex()) {
                case 0:
                    UngVien uv = uvDAO.selectById(keyword);
                    if (uv != null) {
                        dsUV.add(uv);
                    }
                    break;
                case 1:
                    dsUV = uvDAO.selectByHoTen(keyword); // Danh sách nhân viên theo tên
                    break;
                case 2:                    
                    dsUV = (List<UngVien>) uvDAO.selectByMaChucVu(keyword); // Danh sách nhân viên theo mã chức vụ
                    break;
                default:
                    this.fillTable();
                    break;
            }

            for (UngVien uv : dsUV) {
                Object[] row = {
                    uv.getMaUngVien(),
                    uv.getHoTen(),
                    uv.isGioiTinh() ? "Nam" : "Nữ",
                    uv.getNgaySinh(),
                    uv.getSDT(),
                    uv.getEmail(),
                    uv.getDiaChi(),
                    uv.getMaChucVu(),
                    uv.getMoTa()              
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.print(e);
        }
    }

    void first() {
        row = 0;
        edit();
    }

    void prev() {
        if (row > 0) {
            row--;
            edit();
        }
    }

    void next() {
        if (row < tblUngVien.getRowCount() - 1) {
            row++;
            edit();
        }
    }

    void last() {
        row = tblUngVien.getRowCount() - 1;
        edit();
    }
    void themNhanVien() {
        try {
            
            maUngVien = txtMaUngVien.getText();
            tenUv = txtHoTen.getText();
            ngaySinhUv = txtNgaySinh.getText();
            gioiTinhUv = rdoNam.isSelected();
            sdtUv=txtSdt.getText();
            emailUv=txtEmail.getText();
            dcUv=txtDiaChi.getText();
            Object selectedItem = cboMaChucVu.getSelectedItem();
            if (selectedItem != null) {
                cvUv = selectedItem.toString(); // Lưu giá trị được chọn thành String
            } else {
                cvUv = ""; // Giá trị mặc định nếu không có gì được chọn
            }
            System.out.println(cvUv+maUngVien+tenUv+ngaySinhUv+gioiTinhUv+sdtUv+emailUv);
            EmployeeManagement them = new EmployeeManagement();
            JDesktopPane desktopPane = getDesktopPane();
            desktopPane.add(them);//add f1 to desktop pane
            them.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(CandidateManagement.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblMaNV = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        lblSdt = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        txtMaUngVien = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtSdt = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        cboMaChucVu = new javax.swing.JComboBox<>();
        lblDiaChi1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        txtHoTen = new javax.swing.JTextField();
        txtNgaySinh = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnThemNhanVien = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cboFind = new javax.swing.JComboBox<>();
        txtFind = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUngVien = new javax.swing.JTable();

        setBackground(new java.awt.Color(235, 223, 235));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(235, 223, 235));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("QUẢN LÝ ỨNG VIÊN");

        tabs.setBackground(new java.awt.Color(112, 194, 180));
        tabs.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(112, 194, 180));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel4.setBackground(new java.awt.Color(112, 194, 180));

        lblMaNV.setBackground(new java.awt.Color(255, 255, 255));
        lblMaNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMaNV.setText("Mã ứng viên");

        lblMatKhau.setBackground(new java.awt.Color(255, 255, 255));
        lblMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMatKhau.setText("Họ và tên");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Giới tính");

        lblNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNgaySinh.setText("Ngày sinh");

        lblSdt.setBackground(new java.awt.Color(255, 255, 255));
        lblSdt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSdt.setText("Số điện thoại");

        lblEmail.setBackground(new java.awt.Color(255, 255, 255));
        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEmail.setText("Email");

        lblDiaChi.setBackground(new java.awt.Color(255, 255, 255));
        lblDiaChi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDiaChi.setText("Địa chỉ");

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");
        rdoNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoNam)
                .addGap(18, 18, 18)
                .addComponent(rdoNu)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(112, 194, 180));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThem.setBackground(new java.awt.Color(255, 255, 204));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/image-gallery.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorderPainted(false);
        btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThem.setOpaque(true);
        btnThem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 255, 204));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/xoa.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.setBorderPainted(false);
        btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoa.setOpaque(true);
        btnXoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 255, 204));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/edit-text.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorderPainted(false);
        btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSua.setOpaque(true);
        btnSua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 255, 204));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/circle-of-two-clockwise-arrows-rotation.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.setBorderPainted(false);
        btnReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReset.setOpaque(true);
        btnReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(112, 194, 180));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnFirst.setBackground(new java.awt.Color(255, 154, 0));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.setBorderPainted(false);
        btnFirst.setOpaque(true);
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(255, 154, 0));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.setBorderPainted(false);
        btnPrev.setOpaque(true);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(255, 154, 0));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnNext.setText(">>");
        btnNext.setBorderPainted(false);
        btnNext.setOpaque(true);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(255, 154, 0));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLast.setText(">|");
        btnLast.setBorderPainted(false);
        btnLast.setOpaque(true);
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cboMaChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDiaChi1.setBackground(new java.awt.Color(255, 255, 255));
        lblDiaChi1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDiaChi1.setText("Tên chức vụ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mô tả");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

        btnThemNhanVien.setBackground(new java.awt.Color(198, 136, 235));
        btnThemNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThemNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNhanVien.setText("THÊM VÀO DANH SÁCH NHÂN VIÊN");
        btnThemNhanVien.setBorderPainted(false);
        btnThemNhanVien.setOpaque(true);
        btnThemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(lblMatKhau)
                            .addComponent(lblMaNV)
                            .addComponent(lblSdt)
                            .addComponent(lblDiaChi)
                            .addComponent(lblDiaChi1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaUngVien)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtSdt, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblNgaySinh)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblEmail)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEmail))))
                            .addComponent(txtDiaChi)
                            .addComponent(txtHoTen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(cboMaChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(btnThemNhanVien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaNV)
                            .addComponent(txtMaUngVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMatKhau)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(lblNgaySinh)
                            .addComponent(txtNgaySinh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSdt)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDiaChi)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDiaChi1)
                            .addComponent(cboMaChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThemNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabs.addTab("THÔNG TIN ỨNG VIÊN", jPanel2);

        jPanel3.setBackground(new java.awt.Color(112, 194, 180));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBackground(new java.awt.Color(112, 194, 180));

        cboFind.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MÃ ỨNG VIÊN", "HỌ TÊN", "MÃ CHỨC VỤ" }));

        btnFind.setBackground(new java.awt.Color(255, 255, 204));
        btnFind.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/icons8-search-32.png"))); // NOI18N
        btnFind.setText("Tìm kiếm");
        btnFind.setBorderPainted(false);
        btnFind.setOpaque(true);
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        tblUngVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã ứng viên", "Họ và tên", "Giới tính", "Ngày sinh", "Số điện thoại", "Email", "Địa chỉ", "Mã chức vụ", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUngVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUngVienMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblUngVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblUngVien);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cboFind, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFind)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabs.addTab("DANH SÁCH ỨNG VIÊN", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNamActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblUngVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUngVienMousePressed
        
    }//GEN-LAST:event_tblUngVienMousePressed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        try {
            insert();
        } catch (SQLException ex) {
            Logger.getLogger(CandidateManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnThemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanVienActionPerformed
        themNhanVien();
    }//GEN-LAST:event_btnThemNhanVienActionPerformed

    private void tblUngVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUngVienMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            this.row=tblUngVien.getSelectedRow();            
            this.edit();
        }
    }//GEN-LAST:event_tblUngVienMouseClicked

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        // TODO add your handling code here:
        timKiem();
    }//GEN-LAST:event_btnFindActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemNhanVien;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboFind;
    private javax.swing.JComboBox<String> cboMaChucVu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblDiaChi1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblSdt;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblUngVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaUngVien;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSdt;
    // End of variables declaration//GEN-END:variables
}
