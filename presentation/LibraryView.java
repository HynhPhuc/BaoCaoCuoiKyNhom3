package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import model.LibrarySachGiaoKhoa;
import model.LibrarySachThamKhao;
import model.LibraryServiceImpl;
import observer.Subscriber;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class LibraryView extends JFrame implements Subscriber {
    private JPanel panel;
    private DefaultTableModel tableModel1, tableModel2;
    private JTable table1, table2;
    private JMenuBar menuBar;
    private JComboBox<String> comboTinhTrang;
    private SpinnerDateModel dateModel;
    private JSpinner date;
    private JLabel lbMaSach, lbNgayNhap, lbDonGia, lbSoLuong, lbNhaXuatBan, lbTinhTrang, lbThue;
    private JTextField txtMaSach, txtDonGia, txtSoLuong, txtNhaXuatBan, txtThue;
    private LibraryController controller;

    // khai báo và khởi tạo đối tượng tham chiếu
    private LibraryServiceImpl libraryServiceImpl = new LibraryServiceImpl();

    // xử lý ngoại lệ xảy ra khi có vấn đề liên quan đến cơ sở dữ liệu
    public LibraryView(LibraryController controller) throws SQLException {
        this.controller = controller;
        libraryServiceImpl = new LibraryServiceImpl();
        libraryServiceImpl.subscribe(this);// Nhận thông báo

        buildMenu();
        buildPanel();
        setJMenuBar(menuBar);

        setExtendedState(MAXIMIZED_BOTH);
        setTitle("Phần Mềm Thư Viện Sách");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(panel);

    }

    private void buildMenu() {
        menuBar = new JMenuBar();
        JMenu ThemMenu = new JMenu("Thêm");
        menuBar.add(ThemMenu);
        JMenuItem addSachGiaoKhoa = new JMenuItem("Thêm Sách Giáo Khoa");
        JMenuItem addSachThamKhao = new JMenuItem("Thêm Sách Tham Khảo");
        ThemMenu.add(addSachGiaoKhoa);
        addSachGiaoKhoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSachGiaoKhoa();
            }
        });
        ThemMenu.add(addSachThamKhao);
        addSachThamKhao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSachThamKhao();
            }
        });
        JMenu XoaMenu = new JMenu("Xóa");
        menuBar.add(XoaMenu);
        JMenuItem removeSachGiaoKhoa = new JMenuItem("Xóa Sách Giáo Khoa");
        JMenuItem removeSachThamKhao = new JMenuItem("Xóa Sách Tham Khảo");
        XoaMenu.add(removeSachGiaoKhoa);
        removeSachGiaoKhoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSachGiaoKhoa();
            }
        });
        XoaMenu.add(removeSachThamKhao);
        removeSachThamKhao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSachThamKhao();
            }
        });
        JMenu SuaMenu = new JMenu("Sửa");
        menuBar.add(SuaMenu);
        JMenuItem updateSachGiaoKhoa = new JMenuItem("Sửa Sách Giáo Khoa");
        JMenuItem updateSachThamKhao = new JMenuItem("Sửa Sách Tham Khảo");
        SuaMenu.add(updateSachGiaoKhoa);
        updateSachGiaoKhoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSachGiaoKhoa();
            }
        });
        SuaMenu.add(updateSachThamKhao);
        updateSachThamKhao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSachThamKhao();
            }
        });
        JMenu InMenu = new JMenu("In");
        menuBar.add(InMenu);
        JMenuItem printSachGiaoKhoa = new JMenuItem("In Excel Sách Giáo Khoa");
        JMenuItem printSachThamKhao = new JMenuItem("In Excel Sách Tham Khảo");
        InMenu.add(printSachGiaoKhoa);
        printSachGiaoKhoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportGiaoKhoaToExcel();
            }
        });
        InMenu.add(printSachThamKhao);
        printSachThamKhao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportThamKhaoToExcel();
            }
        });
        JMenu TimKiemMenu = new JMenu("Tìm Kiếm");
        menuBar.add(TimKiemMenu);
        JMenuItem searchSachGiaoKhoa = new JMenuItem("Tìm Kiếm Mã Sách");
        JMenuItem searchSachThamKhao = new JMenuItem("In Ra Tất Cả Sách Hiện Có");
        TimKiemMenu.add(searchSachGiaoKhoa);
        searchSachGiaoKhoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timSachTheoMa();
            }
        });
        TimKiemMenu.add(searchSachThamKhao);
        searchSachThamKhao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshThuVienTable();
            }
        });
        JMenu trungBinhCong = new JMenu("TrungBinhDonGia");
        menuBar.add(trungBinhCong);
        JMenuItem trungBinhDonGiaSGK = new JMenuItem("TBDonGiaSGK");
        JMenuItem trungBinhDonGiaSTK = new JMenuItem("TBDonGiaSTK");
        trungBinhCong.add(trungBinhDonGiaSGK);
        trungBinhDonGiaSGK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateAveragePriceSGK();
            }
        });
        trungBinhCong.add(trungBinhDonGiaSTK);
        trungBinhDonGiaSTK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateAveragePriceSTK();
            }
        });
        JMenu XuatRaDSNhaXuatBan = new JMenu("Xuất ra Danh Sách Nhà Xuất Bản ");
        menuBar.add(XuatRaDSNhaXuatBan);
        JMenuItem NXBSach = new JMenuItem("Nhà Xuất Bản Sách Có Tên Là ");
        JMenuItem NXBReset = new JMenuItem("Hiện Tất Cả Nhà Xuất Bản ");
        XuatRaDSNhaXuatBan.add(NXBSach);
        XuatRaDSNhaXuatBan.add(NXBReset);
        NXBSach.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timNXBSachTheoNhaXuatBan();
            }
        });
        NXBReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshThuVienTable();
            }
        });
        JMenu exit = new JMenu("Exit");
        menuBar.add(exit);
        JMenuItem thoat = new JMenuItem("Thoát");
        exit.add(thoat);
        thoat.addActionListener((e -> System.exit(0)));

    }

    private void buildPanel() {
        panel = new JPanel();
        dateModel = new SpinnerDateModel();
        date = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(date, "dd-MM-yyyy");
        date.setEditor(editor);

        String[] tinhTrangOptions = { "Mới", "Cũ" };
        comboTinhTrang = new JComboBox<>(tinhTrangOptions);

        lbMaSach = new JLabel("Mã Sách:");
        panel.add(lbMaSach);
        txtMaSach = new JTextField(5);
        panel.add(txtMaSach);
        lbNgayNhap = new JLabel("Ngày Nhập:");
        panel.add(lbNgayNhap);
        panel.add(date);
        lbDonGia = new JLabel("Đơn Giá:");
        panel.add(lbDonGia);
        txtDonGia = new JTextField(5);
        panel.add(txtDonGia);
        lbSoLuong = new JLabel("Số Lượng:");
        panel.add(lbSoLuong);
        txtSoLuong = new JTextField(5);
        panel.add(txtSoLuong);
        lbNhaXuatBan = new JLabel("Nhà Xuất Bản:");
        panel.add(lbNhaXuatBan);
        txtNhaXuatBan = new JTextField(5);
        panel.add(txtNhaXuatBan);
        lbTinhTrang = new JLabel("Tình Trạng:");
        panel.add(lbTinhTrang);
        panel.add(comboTinhTrang);
        lbThue = new JLabel("Thuế:");
        panel.add(lbThue);
        txtThue = new JTextField(5);
        panel.add(txtThue);

        // layout setup
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(lbMaSach)
                .addComponent(lbNgayNhap)
                .addComponent(lbDonGia).addComponent(lbSoLuong).addComponent(lbNhaXuatBan)
                .addComponent(lbTinhTrang)
                .addComponent(lbThue));
        hGroup.addGroup(layout.createParallelGroup().addComponent(txtMaSach)
                .addComponent(date)
                .addComponent(txtDonGia).addComponent(txtSoLuong).addComponent(txtNhaXuatBan)
                .addComponent(comboTinhTrang)
                .addComponent(txtThue));
        layout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbMaSach)
                .addComponent(txtMaSach));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbNgayNhap)
                .addComponent(date));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbDonGia)
                .addComponent(txtDonGia));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbSoLuong)
                .addComponent(txtSoLuong));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbNhaXuatBan)
                .addComponent(txtNhaXuatBan));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbTinhTrang)
                .addComponent(comboTinhTrang));
        vGroup.addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbThue)
                        .addComponent(txtThue));

        layout.setVerticalGroup(vGroup);

        SwingUtilities.invokeLater(() -> {

            // Tạo model cho JTable đầu tiên (SachGiaoKhoa)
            tableModel1 = new DefaultTableModel();
            table1 = new JTable(tableModel1);

            // Tạo model cho JTable thứ hai (SachThamKhao)
            tableModel2 = new DefaultTableModel();
            table2 = new JTable(tableModel2);

            // Thêm các cột vào tableModel đầu tiên
            tableModel1.addColumn("Mã Sách");
            tableModel1.addColumn("Ngày Nhập");
            tableModel1.addColumn("Đơn Giá");
            tableModel1.addColumn("Số Lượng");
            tableModel1.addColumn("Nhà Xuất Bản");
            tableModel1.addColumn("Tình Trạng");
            tableModel1.addColumn("Thành Tiền");

            // Thêm các cột vào tableModel thứ hai
            tableModel2.addColumn("Mã Sách");
            tableModel2.addColumn("Ngày Nhập");
            tableModel2.addColumn("Đơn Giá");
            tableModel2.addColumn("Số Lượng");
            tableModel2.addColumn("Nhà Xuất Bản");
            tableModel2.addColumn("Thuế");
            tableModel2.addColumn("Thành Tiền");

            // Kết nối đến cơ sở dữ liệu và lấy dữ liệu từ bảng SachGiaoKhoa
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:sqlserver://hynhphuc:1433;databaseName=ThuVienSach;encrypt=true;trustServerCertificate=true;trustStore=\"C:\\Users\\ASUS\\truststore.jks\";trustStorePassword=070999;",
                    "sa", "070999");
                    Statement statement = connection.createStatement()) {

                // Truy vấn dữ liệu từ bảng SachGiaoKhoa
                String sql1 = "SELECT * FROM SachGiaoKhoa";
                try (ResultSet resultSet = statement.executeQuery(sql1)) {
                    while (resultSet.next()) {
                        String maSach = resultSet.getString("MaSach");
                        String ngayNhap = resultSet.getString("NgayNhap");
                        Double donGia = resultSet.getDouble("DonGia");
                        int soLuong = resultSet.getInt("SoLuong");
                        String nhaXuatBan = resultSet.getString("NhaXuatBan");
                        String tinhTrang = resultSet.getString("TinhTrang");
                        double thanhTien = 0;
                        if (tinhTrang.equals("Mới") || tinhTrang.equals("mới")) {
                            thanhTien = (soLuong * donGia);
                        } else if (tinhTrang.equals("Cũ") || tinhTrang.equals("cũ")) {
                            thanhTien = (soLuong * donGia) * 0.5;
                        }
                        tableModel1
                                .addRow(new Object[] { maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang,
                                        thanhTien });
                    }
                }

                // Truy vấn dữ liệu từ bảng SachThamKhao
                String sql2 = "SELECT * FROM SachThamKhao";
                try (ResultSet resultSet = statement.executeQuery(sql2)) {
                    while (resultSet.next()) {
                        String maSach = resultSet.getString("MaSach");
                        String ngayNhap = resultSet.getString("NgayNhap");
                        Double donGia = resultSet.getDouble("DonGia");
                        int soLuong = resultSet.getInt("SoLuong");
                        String nhaXuatBan = resultSet.getString("NhaXuatBan");
                        double thue = resultSet.getDouble("Thue");
                        double thanhTien = (soLuong * donGia) + thue;

                        tableModel2
                                .addRow(new Object[] { maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue,
                                        thanhTien });
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Đưa JTable vào JScrollPane và thêm vào JFrame
            JScrollPane tabScrollPane1 = new JScrollPane(table1);
            tabScrollPane1.setBounds(20, 220, 1500, 260);
            JLabel jLabel1 = new JLabel("Sách Giáo Khoa: ");
            jLabel1.setBounds(35, 200, 200, 20);
            panel.add(jLabel1);
            panel.add(tabScrollPane1);

            JScrollPane tabScrollPane2 = new JScrollPane(table2);
            tabScrollPane2.setBounds(20, 497, 1500, 270);
            JLabel jLabel2 = new JLabel("Sách Tham Khảo: ");
            jLabel2.setBounds(35, 477, 200, 20);
            panel.add(jLabel2);
            panel.add(tabScrollPane2);

        });

    }

    private void addSachGiaoKhoa() {
        try {
            String maSach = String.valueOf(txtMaSach.getText());
            Date ngayNhap = (Date) dateModel.getValue();
            double donGia = Double.parseDouble(txtDonGia.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            String nhaXuatBan = txtNhaXuatBan.getText();
            String tinhTrang = (String) comboTinhTrang.getSelectedItem();
            double thue = Double.parseDouble(txtThue.getText());

            LibrarySachGiaoKhoa ttThuVien = new LibrarySachGiaoKhoa(maSach, ngayNhap, donGia,
                    soLuong, nhaXuatBan,
                    tinhTrang);
            libraryServiceImpl.addSachGK(ttThuVien);

            txtMaSach.setText("");
            date.setValue(new Date(0));
            txtDonGia.setText("");
            txtSoLuong.setText("");
            txtNhaXuatBan.setText("");
            comboTinhTrang.setSelectedIndex(0);
            txtThue.setText("");

            refreshThuVienTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
        }
    }

    private void addSachThamKhao() {
        try {
            String maSach = String.valueOf(txtMaSach.getText());
            Date ngayNhap = (Date) dateModel.getValue();
            double donGia = Double.parseDouble(txtDonGia.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            String nhaXuatBan = txtNhaXuatBan.getText();
            String tinhTrang = (String) comboTinhTrang.getSelectedItem();
            double thue = Double.parseDouble(txtThue.getText());

            LibrarySachThamKhao ttThuVien = new LibrarySachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan,
                    thue);
            libraryServiceImpl.addSachTK(ttThuVien);

            txtMaSach.setText("");
            date.setValue(new Date(0));
            txtDonGia.setText("");
            txtSoLuong.setText("");
            txtNhaXuatBan.setText("");
            comboTinhTrang.setSelectedIndex(0);
            txtThue.setText("");

            refreshThuVienTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
        }
    }

    private void removeSachGiaoKhoa() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            String maSach = (String) table1.getValueAt(selectedRow, 0);
            libraryServiceImpl.deleteSachGK(maSach);
            refreshThuVienTable(); // Refresh the tables
            txtMaSach.setText("");
            date.setValue(new Date());
            txtDonGia.setText("");
            txtSoLuong.setText("");
            txtNhaXuatBan.setText("");
            comboTinhTrang.setSelectedIndex(0);
            txtThue.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to remove.");
        }
    }

    private void removeSachThamKhao() {
        int selectedRow = table2.getSelectedRow();
        if (selectedRow != -1) {
            String maSach = (String) table2.getValueAt(selectedRow, 0);
            libraryServiceImpl.deleteSachTK(maSach);
            refreshThuVienTable(); // Refresh the tables
            txtMaSach.setText("");
            date.setValue(new Date());
            txtDonGia.setText("");
            txtSoLuong.setText("");
            txtNhaXuatBan.setText("");
            comboTinhTrang.setSelectedIndex(0);
            txtThue.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to remove.");
        }
    }

    private void updateSachGiaoKhoa() {
        try {
            String maSach = txtMaSach.getText();
            Date ngayNhap = (Date) dateModel.getValue();
            double donGia = Double.parseDouble(txtDonGia.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            String nhaXuatBan = txtNhaXuatBan.getText();
            String tinhTrang = (String) comboTinhTrang.getSelectedItem();
            // double thue = Double.parseDouble(txtThue.getText());

            // Create SQL update query
            String sql = "UPDATE SachGiaoKhoa SET NgayNhap = ?, DonGia = ?, SoLuong = ?, NhaXuatBan = ?, TinhTrang = ? WHERE MaSach = ?";

            // Update the database
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:sqlserver://hynhphuc:1433;databaseName=ThuVienSach;encrypt=true;trustServerCertificate=true;trustStore=\"C:\\Users\\ASUS\\truststore.jks\";trustStorePassword=070999;",
                    "sa", "070999");
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setDate(1, new java.sql.Date(ngayNhap.getTime()));
                preparedStatement.setDouble(2, donGia);
                preparedStatement.setInt(3, soLuong);
                preparedStatement.setString(4, nhaXuatBan);
                preparedStatement.setString(5, tinhTrang);
                preparedStatement.setString(6, maSach);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Sách tham khảo đã được cập nhật thành công.");
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy sách tham khảo với mã: "
                            + maSach);
                }

                // Clear input fields
                txtMaSach.setText("");
                date.setValue(new Date());
                txtDonGia.setText("");
                txtSoLuong.setText("");
                txtNhaXuatBan.setText("");
                comboTinhTrang.setSelectedIndex(0);
                txtThue.setText("");

                // Refresh the table
                refreshThuVienTable();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sách tham khảo: " +
                    e.getMessage());
        }
    }

    private void updateSachThamKhao() {
        try {
            String maSach = txtMaSach.getText();
            Date ngayNhap = (Date) dateModel.getValue();
            double donGia = Double.parseDouble(txtDonGia.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            String nhaXuatBan = txtNhaXuatBan.getText();
            // String tinhTrang = (String) comboTinhTrang.getSelectedItem();
            double thue = Double.parseDouble(txtThue.getText());

            // Create SQL update query
            String sql = "UPDATE SachThamKhao SET NgayNhap = ?, DonGia = ?, SoLuong = ?, NhaXuatBan = ?, Thue = ? WHERE MaSach = ?";

            // Update the database
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:sqlserver://hynhphuc:1433;databaseName=ThuVienSach;encrypt=true;trustServerCertificate=true;trustStore=\"C:\\Users\\ASUS\\truststore.jks\";trustStorePassword=070999;",
                    "sa", "070999");
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setDate(1, new java.sql.Date(ngayNhap.getTime()));
                preparedStatement.setDouble(2, donGia);
                preparedStatement.setInt(3, soLuong);
                preparedStatement.setString(4, nhaXuatBan);
                preparedStatement.setDouble(5, thue);
                preparedStatement.setString(6, maSach);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Sách tham khảo đã được cập nhật thành công.");
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy sách tham khảo với mã: "
                            + maSach);
                }

                // Clear input fields
                txtMaSach.setText("");
                date.setValue(new Date());
                txtDonGia.setText("");
                txtSoLuong.setText("");
                txtNhaXuatBan.setText("");
                comboTinhTrang.setSelectedIndex(0);
                txtThue.setText("");

                // Refresh the table
                refreshThuVienTable();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sách tham khảo: " +
                    e.getMessage());
        }

    }

    private void exportGiaoKhoaToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Thêm đuôi .xlsx nếu filePath chưa có
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            exportTableToExcel(table1, filePath);
        }
    }

    private void exportThamKhaoToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Thêm đuôi .xlsx nếu filePath chưa có
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            exportTableToExcel(table2, filePath);
        }
    }

    public static void exportTableToExcel(JTable table, String filePath) {
        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                JOptionPane.showMessageDialog(null, "Không thể tạo thư mục: " + parentDir.getAbsolutePath());
                return;
            }
        }

        // Thêm đuôi .xlsx nếu filePath chưa có
        if (!filePath.toLowerCase().endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            TableModel model = table.getModel();

            // Tạo header cho file Excel
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
            }

            // Xuất dữ liệu từ JTable vào file Excel
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    Object value = model.getValueAt(i, j);

                    if (value != null) {
                        // Kiểm tra kiểu dữ liệu và ghi giá trị tương ứng vào cell
                        if (value instanceof Double) {
                            cell.setCellValue((Double) value);
                        } else if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        } else {
                            cell.setCellValue(value.toString());
                        }
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            // Lưu file Excel
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
                JOptionPane.showMessageDialog(null, "Xuất dữ liệu thành công: " + filePath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi ghi dữ liệu vào file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo workbook Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void timSachTheoMa() {
        String maSach = txtMaSach.getText();
        LibrarySachGiaoKhoa sachGiaoKhoa = controller.getServiceImpl().timSachGiaoKhoaTheoMa(maSach);
        LibrarySachThamKhao sachThamKhao = controller.getServiceImpl().timSachThamKhaoTheoMa(maSach);

        tableModel1.setRowCount(0);
        tableModel2.setRowCount(0);

        if (sachGiaoKhoa != null) {
            tableModel1.addRow(new Object[] {
                    sachGiaoKhoa.getMaSach(),
                    sachGiaoKhoa.getNgayNhap(),
                    sachGiaoKhoa.getDonGia(),
                    sachGiaoKhoa.getSoLuong(),
                    sachGiaoKhoa.getNhaXuatBan(),
                    sachGiaoKhoa.getTinhTrang(),
                    sachGiaoKhoa.tinhThanhTien()
            });
        }

        if (sachThamKhao != null) {
            tableModel2.addRow(new Object[] {
                    sachThamKhao.getMaSach(),
                    sachThamKhao.getNgayNhap(),
                    sachThamKhao.getDonGia(),
                    sachThamKhao.getSoLuong(),
                    sachThamKhao.getNhaXuatBan(),
                    sachThamKhao.getThue(),
                    sachThamKhao.tinhThanhTien()
            });
        }
        txtMaSach.setText("");
    }

    private void timNXBSachTheoNhaXuatBan() {
        String nhaXuatBan = txtNhaXuatBan.getText();
        LibrarySachGiaoKhoa sachGiaoKhoa = controller.getServiceImpl().xuatRaGiaoKhoaTheoNXB(nhaXuatBan);
        LibrarySachThamKhao sachThamKhao = controller.getServiceImpl().xuatRaThamKhaoTheoNXB(nhaXuatBan);

        tableModel1.setRowCount(0);
        tableModel2.setRowCount(0);

        if (sachGiaoKhoa != null) {
            tableModel1.addRow(new Object[] {
                    sachGiaoKhoa.getMaSach(),
                    sachGiaoKhoa.getNgayNhap(),
                    sachGiaoKhoa.getDonGia(),
                    sachGiaoKhoa.getSoLuong(),
                    sachGiaoKhoa.getNhaXuatBan(),
                    sachGiaoKhoa.getTinhTrang(),
                    sachGiaoKhoa.tinhThanhTien()
            });
        }

        if (sachThamKhao != null) {
            tableModel2.addRow(new Object[] {
                    sachThamKhao.getMaSach(),
                    sachThamKhao.getNgayNhap(),
                    sachThamKhao.getDonGia(),
                    sachThamKhao.getSoLuong(),
                    sachThamKhao.getNhaXuatBan(),
                    sachThamKhao.getThue(),
                    sachThamKhao.tinhThanhTien()
            });
        }
        txtNhaXuatBan.setText("");
    }

    private void calculateAveragePriceSGK() {
        double avgPrice = libraryServiceImpl.tinhTrungBinhDonGiaSachGiaoKhoa();
        JOptionPane.showMessageDialog(this,
                "Trung Bình Đơn Giá Sách Giáo Khoa: " + avgPrice,
                "Thông Báo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void calculateAveragePriceSTK() {
        double avgPrice = libraryServiceImpl.tinhTrungBinhDonGiaSachThamKhao();
        JOptionPane.showMessageDialog(this,
                "Trung Bình Đơn Giá Sách Tham Khảo: " + avgPrice,
                "Thông Báo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshThuVienTable() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:sqlserver://hynhphuc:1433;databaseName=ThuVienSach;encrypt=true;trustServerCertificate=true;trustStore=\"C:\\Users\\ASUS\\truststore.jks\";trustStorePassword=070999;",
                "sa", "070999");
                Statement statement = connection.createStatement()) {

            // Clear existing data
            tableModel1.setRowCount(0);
            tableModel2.setRowCount(0);

            // Query and update SachGiaoKhoa table
            String sql1 = "SELECT * FROM SachGiaoKhoa";
            try (ResultSet resultSet = statement.executeQuery(sql1)) {
                while (resultSet.next()) {
                    String maSach = resultSet.getString("MaSach");
                    String ngayNhap = resultSet.getString("NgayNhap");
                    Double donGia = resultSet.getDouble("DonGia");
                    int soLuong = resultSet.getInt("SoLuong");
                    String nhaXuatBan = resultSet.getString("NhaXuatBan");
                    String tinhTrang = resultSet.getString("TinhTrang");
                    double thanhTien = tinhTrang.equalsIgnoreCase("Mới") ? (soLuong * donGia)
                            : (soLuong * donGia) * 0.5;
                    tableModel1.addRow(
                            new Object[] { maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang,
                                    thanhTien });
                }
            }

            // Query and update SachThamKhao table
            String sql2 = "SELECT * FROM SachThamKhao";
            try (ResultSet resultSet = statement.executeQuery(sql2)) {
                while (resultSet.next()) {
                    String maSach = resultSet.getString("MaSach");
                    String ngayNhap = resultSet.getString("NgayNhap");
                    Double donGia = resultSet.getDouble("DonGia");
                    int soLuong = resultSet.getInt("SoLuong");
                    String nhaXuatBan = resultSet.getString("NhaXuatBan");
                    double thue = resultSet.getDouble("Thue");
                    double thanhTien = (soLuong * donGia) + thue;
                    tableModel2.addRow(new Object[] { maSach, ngayNhap, donGia, soLuong,
                            nhaXuatBan, thue, thanhTien });
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error refreshing table data: " +
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateTableData() {
        List<LibrarySachGiaoKhoa> sachGiaoKhoaList = controller.getAllSachGiaoKhoa();
        List<LibrarySachThamKhao> sachThamKhaoList = controller.getAllSachThamKhao();

        tableModel1.setRowCount(0);
        for (LibrarySachGiaoKhoa sach : sachGiaoKhoaList) {
            tableModel1.addRow(new Object[] {
                    sach.getMaSach(),
                    sach.getNgayNhap(),
                    sach.getDonGia(),
                    sach.getSoLuong(),
                    sach.getNhaXuatBan(),
                    sach.getTinhTrang(),
                    sach.tinhThanhTien()
            });
        }

        tableModel2.setRowCount(0);
        for (LibrarySachThamKhao sach : sachThamKhaoList) {
            tableModel2.addRow(new Object[] {
                    sach.getMaSach(),
                    sach.getNgayNhap(),
                    sach.getDonGia(),
                    sach.getSoLuong(),
                    sach.getNhaXuatBan(),
                    sach.getThue(),
                    sach.tinhThanhTien()
            });
        }
    }

    @Override
    public void update() {
        updateTableData();
    }

    public static void main(String[] args) {
        try {
            LibraryController controller = new LibraryController();
            LibraryView view = new LibraryView(controller);
            view.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
