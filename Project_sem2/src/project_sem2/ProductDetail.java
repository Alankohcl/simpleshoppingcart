
package Project_Sem2;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ProductDetail extends JFrame {
    private Product product;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel sizeLabel;
    private JLabel colorLabel;
    private JLabel quantity;
    private JTextField quantityField;
    private JButton addToCartButton;
    private JButton backButton;
    private JButton exitButton;
    private static User user;

    public ProductDetail(User user, Product product) {
        this.product = product;
        ProductDetail.user = user;

        setTitle("Product Detail");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 600));

        JPanel mainPanel = new JPanel(new BorderLayout());//BorderLayout  
        
        JLayeredPane layer = new JLayeredPane();
        
        JLabel backgroundLabel;
        try {
            ImageIcon backgroundImageIcon = new ImageIcon(getClass().getResource("downloaded.jpeg"));
            Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        } catch (Exception e) {//Exception
            backgroundLabel = new JLabel();
            System.out.println("Cannot load the background image " + e.toString());
        }

        // Set the size and position of the background label
        backgroundLabel.setBounds(-150, 0, 700, 300);

        // Add the background label to the layered pane
        layer.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // Create left panel for image display
        JPanel leftPanel = new JPanel(new BorderLayout());//BorderLayout
        imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(product.getImagePath());
        Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // Create right panel for product details
        JPanel rightPanel = new JPanel(new GridLayout(3, 6));//GridLayout
        nameLabel = new JLabel("Name: " + product.getName());
        nameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        priceLabel = new JLabel("Price: " + product.getPrice());
        priceLabel.setFont(new Font("Serif", Font.BOLD, 18));
        sizeLabel = new JLabel("Size: " + product.getSize());
        sizeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        colorLabel = new JLabel("Color: " + product.getColor());
        colorLabel.setFont(new Font("Serif", Font.BOLD, 18));
        quantityField = new JTextField();
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Serif", Font.BOLD, 18));
        backButton = new JButton("Back");
        backButton.setFont(new Font("Serif", Font.BOLD, 18));
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.BOLD, 18));
        quantity = new JLabel("Quantity");
        quantity.setFont(new Font("Serif", Font.BOLD, 18));

        rightPanel.add(new JLabel());
        rightPanel.add(nameLabel);
        rightPanel.add(new JLabel());
        rightPanel.add(priceLabel);
        rightPanel.add(new JLabel());
        rightPanel.add(sizeLabel);
        rightPanel.add(new JLabel());
        rightPanel.add(colorLabel);
        rightPanel.add(new JLabel());
        rightPanel.add(quantity);
        rightPanel.add(new JLabel());
        rightPanel.add(quantityField);
        rightPanel.add(new JLabel());
        rightPanel.add(addToCartButton);
        rightPanel.add(new JLabel());
        rightPanel.add(backButton);
        rightPanel.add(new JLabel());
        rightPanel.add(exitButton);
        
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setVisible(true);

        // Add action listener for "Add to Cart" button
        addToCartButton.addActionListener(new ActionListener() {//ActionListener
            @Override
            public void actionPerformed(ActionEvent e) {//ActionEvent
                String quantityText = quantityField.getText();

                if (!quantityText.isEmpty()) {
                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("button-124476.wav"));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                        
                        int quantity = Integer.parseInt(quantityText);
                        JOptionPane.showMessageDialog(null, "Product added to cart: " + quantity + " quantity");

                        List<Product> cartItems = new ArrayList<>();
                        cartItems.add(product); 
                        ProductCart productCart = new ProductCart(cartItems, quantity);
                        productCart.setVisible(true);
                        dispose();
                        
                        writeCartToFile(cartItems, quantity);
                        
                    } catch (NumberFormatException ex) {//Catch when parse int not functioning
                        JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter an integer value." + ex.toString());
                    } catch (InputMismatchException ex) {//catch when wrong input is done
                        JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter an integer value." + ex.toString());
                    } catch (Exception ex) {//catch general exception
                        JOptionPane.showMessageDialog(null, "Error in add to cart button: " + ex.toString());
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//ActionEvent
                try{
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("button-124476.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();

                    SelectProduct selectProduct = new SelectProduct(user);
                    selectProduct.setVisible(true);
                    dispose();
                }catch(Exception r){//catch sound exception
                     System.out.println("sound not fount" + r.toString());
                }
            }
        });
        
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){//ActionEvent
                System.exit(0);
            }
        });
    }
    
    public void writeCartToFile(List<Product> cartItems, int quantity){
         try (FileWriter fileWriter = new FileWriter("Cart.txt", true);
         PrintWriter writer = new PrintWriter(fileWriter)) {
            for (Product item: cartItems){
                writer.print(item.getId());
                writer.print(",");
                writer.print(item.getName());
                writer.print(",");
                writer.print(item.getPrice());
                writer.print(",");
                writer.print(item.getSize());
                writer.print(",");
                writer.print(item.getColor());
                writer.print(",");
                writer.print(quantity);
                writer.println();
            }
        }catch(FileNotFoundException e){//catch when file not found exception 
            JOptionPane.showMessageDialog(null, "error writing cart to file:" + e.toString());
        }catch(IOException e){//catch when input/output file not found
            JOptionPane.showMessageDialog(null, "Error writing cart to file: " + e.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Read product data from file
            List<Product> products = readProductsFromFile("ProductList.txt");

            if (!products.isEmpty()) {
                // Create a ProductDetail frame for the first product and display it
                ProductDetail frame = new ProductDetail(user, products.get(0));
                frame.setVisible(true);
            } else {
                System.out.println("Failed to read product data from file.");
            }
        });
    }

    public static List<Product> readProductsFromFile(String filename) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                double price = Double.parseDouble(data[2]);
                String size = data[3];
                String color = data[4];
                String imagePath = data[5];
                products.add(new Product(id, name, price, size, color, imagePath));
            }
        } catch (IOException e) {//catch when input/output file not found
            System.out.println("error" + e.toString());
        }

        return products;
    }
}






