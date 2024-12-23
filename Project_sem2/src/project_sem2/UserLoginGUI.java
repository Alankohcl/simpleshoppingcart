
package Project_Sem2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class UserLoginGUI extends JFrame {//Inheritance
    private JTextField nameField;
    private JTextField passwordField;
    private JLabel messageLabel;
    public User user;

    public UserLoginGUI() {
        
        // Create the layered pane
        JLayeredPane layeredPane = new JLayeredPane();

        // Create a label for the background image
        JLabel backgroundLabel;
        try {
            BufferedImage backgroundImage = ImageIO.read(getClass().getResource("clothing-2000-b5ef8d353ed54800b1a29e3e7c5b2709.jpg"));
            ImageIcon backgroundIcon = new ImageIcon(backgroundImage);
            backgroundLabel = new JLabel(backgroundIcon);
        } catch (IOException e) {//catch input/output file not found exception
            backgroundLabel = new JLabel(); // fallback if image loading fails
            System.out.println("Cannot load the image" + e.toString());
        }

        // Set the size and position of the background label
        backgroundLabel.setBounds(-150, 0, 700, 300);

        // Add the background label to the layered pane
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // Create a panel for the login components
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));//GridLayout
        loginPanel.setOpaque(false);

        // Add the name label and field to the login panel
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        loginPanel.add(nameLabel);
        nameField = new JTextField();
        loginPanel.add(nameField);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 16));
        passwordLabel.setForeground(Color.WHITE);
        loginPanel.add(passwordLabel);
        passwordField = new JTextField();
        loginPanel.add(passwordField);
        
        // Add the login button to the login panel
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Serif", Font.BOLD, 16));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {//ActionListener() ActionEvent
                String name = nameField.getText();
                String password = passwordField.getText();
                if (!name.isEmpty() && !password.isEmpty()) {
                    if(validateUser(name, password, "User.txt")){
                        try{
                            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("button-124476.wav"));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);
                            clip.start();
                            openWelcomePage(name);
                            SelectProduct selectProduct = new SelectProduct(user);
                            selectProduct.setVisible(true);
                        }catch(Exception r){//Catch sound file not found exception
                            System.out.println("sound not found " + e.toString());
                        }
                    }else{
                        messageLabel.setText("Please enter the correct name and password");
                    }
                   
                } else {
                    messageLabel.setText("Please enter your name, or password");
                }
            }
        });
        loginButton.setForeground(Color.BLACK);
        loginPanel.add(loginButton);
        
        // Set the size and position of the login panel
        loginPanel.setBounds(80, 100, 240, 80);

        // Add the login panel to the layered pane
        layeredPane.add(loginPanel, JLayeredPane.PALETTE_LAYER);

        // Create a label for messages
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.WHITE);

        // Set the size and position of the message label
        messageLabel.setBounds(80, 200, 240, 20);

        // Add the message label to the layered pane
        layeredPane.add(messageLabel, JLayeredPane.MODAL_LAYER);

        // Set up the frame
        setTitle("User Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(layeredPane);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openWelcomePage(String name) {
        messageLabel.setText("Welcome, " + name + "!");
        messageLabel.setFont(new Font("Serif", Font.BOLD, 20));
    }
    
    private boolean validateUser(String name, String password, String filename){
            try{
                
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[1].trim().equals(name) && parts[2].trim().equals(password)) {
                    scanner.close();
                    user = new User(Integer.parseInt(parts[0]), parts[1], parts[2]);
                    return true;
                }
            }
            scanner.close();
        }catch(FileNotFoundException e){//FileNotFoundException
            System.out.println("File not found" + e.toString());
        }
        return false;
    }
    
    public static void main(String[] args) {
        UserLoginGUI userlogin = new UserLoginGUI();
    }
    
}





