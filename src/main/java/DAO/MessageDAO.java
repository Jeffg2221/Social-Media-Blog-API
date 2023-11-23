package DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    public  List<Message> getAllMessages(){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message addMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try {
            
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES ( ?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.time_posted_epoch);
            

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1); 
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(),message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessageById(int messageId){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "SELECT * FROM message WHERE message_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            
            preparedStatement.setInt(1,messageId);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by") ,rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void  updateMessage(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "UPDATE message SET  posted_by = ?, message_text = ? , time_posted_epoch=? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.time_posted_epoch);
            preparedStatement.setInt(4, message_id);
            


            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
		
    }

    public Message deleteMessageById(int deletMessage){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "DELETE FROM message WHERE message_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            
            preparedStatement.setInt(1,deletMessage);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by") ,rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;


        
    }
    public List<Message> getAllMessagesFromUser(int posted_by){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setInt(1,posted_by);
            
           


            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    

    
}