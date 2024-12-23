
package Project_Sem2;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ProductCart extends JFrame {
    private static List<Product> cartItems;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JTextField totalPriceField;
    private User user;
    private int quantity;
    
    public ProductCart(User user){
        this.user = user;
    }
    public ProductCart(List<Product> cartItems){
        this(cartItems, 0);
    }
    
    public List<Product> getCartItems() {
        return cartItems;
    }
    
    public ProductCart(List<Product> cartItems, int quantity) {
        ProductCart.cartItems = cartItems;
        this.quantity = quantity;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Product Cart");
        setPreferredSize(new Dimension(1000, 300));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());//BorderLayout
        
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BorderLayout());

        // Create cart table
        cartTableModel = new DefaultTableModel();
        cartTableModel.addColumn("ID");
        cartTableModel.addColumn("Name");
        cartTableModel.addColumn("Price");
        cartTableModel.addColumn("Size");
        cartTableModel.addColumn("Color");
        cartTableModel.addColumn("Quantity");
        cartTableModel.addColumn("Sum");

        cartTable = new JTable(cartTableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        // Add cart items to the table
        for (Product product : cartItems) {
            Object[] rowData = {
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getSize(),
                    product.getColor(),
                    quantity, 
                    product.getPrice() * quantity 
            };
            cartTableModel.addRow(rowData);
        }

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create total price field
        totalPriceField = new JTextField();
        totalPriceField.setEditable(false);
        mainPanel.add(totalPriceField, BorderLayout.SOUTH);

        // Create pay button
        JButton payButton = new JButton("Pay");
        payButton.addActionListener(new ActionListener() {//ActionListener
            @Override
            public void actionPerformed(ActionEvent e) {//ActionEvent
                try{
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("cash-register-141427.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    double totalSum = calculateTotalSum();
                    JOptionPane.showMessageDialog(null, "You Payed a total of RM " + totalSum);
                }catch(Exception er){//Exception
                    System.out.println("Sound Not Found" + er.toString());
                }
            }
        });

        secondPanel.add(payButton, BorderLayout.CENTER);
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        secondPanel.add(exitButton,BorderLayout.EAST);
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                SelectProduct selectProduct = new SelectProduct(user);
                selectProduct.setVisible(true);
                dispose();
            }
        });
        
        secondPanel.add(backButton, BorderLayout.WEST);
        
        add(mainPanel);
        add(secondPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
    
    public double calculateTotalSum(){
        double totalSum = 0;
        int rowCount = cartTableModel.getRowCount();
        int sumColumnIndex = cartTableModel.findColumn("Sum");
        for (int row = 0; row < rowCount; row++) {
            Object sumValue = cartTableModel.getValueAt(row, sumColumnIndex);
            if (sumValue instanceof Double) {
                totalSum += (Double) sumValue;
            }
        }
        return totalSum;
    }
    
    public static void main(String[] args) {
        List<Product> cartItems = new ArrayList<>();

        SwingUtilities.invokeLater(() -> new ProductCart(cartItems));
    }
}

