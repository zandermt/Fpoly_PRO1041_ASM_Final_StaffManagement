/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package UI;

import CLASS.NhanVien;
import CLASS.PhongBan;
import DAO.NhanVienDAO;
import DAO.PhongBanDAO;
import UTILS.Msgbox;
import UTILS.XDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zander
 */
public class DepartmentManagement extends javax.swing.JInternalFrame {

    /**
     * Creates new form PositionManagement
     */
    public DepartmentManagement() {
        initComponents();
        setTitle("Quản lý phòng ban");
        this.fillTable();
        this.row=0;
        this.edit();
        this.updateStatus();
    }
    
    int row = -1;
    PhongBanDAO pbDAO = new PhongBanDAO();
    NhanVienDAO nvDAO = new NhanVienDAO();

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblPhongBan.getModel();
        model.setRowCount(0);
        try {
            List<CLASS.PhongBan> list = pbDAO.selectAll();
            for (CLASS.PhongBan pb : list) {
                Object[] row = {
                    pb.getMaPhongBan(),
                    pb.getTenPhongBan(),
                    pb.getMaTruongPhong(),
                    pb.getMoTa()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setForm(CLASS.PhongBan pb) {
        txtMaPhongBan.setText(pb.getMaPhongBan());
        txtTenPhongBan.setText(pb.getTenPhongBan());
        txtMaTruongPhong.setText(pb.getMaTruongPhong());
        txtMoTa.setText(pb.getMoTa());
    }

    public PhongBan getForm() {
   PhongBan pb = new PhongBan();
    
    if (txtTenPhongBan.getText().isEmpty()) {
        Msgbox.alert(this, "Tên phòng ban không được để trống!");
        return null;
    } else if (txtTenPhongBan.getText().length() > 100) {
        Msgbox.alert(this, "Tên phòng ban không được vượt quá 100 kí tự");
        return null;
    }
    pb.setTenPhongBan(txtTenPhongBan.getText());

    NhanVienDAO nvdao = new NhanVienDAO();
    NhanVien MaTruongPhong = nvdao.selectById(txtMaTruongPhong.getText());
    if (MaTruongPhong == null) {
        Msgbox.alert(this, "Mã trưởng phòng không tồn tại!");
        return null;
    } else if (txtMaTruongPhong.getText().length() > 5) {
        Msgbox.alert(this, "Mã trưởng phòng không được vượt quá 5 kí tự");
        return null;
    }
    List<PhongBan> list = pbDAO.selectAll();
    for (PhongBan phongBan : list) {
        if (phongBan.getMaTruongPhong().equals(pb.getMaTruongPhong())) {
            Msgbox.alert(this, "Mã trưởng phòng này đã thuộc phòng ban khác!");
            return null;
        }
    }
    pb.setMaTruongPhong(txtMaTruongPhong.getText());
    pb.setMoTa(txtMoTa.getText());
    return pb;
}

    
    void updateStatus() {
    boolean edit = this.row >= 0;
    boolean first = this.row == 0;
    boolean last = this.row == tblPhongBan.getRowCount() - 1;
    
    // Trang thái form
    txtMaPhongBan.setEditable(false);
    btnThem.setEnabled(!edit);
    btnSua.setEnabled(edit);
    btnXoa.setEnabled(edit);
    btnFirst.setEnabled(edit && !first);
    btnPrev.setEnabled(edit && !first);
    btnNext.setEnabled(edit && !last);
    btnLast.setEnabled(edit && !last);
}


    void edit() {
        String MaPhongBan = (String) tblPhongBan.getValueAt(this.row, 0);
        CLASS.PhongBan model = pbDAO.selectById(MaPhongBan);
        this.setForm(model);
        tabs.setSelectedIndex(0);
        this.updateStatus();

    }
    
     void clearForm() {
    txtMaPhongBan.setText(pbDAO.getNextMaPhongBan());
    txtTenPhongBan.setText("");
    txtMaTruongPhong.setText("");
    txtMoTa.setText("");

    this.row = -1;
    this.updateStatus();


    txtMaPhongBan.setEditable(false);
}

     
    void insert() {
    PhongBan pb = getForm();    
    if(pb==null){
        return;
    }
    pb.setMaPhongBan(pbDAO.getNextMaPhongBan());
    System.out.println(txtMaPhongBan.getText());
    System.out.println(pb.getMaPhongBan()+pb.getMaTruongPhong()+pb.getMoTa()+pb.getTenPhongBan());
    if(Msgbox.confirm(this,"Bạn muốn thêm phòng ban này?")){
        
    try {
        pbDAO.insert(pb);
        this.fillTable();
        this.clearForm(); // Xóa thông tin form sau khi thêm
        Msgbox.alert(this, "Thêm mới thành công!");
    } catch (Exception e) {
        Msgbox.alert(this, "Thêm mới thất bại!");
        e.printStackTrace();
    }}
}


    

    void update() {
        PhongBan pb = getForm();
        if(pb==null){
        return;
    }
        if(Msgbox.confirm(this,"Bạn muốn cập nhật phòng ban này?")){
        try {
            pbDAO.update(pb);
            this.fillTable();
            this.clearForm();
            Msgbox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            Msgbox.alert(this, "Cập nhật thất bại!");
            e.printStackTrace();
        }}
    }


    void delete() {
        if (Msgbox.confirm(this, "Bạn thực sự muốn xóa phòng ban này?")) {
            String MaNhanVien = txtMaPhongBan.getText();
            try {
                pbDAO.delete(MaNhanVien);
                this.fillTable();
               
                Msgbox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                Msgbox.alert(this, "Xóa thất bại!");
            }
        }
        if(row>=tblPhongBan.getRowCount()){
            row = tblPhongBan.getRowCount()-1;
            this.edit();
        }
        else{
            this.edit();
        }
    } 
    
    private void timKiem() {
        this.fillFind();
        this.clearForm();
        this.row = -1;
        updateStatus();
    }

    // Trong phương thức fillFind(), điều chỉnh xử lý khi chọn tìm kiếm theo mã ứng viên
    private void fillFind() {
        DefaultTableModel model = (DefaultTableModel) tblPhongBan.getModel();
        model.setRowCount(0);

        String keyword = txtFind.getText();
        try {
            List<PhongBan> ds = new ArrayList<>();
            switch (cboFind.getSelectedIndex()) {
                case 0:
                    PhongBan pb = pbDAO.selectById(keyword);
                    if (pb != null) {
                        ds.add(pb);
                    }
                    break;
                case 1:
                    ds = (List<PhongBan>) pbDAO.selectByTenPhongBan(keyword);
                    break;
                case 2:
                    ds = (List<PhongBan>) pbDAO.selectByMaTruongPhong(keyword);
                    break;
                
                default:
                    this.fillTable();
                    break;
            }

            for (PhongBan pbItem : ds) {
                Object[] row = {
                    pbItem.getMaPhongBan(),
                    pbItem.getTenPhongBan(),
                    pbItem.getMaTruongPhong(),
                    pbItem.getMoTa(),
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
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
        if (row < tblPhongBan.getRowCount() - 1) {
            row++;
            edit();
        }
    }

    void last() {
        row = tblPhongBan.getRowCount() - 1;
        edit();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaPhongBan = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtTenPhongBan = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtMaTruongPhong = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cboFind = new javax.swing.JComboBox<>();
        txtFind = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhongBan = new javax.swing.JTable();

        setBackground(new java.awt.Color(235, 223, 235));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(235, 223, 235));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("QUẢN LÝ PHÒNG BAN");

        tabs.setBackground(new java.awt.Color(112, 194, 180));
        tabs.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(112, 194, 180));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBackground(new java.awt.Color(112, 194, 180));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Mã phòng ban");

        jPanel6.setBackground(new java.awt.Color(112, 194, 180));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tên phòng ban");

        jPanel7.setBackground(new java.awt.Color(112, 194, 180));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Mã trưởng phòng");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Mô tả");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(288, 288, 288))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaPhongBan)
                            .addComponent(txtTenPhongBan)
                            .addComponent(txtMaTruongPhong)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaTruongPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        tabs.addTab("THÔNG TIN PHÒNG BAN", jPanel2);

        jPanel3.setBackground(new java.awt.Color(112, 194, 180));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel5.setBackground(new java.awt.Color(112, 194, 180));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cboFind.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã phòng ban", "Tên phòng ban", "Mã trưởng phòng" }));
        cboFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFindActionPerformed(evt);
            }
        });

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

        tblPhongBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phòng ban", "Tên phòng ban", "Tên trưởng phòng", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhongBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblPhongBanMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblPhongBan);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cboFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFind)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboFind)
                    .addComponent(txtFind)
                    .addComponent(btnFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
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

        tabs.addTab("DANH SÁCH PHÒNG BAN", jPanel3);

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

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        timKiem();
    }//GEN-LAST:event_btnFindActionPerformed

    private void tblPhongBanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongBanMousePressed
        if(evt.getClickCount()==2){
            this.row=tblPhongBan.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblPhongBanMousePressed

    private void cboFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFindActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblPhongBan;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtMaPhongBan;
    private javax.swing.JTextField txtMaTruongPhong;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenPhongBan;
    // End of variables declaration//GEN-END:variables
}
