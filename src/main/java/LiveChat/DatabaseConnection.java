/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LiveChat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lukap
 */
public class DatabaseConnection {
    private Connection conn;
    
    public DatabaseConnection(){
    }
    public void Open(){
        try {
            conn = DriverManager.getConnection(
                    // insert connection here
                    //
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Cant connect to database, "+ ex.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeDB(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addUser(String _username, String _email, String _password, String _name, String _lastname){
        try {
            String sql = "INSERT INTO users(username, password, name, lastname, email) VALUES (?,?,?,?,?)";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setString(1, _username);
            st.setString(2, _password);
            st.setString(3, _name);
            st.setString(4, _lastname);
            st.setString(5, _email);
            st.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public User GetUser(int _id){
        User u;
        try{
            String sql = "SELECT id, name, lastname, email, username, avatar_url FROM users WHERE id = ?";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, _id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                u = new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), "USER");
                return u;
            }
            
        } catch(SQLException ex){
        }
        return null;
    }
    public User getUserId(String _username, String _password){
        User u;
        try{
            String sql = "SELECT id, name, lastname, email, username, avatar_url FROM users WHERE (username = ? OR email = ?) AND password = ?";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setString(1, _username);
            st.setString(2, _username);
            st.setString(3, _password);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                u = new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), "USER");
                return u;
            }
            
        } catch(SQLException ex){
        }
        return null;
    }
    
    public List<User> getAllContactsThatArentFriends(User u){
        List<User> usr = new ArrayList<User>();
        try{
            String sql = "SELECT u.id, u.name, u.lastname, u.email, u.username, u.avatar_url FROM users u WHERE u.id != ?\n" +
                        "EXCEPT\n" +
                        "(SELECT u.id, u.name, u.lastname, u.email, u.username, u.avatar_url FROM users u\n" +
                        "LEFT OUTER JOIN contacts c ON c.contact_id = u.id WHERE (c.user_id = ? AND c.friends IS NOT NULL)\n" +
                        "UNION\n" +
                        "SELECT u.id, u.name, u.lastname, u.email, u.username, u.avatar_url FROM users u\n" +
                        "LEFT OUTER JOIN contacts c ON c.user_id = u.id WHERE (c.contact_id = ? AND c.friends = 'YES')\n" +
                        ")";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, u.GetId());
            st.setInt(2, u.GetId());
            st.setInt(3, u.GetId());
            ResultSet rs = st.executeQuery();
             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, u.GetId());
            while(rs.next()){
                usr.add(new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch(SQLException ex){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usr;
    }
    
    public List<User> getAllContactsThatAreFriends(User u){
        List<User> usr = new ArrayList<User>();
        try{
            String sql = "SELECT u.id, u.name, u.lastname, u.email, u.username, u.avatar_url,c.friends FROM users u INNER JOIN contacts c ON c.contact_id = u.id WHERE (c.user_id = ? AND u.id != ? AND c.friends = 'YES')\n" +
                "UNION\n" +
                "SELECT u.id, u.name, u.lastname, u.email, u.username, u.avatar_url,c.friends FROM users u INNER JOIN contacts c ON c.user_id = u.id WHERE (c.contact_id = ? AND u.id != ? AND c.friends = 'YES')";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, u.GetId());
            st.setInt(2, u.GetId());
            st.setInt(3, u.GetId());
            st.setInt(4, u.GetId());
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                usr.add(new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch(SQLException ex){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usr;
    }
    public void SendFriendSuggestion(int myUser_id, int other_user_id){
        try {
            String sql = "SELECT addfriend(?,?)";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, myUser_id);
            st.setInt(2, other_user_id);
            st.executeUpdate();
        } catch(SQLException ex){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }
    public List<User> getFriendRequests(int user_id){
        List<User> usr = new ArrayList<User>();
        try{
            String sql = "SELECT u.id, u.name, u.lastname, u.email, u.username, u.avatar_url, c.friends FROM users u INNER JOIN contacts c ON c.user_id = u.id WHERE (c.contact_id = ? AND c.friends = 'PENDING')";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                usr.add(new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch(SQLException ex){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usr;
    }
    public List<User> getSentFriendRequests(int user_id){
        List<User> usr = new ArrayList<User>();
        try{
            String sql = "SELECT u.id, u.name, u.lastname, u.email, u.username, u.avatar_url, c.friends FROM users u INNER JOIN contacts c ON c.contact_id = u.id WHERE (c.user_id = ? AND c.friends = 'PENDING')";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                usr.add(new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch(SQLException ex){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usr;
    }
    public void AddFriendRequest(int user_id, int other_user_id){
        try {
            String sql = "SELECT addfriend(?,?)";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, other_user_id);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void RemoveFriendRequest(int user_id, int other_user_id){
        try {
            String sql = "DELETE FROM contacts WHERE user_id = ? AND contact_id = ?";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, other_user_id);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String checkFriendship(int user_id, int other_user_id){
        String fr = "NO";
        try {
            String sql = "SELECT friends FROM contacts WHERE user_id = ? AND contact_id = ?";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, other_user_id);
            st.setInt(2, user_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                fr = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fr;
    }
    public int getChatId(int user_id, int other_user_id){
        int id = 0;
        try{
            String sql = "SELECT getChatId(?,?)";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, other_user_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    public List<Message> getMessages(int chat_id){
        List<Message> messages = new ArrayList<Message>();
        try{
            String sql = "SELECT * FROM messages WHERE chat_id = ? ORDER BY date ASC";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, chat_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                messages.add(new Message(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(5), rs.getInt(5)));
            }
        } catch(SQLException ex){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return messages;
    }
    public void sendMessage(int chat_id, int sender, String msg){
        try {
            String sql = "INSERT INTO messages(message, chat_id, sent_id) VALUES (?, ?, ?)";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setString(1, msg);
            st.setInt(2, chat_id);
            st.setInt(3, sender);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void readMessages(int chat_id, int user_id){
        try {
            String sql = "SELECT readmsg(?, ?)";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, chat_id);
            st.setInt(2, user_id);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<RecentChat> getRecents(int user_id) {
        List<RecentChat> recents = new ArrayList<RecentChat>();
        try{
            String sql = "SELECT * FROM messages WHERE chat_id = ? ORDER BY date ASC";
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                //recents.add(new Message(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(5), rs.getInt(5)));
            }
        } catch(SQLException ex){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recents;
    }
}
