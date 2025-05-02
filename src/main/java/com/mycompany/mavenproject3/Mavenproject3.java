package com.mycompany.mavenproject3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Mavenproject3 extends JFrame implements Runnable {
    private String text;
    List<Product> products = new ArrayList<>();
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;

    public Mavenproject3(String text, List<Product> products) {
        this.text = text;
        this.products = products;
        setTitle("WK. STI Chill");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel teks berjalan
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);

        // Tombol "Kelola Produk"
        JPanel bottomPanel = new JPanel();
        addProductButton = new JButton("Kelola Produk");
        bottomPanel.add(addProductButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        addProductButton.addActionListener(e -> {
            new ProductForm(this, this.products).setVisible(true);
        });

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    class BannerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(text, x, getHeight() / 2);
        }
    }

    @Override
    public void run() {
        width = getWidth();
        while (true) {
            x += 5;
            if (x > width) {
                x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    public void updateText() {
    text = "Menu yang tersedia:";
    for (Product p : products) {
        text += " | " + p.getName();
    }
    bannerPanel.repaint(); 
}


    public static void main(String[] args) {
    List<Product> products = ProductManager.getProducts(); // Ambil dari manager

    String text = "";
    for (Product product : products) {
        text += " | " + product.getName() + " | ";
    }

    Mavenproject3 gui = new Mavenproject3("Menu yang tersedia: " + text, products);
}
    }

