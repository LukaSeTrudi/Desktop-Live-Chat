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
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author lukap
 */
public class User {
    private int id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    
    public User(int _id, String _name, String _lastname, String _email, String _username){
        id = _id;
        name = _name;
        lastname = _lastname;
        email = _email;
        username = _username;
    }
    public User(){
        
    }
    public int GetId(){
        return id;
    }
    public String GetFullName(){
        return name + " " + lastname;
    }
    public JPanel getUserPanel(){
        JPanel jp = new JPanel();
        JButton btn = new JButton("Add Friend");
        JLabel lbl = new JLabel(GetFullName());
        lbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        //jp.setPreferredSize(new Dimension(250,20));
        jp.setAlignmentX(Component.LEFT_ALIGNMENT);
        jp.setBackground(Color.GRAY);
        jp.add(lbl);
        jp.add(btn);
        return jp;
    }
    
}
