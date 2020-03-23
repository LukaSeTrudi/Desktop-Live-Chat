/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LiveChat;

import java.sql.Timestamp;

/**
 *
 * @author lukap
 */
public class Message {
    public int id;
    public String msg;
    public String timestamp;
    public int sent_id;
    public int chat_id;
    
    public Message(int _id, String _msg, String _time, int _chat, int _sent){    
        id = _id;
        msg = _msg;
        timestamp = _time;
        sent_id = _sent;
        chat_id = _chat;
    }
}
