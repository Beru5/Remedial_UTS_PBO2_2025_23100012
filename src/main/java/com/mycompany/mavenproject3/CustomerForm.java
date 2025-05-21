package com.mycompany.mavenproject3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerForm extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    private Mavenproject3 gui;

    public CustomerForm(Mavenproject3 gui) {
        this.gui = gui;

        setTitle("WK. Cuan | Daftar Pelanggan");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1));

        formPanel.add(new JLabel("Nama Pelanggan:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("No. Telepon:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        addButton = new JButton("Tambah");
        formPanel.add(addButton);
        editButton = new JButton("Edit");
        formPanel.add(editButton);
        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new String[]{"ID","Nama", "No. Telepon"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadCustomerData();

        table.getSelectionModel().addListSelectionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                nameField.setText(tableModel.getValueAt(selected, 1).toString());
                phoneField.setText(tableModel.getValueAt(selected, 2).toString());
            }
        });

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            if (!name.isEmpty() && !phone.isEmpty()) {
                Customer newCustomer = new Customer(name, phone);
                CustomerManager.addCustomer(newCustomer);
                loadCustomerData();
                nameField.setText("");
                phoneField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
            }
        });


        editButton.addActionListener(e -> {
           int selectedRow = table.getSelectedRow();
           if (selectedRow < 0) {
               JOptionPane.showMessageDialog(this, "Pilih produk yang ingin diedit.");
               return;
           }

           try {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
               Customer preupdatedCustomer = CustomerManager.getCustomers().get(selectedRow);
               Customer updatedCustomer = new Customer(preupdatedCustomer.getId(), name, phone);
               CustomerManager.editCustomer(selectedRow, updatedCustomer);
               
               loadCustomerData();
               nameField.setText("");
               phoneField.setText("");

           } catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(this, "Input tidak valid:\n" + ex);
           }
           });
        
        deleteButton.addActionListener(e -> {
           int selectedRow = table.getSelectedRow();
           if (selectedRow < 0) {
               JOptionPane.showMessageDialog(this, "Pilih customer yang ingin dihapus.");
           } else {
                CustomerManager.deleteCustomer(selectedRow); // gunakan method di CustomerManager
                loadCustomerData();
                nameField.setText("");
                phoneField.setText("");
            }
        });
    }

    private void loadCustomerData() {
        tableModel.setRowCount(0);
        for (Customer c : CustomerManager.getCustomers()) {
            tableModel.addRow(new Object[]{c.getId(), c.getName(), c.getPhone()});
        }
    }

}
