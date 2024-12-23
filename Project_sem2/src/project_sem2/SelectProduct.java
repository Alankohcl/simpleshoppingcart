
package Project_Sem2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SelectProduct extends JFrame {//inheritance
    private JLabel title = new JLabel("Product List");
    private List<Product> products;
    private User user;
    
    
    public SelectProduct(User user) {
        this.user = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Product Display");//super
        setPreferredSize(new Dimension(700, 1000));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));//BoxLayout
        
        mainPanel.add(title);
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Read the text file
         
         products = ProductLoader.loadProducts("ProductList.txt");
            for(Product product: products){
                JPanel productPanel = new JPanel();
                productPanel.setLayout(new BorderLayout());//BorderLayout

                // Create image label
                JLabel imageLabel = new JLabel();
                ImageIcon imageIcon = new ImageIcon(product.getImagePath());
                Image image = imageIcon.getImage(); 
                Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH); 
                imageLabel.setIcon(new ImageIcon(newimg));
                imageLabel.addMouseListener(new MouseAdapter() {//MouseAdapter
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {//MouseEvent
                        try{
                            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("button-124476.wav"));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);
                            clip.start();
                            ProductDetail productDetail = new ProductDetail(user, product);
                            productDetail.setVisible(true);
                            dispose();
                        }catch(Exception r){ //Catch sound not foud exception
                            System.out.println("sound not fount" + r.toString());
                        }
                    }
                });

                // Create name label
                JLabel nameLabel = new JLabel(product.getName());

                // Add image and name labels to the product panel
                productPanel.add(imageLabel, BorderLayout.WEST);
                productPanel.add(nameLabel, BorderLayout.CENTER);

                // Add product panel to the main panel
                mainPanel.add(productPanel);
            }
        
        add(scrollPane);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        User user = new User();
        SelectProduct aFrame = new SelectProduct(user);
    }


}