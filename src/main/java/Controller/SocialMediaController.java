package Controller;

import java.util.List;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import Model.Message;
import Model.Account;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();

    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginAccount);
        app.get("/messages", this::getAllMessages);
        app.post("/messages",this::postMessageHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        
    }

    private void getMessageById(Context ctx){
       Integer messageId = Integer.parseInt(ctx.pathParam("message_id"));
       Message message = messageService.getMessageById(messageId);
       if(message != null){
        ctx.json(message);
       }else{
        ctx.result("");
       }
    }

    private void getAllMessagesFromUser(Context ctx){
        Integer messageId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> message =  messageService.getAllMessagesFromUserList(messageId);
        if(message != null){
            ctx.json(message);
        }else{
            ctx.result("");
        }
    }
       

    private void postMessageHandler(Context ctx) throws JsonProcessingException{
       ObjectMapper om = new ObjectMapper();
       Message message = om.readValue(ctx.body(),Message.class);
       Message addMessage = messageService.addMessage(message);
       if(addMessage == null || addMessage.getMessage_text().isEmpty() || addMessage.getMessage_text().length() > 254){
        ctx.status(400);
        
       }else{
        ctx.json(om.writeValueAsString(addMessage));
        
       }
        
    }

    private void updateMessageById(Context ctx)throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(),Message.class);
        System.out.println(ctx.body());
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        System.out.println(message_id);
        Message updatedMessage = messageService.updateMessageTextMessage(message_id, message);
        
        System.out.println(updatedMessage);
        if(   updatedMessage == null ||updatedMessage.getMessage_text().isEmpty()|| updatedMessage.getMessage_text().length() >= 255  ){
            ctx.status(400);
        }else{
            ctx.json(updatedMessage);
        }
    }

    private void deleteMessageById(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        //Check if message exist in the database 
        Message  deleteMessage = messageService.getMessageById(messageId);

        if(deleteMessage != null){
            //Delete message from database and return it in the response body
            messageService.deletMessageById( deleteMessage);
            ctx.json(deleteMessage).status(200);
        }else{
            ctx.status(200);
        }

        
    }

    private void registerAccount(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        
        Account addAccount = accountService.registerAccount(account);
        
        if( addAccount == null || addAccount.getUsername().isEmpty()   || addAccount.getPassword().length()<4 ){
            ctx.status(400);
        }else{
            ctx.json(om.writeValueAsString(addAccount));
        }
    }
        

   public void loginAccount(Context ctx) throws Exception {
    ObjectMapper om = new ObjectMapper();
    Account account = om.readValue(ctx.body(), Account.class);
    
    if (!accountService.checkUsernameExists(account.getUsername())) {
        ctx.status(401);
        ctx.result("");
        return;
    }
    
    Account getAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
    if (getAccount == null) {
        ctx.status(401);
        ctx.result("");
        return;
    }
    
    ctx.json(om.writeValueAsString(getAccount));
    ctx.status(200);
    }


}