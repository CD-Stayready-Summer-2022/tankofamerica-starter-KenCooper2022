package com.codedifferently.tankofamerica.domain.account.controllers;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod("Create new Account")
    public String createNewAccount (@ShellOption({"-U","--user"}) Long id,
                                     @ShellOption({"-N", "--name"}) String name)
            {
        try {
            Account account = new Account(name);
            account = accountService.create(id, account);
            return account.toString();
        } catch (UserNotFoundException e) {
            return "The User Id is invalid";
        }
    }

    @ShellMethod("Get User Accounts")
    public String userAccounts(@ShellOption({"-U","--user"}) Long id){

        try{
            String accounts = accountService.getAllFromUser(id);
            return accounts;
        }catch (UserNotFoundException e){
            return "The User Id is invalid";
        }

    }
    @ShellMethod("deposit into account")
    public void deposit( @ShellOption({"-U","--user"})  Long id,@ShellOption({"-V","--Value"}) double deposit) throws UserNotFoundException {
        accountService.deposit(id, deposit);
    }
    @ShellMethod("withdraw from account")
    public void withdraw( @ShellOption({"U", "--user"}) Long id,@ShellOption({"-V","--Value"}) double deposit) throws UserNotFoundException {
        accountService.withdraw(id, deposit);
    }
    @ShellMethod("view account balance")
    public double viewBalance( @ShellOption({"User", "--user"}) Long id) throws UserNotFoundException {
        return accountService.viewBalance(id);
    }
}
