/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.util.ArrayList;
import java.util.List;

public class ProductForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton saveButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    
    List<Product> products = new ArrayList<>(); 
    private Mavenproject3 gui;
    

    public ProductForm(Mavenproject3 gui, List<Product> products) {  
        this.gui = gui;
        this.products = products;
        
        setTitle("WK. Cuan | Stok Barang");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel form pemesanan
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.add(new JLabel("Kode Barang"));
        codeField = new JTextField(3);
        formPanel.add(codeField);
        
        formPanel.add(new JLabel("Nama Barang:"));
        nameField = new JTextField(6);
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Kategori:"));
        categoryField = new JComboBox<>(new String[]{"Coffee", "Dairy", "Juice", "Soda", "Tea"});
        formPanel.add(categoryField);
        
        formPanel.add(new JLabel("Harga Jual:"));
        priceField = new JTextField(5);
        formPanel.add(priceField);
        
        formPanel.add(new JLabel("Stok Tersedia:"));
        stockField = new JTextField(2);
        formPanel.add(stockField);
        
        saveButton = new JButton("Simpan");
        formPanel.add(saveButton);
        addButton = new JButton("Tambah");
        formPanel.add(addButton);
        editButton = new JButton("Edit");
        formPanel.add(editButton);
        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton);
        
        add(formPanel, BorderLayout.EAST);
        
        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "Kategori", "Harga Jual", "Stok"}, 0);
        drinkTable = new JTable(tableModel);
        loadProductData(products);
        add(new JScrollPane(drinkTable), BorderLayout.CENTER);
        setVisible(true);  
        
        
 drinkTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedCode = tableModel.getValueAt(selectedRow, 0).toString();
                String selectedPrice = tableModel.getValueAt(selectedRow, 3).toString(); // Assuming price is in column 3
                codeField.setText(selectedCode);
                priceField.setText(selectedPrice);
            }
        }
    });

 saveButton.addActionListener(e -> {
     try {
         gui.updateText();
     }
     catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "eror!", "Error", JOptionPane.ERROR_MESSAGE);
     }
 });
         
  addButton.addActionListener(e -> {
    try {
        String code = codeField.getText();
        String name = nameField.getText();
        String category = categoryField.getSelectedItem().toString();
        double price = Double.parseDouble(priceField.getText());
        int stock = Integer.parseInt(stockField.getText());

        int id = products.size() + 1;
        Product product = new Product(id, code, name, category, price, stock);  // Assuming "Coffee" category and stock 10
        
        products.add(product);
        tableModel.addRow(new Object[]{product.getCode(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()});

        nameField.setText("");
        priceField.setText("");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Masukkan harga dalam angka!", "Error", JOptionPane.ERROR_MESSAGE);
    }
});
    }

    private void loadProductData(List<Product> productList) {
        for (Product product : productList) {
            tableModel.addRow(new Object[]{
                product.getCode(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()
            });
    }
}
    
}