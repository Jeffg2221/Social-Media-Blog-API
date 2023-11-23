package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    //Register Account
    //     The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
    // If the registration is not successful, the response status should be 400. (Client error)
    public Account registerAccount(Account account){
        return accountDAO.registerAccount(account);
    }

    //Get all accounts
    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public boolean checkUsernameExists(String username) {
        // Call the getAccountByUsername method of the AccountDAO object
        // to check if the account exists in the database with the given username
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountByUsername(username);
        
        // If the account is not null, the username exists in the database
        if (account != null) {
            return true;
        } else {
            return false;
        }
    }

    public Account loginAccount(String username, String password)  {
        
        return accountDAO.loginAccount(username, password);
    }

        
           
        
        public Account loginAccount(Account account) {
            return null;
        }
  
}