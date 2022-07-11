package com.codedifferently.tankofamerica.domain.account.controllers;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.repo.AccountRepo;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@ShellComponent
public class AccountController {

    private AccountService accountService;
    private AccountRepo accountRepo;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod("Create new Account")
    public String createNewAccount (@ShellOption({"-U","--user"}) Long id,
                                    @ShellOption({"-N", "--name"}) String name,
                                    @ShellOption({"-B","--balance"})Double balance) {
        try {
            Account account = new Account(name);
            account = accountService.create(id, account,balance);
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
    @ShellMethod("View you Balance")
    public String viewBalance(@ShellOption({"--A","--account"})UUID uuid) throws UserNotFoundException, AccountNotFoundException {
        String UserBalance = accountService.viewBalance(uuid);
        return UserBalance;

    }
    @ShellMethod("Deposit")
    public String deposit(@ShellOption({"--A","--account"})UUID uuid, @ShellOption({"-V","--value"}) Double amount) throws AccountNotFoundException{
        String balance = accountService.deposit(uuid,amount);
        return balance;
    }
    @ShellMethod("Withdraw")
    public String Withdraw(@ShellOption({"--A","--account"})UUID uuid, @ShellOption({"-V","--value"}) Double amount) throws AccountNotFoundException{
        String balance = accountService.withdrawal(uuid,amount);
        return balance;
    }


}
