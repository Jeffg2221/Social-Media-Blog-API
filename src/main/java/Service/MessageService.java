package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    //Constructor that creates a MessageDAO
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    //Constructor for testing
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //Get all messages
     public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
     }

     // Add a message
     public Message addMessage(Message message){
        return messageDAO.addMessage(message);
     }

     //Get message by id
     public Message getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
     }

     //Get message by User ID
     public List<Message> getAllMessagesFromUserList(int posted_by){
         return messageDAO.getAllMessagesFromUser(posted_by);
     }

     //Update Message
     public Message updateMessageTextMessage(int message_id, Message message){
      Message existingMessage = messageDAO.getMessageById(message_id);
      if( existingMessage != null ){
         existingMessage.setMessage_text(message.getMessage_text());
         messageDAO.updateMessage(message_id,existingMessage);
         existingMessage.setMessage_id(message_id);
         
      }
    
     
      return existingMessage;
     }

     //Update message
     public Message updateMessage(int message_id, Message message){
      Message existingMessage = messageDAO.getMessageById(message_id);
      if( existingMessage != null){
         messageDAO.updateMessage(message_id, message);
         message.setMessage_id(message_id);
         return message;
      }
      return existingMessage;
     }

     public Message deletMessageById(int deletMessage){
      return messageDAO.deleteMessageById(deletMessage);
     }

    public void deletMessageById(Message deleteMessage) {
    }

    public Object getAllMessagesFromUserList(String pathParam) {
        return null;
    }
}