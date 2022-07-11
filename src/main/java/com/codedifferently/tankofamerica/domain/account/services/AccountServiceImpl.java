package com.codedifferently.tankofamerica.domain.account.services;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.repo.AccountRepo;

import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepo accountRepo;
    private UserService userService;


    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, UserService userService) {
        this.accountRepo = accountRepo;
        this.userService = userService;
    }

    @Override
    public Account create(Long userId, Account account, Double balance) throws UserNotFoundException {
        User owner = null;
        try {
            owner = userService.getById(userId);
            account.setOwner(owner);
            account.setBalance(balance);
        }catch(UserNotFoundException e){

        }
        return accountRepo.save(account);
    }






    @Override
    public Account getById(UUID id) throws AccountNotFoundException {
        Iterable<Account>accounts = accountRepo.findAll();
        for(Account account:accounts){
            if(account.getId().equals(id)){
                return account;
            }
        }
        throw new AccountNotFoundException("account not there");

    }

    @Override
    public String getAllFromUser(Long userId) throws UserNotFoundException {
        StringBuilder builder = new StringBuilder();
        User owner = userService.getById(userId);
        List<Account> accounts = accountRepo.findByOwner(owner);
        for (Account account: accounts) {
            builder.append(account.toString() + "\n");
        }
        return builder.toString().trim();
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

    @Override
    public String deposit(UUID id, Double amount) throws AccountNotFoundException {
        Account account = getById(id);
        account.setBalance(account.getBalance() + amount);
         accountRepo.save(account);


         return String.format("%s' account has a current balance  of %.2f after deposit ", account.getOwner().getFirstName(),account.getBalance());
    }

    @Override
    public String withdrawal(UUID id, Double amount) throws AccountNotFoundException {
        Account account = getById(id);
        account.setBalance(account.getBalance() -amount);
         accountRepo.save(account);
         //Transaction transaction =new Transaction(id,(-1)*amount);
        // transactionRepo.save(transaction);
         return String.format("%s' account has a current balance  of %.2f after withdrawal ", account.getOwner().getFirstName(),account.getBalance());


    }


    @Override
    public String viewBalance(UUID uuid) throws AccountNotFoundException,UserNotFoundException {
        Account account = getById(uuid);
        return String.format("%s' account has a current balance  of %.2f ", account.getOwner().getFirstName(),account.getBalance());
    }
}
