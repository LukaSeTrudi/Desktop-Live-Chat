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
    private String friends;
    
    public User(int _id, String _name, String _lastname, String _email, String _username, String _avatar_url){
        id = _id;
        name = _name;
        lastname = _lastname;
        email = _email;
        username = _username;
        avatar_url = _avatar_url;
    }
    public User(int _id, String _name, String _lastname, String _email, String _username, String _avatar_url, String fri){
        id = _id;
        name = _name;
        lastname = _lastname;
        email = _email;
        username = _username;
        avatar_url = _avatar_url;
        friends = fri;
    }
    public User(){
        
    }
    public String isFriend(){
        return friends;
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
    
}
