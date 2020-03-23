/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LiveChat;

/**
 *
 * @author lukap
 */
public class RecentChat {
    public int id;
    public String user_name;
    public String last_msg;
    
    public RecentChat(int _id, String _user, String _last) {
        id = _id;
        user_name = _user;
        last_msg = _last;
    }
}
