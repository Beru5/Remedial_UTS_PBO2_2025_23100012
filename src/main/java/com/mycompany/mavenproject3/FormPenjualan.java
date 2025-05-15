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

public class FormPenjualan extends JFrame{
    private JTable tabelPenjualan;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField quantityField;
    private JTextField priceField;    
    private JTextField stockField;
    private JButton processButton;
    private Mavenproject3 gui;
    
    public FormPenjualan (Mavenproject3 gui) {  
        this.gui = gui;
        
        setTitle("WK. Cuan | Jual Barang");
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        formPanel.add(new JLabel("Code Produk:"));
        codeField = new JTextField(5);
        formPanel.add(codeField);
        
        formPanel.add(new JLabel("Stok Produk:"));
        stockField = new JTextField(5);
        formPanel.add(stockField);
        
        formPanel.add(new JLabel("Harga Jual:"));
        priceField = new JTextField(5);
        formPanel.add(priceField);
        
        formPanel.add(new JLabel("Kuantitas:"));
        quantityField = new JTextField(5);
        formPanel.add(quantityField);
        
        processButton = new JButton("Process");
        formPanel.add(processButton);
        
        add(formPanel, BorderLayout.CENTER);
        
        setVisible(true); 
        
        processButton.addActionListener(e -> {
            try {
               String code = codeField.getText();
            }
            catch (Exception ex) {
               JOptionPane.showMessageDialog(this, "eror!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    
}
