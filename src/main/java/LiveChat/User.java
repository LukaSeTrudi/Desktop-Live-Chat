/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LiveChat;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author lukap
 */
public class User {
    public static int user_id;
    private int id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String avatar_url;
    
    public User(int _id, String _name, String _lastname, String _email, String _username, String _avatar_url){
        id = _id;
        name = _name;
        lastname = _lastname;
        email = _email;
        username = _username;
        avatar_url = _avatar_url;
    }
    public User(){
        
    }
    public String GetAvatar(){
        return avatar_url;
    }
    public int GetId(){
        return id;
    }
    public String GetFullName(){
        return name + " " + lastname;
    }
    public JPanel getUserPanel(Color c) {
        FlowLayout fl = new FlowLayout();
        JPanel jp = new JPanel();
        jp.setLayout(fl);
        JLabel avatar = new JLabel();
        try {
            avatar.setIcon(new ImageIcon(new ImageIcon(new URL(avatar_url)).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        } catch (MalformedURLException ex) {
            try {
                avatar.setIcon(new ImageIcon(new ImageIcon(new URL("https://www.w3schools.com/howto/img_avatar.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            } catch (MalformedURLException ex1) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        JButton btn = new JButton("Add Friend");
        btn.setActionCommand(Integer.toString(id));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, e.getActionCommand());
            }
        });
        JLabel lbl = new JLabel(GetFullName());
        lbl.setPreferredSize(new Dimension(135, 30));
        lbl.setToolTipText(GetFullName());
        jp.setBackground(c);
        jp.add(avatar);
        jp.add(lbl);
        jp.add(btn);
        jp.setMaximumSize(new Dimension(300, 40));
        return jp;
    }
    
}
