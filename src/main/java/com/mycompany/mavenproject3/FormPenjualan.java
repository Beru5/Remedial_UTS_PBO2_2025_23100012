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
import java.awt.event.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FormPenjualan extends JFrame{
    private JTable tabelPenjualan;
    private DefaultTableModel tableModel;
    private JComboBox nameBox;
    private JTextField quantityField;
    private JTextField priceField;    
    private JTextField stockField;
    private JButton processButton;
    private JButton refreshButton;
    private Mavenproject3 gui;
    private ProductForm ProductForm;
    
    public FormPenjualan (Mavenproject3 gui, ProductForm ProductForm) {  
        this.gui = gui;
        this.ProductForm = ProductForm;
        
        setTitle("WK. Cuan | Jual Barang");
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        String[] productNames = new String[ProductManager.products.size()];
        for (int i = 0; i < ProductManager.products.size(); i++) {
        productNames[i] = ProductManager.products.get(i).getName();
        }
        
        formPanel.add(new JLabel("Kategori:"));
        nameBox = new JComboBox<>(productNames);
        formPanel.add(nameBox);
        
        formPanel.add(new JLabel("Stok Produk:"));
        stockField = new JTextField(5);
        stockField.setEditable(false);
        formPanel.add(stockField);
        
        formPanel.add(new JLabel("Harga Jual:"));
        priceField = new JTextField(5);
        priceField.setEditable(false);
        formPanel.add(priceField);
        
        formPanel.add(new JLabel("Kuantitas:"));
        quantityField = new JTextField(5);
        formPanel.add(quantityField);
        
        processButton = new JButton("Process");
        formPanel.add(processButton);
        refreshButton = new JButton("Refresh");
        formPanel.add(refreshButton);
        
        add(formPanel, BorderLayout.CENTER);
        
        
        
        nameBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateFields();
            }
        });

        updateFields();
    
        
        processButton.addActionListener(e -> {
            try {
                int selectedIndex = nameBox.getSelectedIndex();
                Product selectedProduct = ProductManager.products.get(selectedIndex);
                int quantity = Integer.parseInt(quantityField.getText());

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Kuantitas harus lebih dari 0");
                    return;
                }

                if (quantity > selectedProduct.getStock()) {
                    JOptionPane.showMessageDialog(this, "Stok tidak cukup!");
                    return;
                }

                selectedProduct.setStock(selectedProduct.updatedStock(quantity));
                JOptionPane.showMessageDialog(this, "Berhasil dijual: " + quantity + " unit dari " + selectedProduct.getName());
                updateFields();
                ProductForm.loadProductData();
                quantityField.setText("");
            }
            catch (Exception ex) {
               JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
               ex.printStackTrace();}
        });
        
        refreshButton.addActionListener(e -> {
            refreshComboBox();
        });
    }
    
public void updateFields() {
    int selectedIndex = nameBox.getSelectedIndex();
    if (selectedIndex < 0 || selectedIndex >= ProductManager.products.size()) {
        stockField.setText("");
        priceField.setText("");
        return;
    }
    Product selectedProduct = ProductManager.products.get(selectedIndex);
    stockField.setText(String.valueOf(selectedProduct.getStock()));
    priceField.setText(String.valueOf(selectedProduct.getPrice()));
}

    
public void refreshComboBox() {
    nameBox.removeAllItems();  
    for (int i = 0; i < ProductManager.products.size(); i++) {
        nameBox.addItem(ProductManager.products.get(i).getName());
    }
    
    if (nameBox.getItemCount() > 0) {
        nameBox.setSelectedIndex(0); 
        updateFields();
    } else {
        stockField.setText("");
        priceField.setText("");
    }
}
    
}
