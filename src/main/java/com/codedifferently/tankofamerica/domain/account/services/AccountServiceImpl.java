package com.codedifferently.tankofamerica.domain.account.services;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.repo.AccountRepo;
import com.codedifferently.tankofamerica.domain.transactions.models.Transaction;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Account create(Long userId, Account account) throws UserNotFoundException {
        User owner = userService.getById(userId);
        account.setOwner(owner);
        return accountRepo.save(account);
    }

    @Override
    public String getById(String id) {
        return null;
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
    public Double viewBalance(Long userId) throws UserNotFoundException {
        double returnBalance=0.0;
        User owner = userService.getById(userId);
        Long id = owner.getId();
        List<Account> accounts = accountRepo.findByOwner(owner);
        for (Account account: accounts) {
            System.out.println(accounts);
            if (account.getOwner().getId()==id){
                returnBalance=  account.getBalance();
            }else{
                throw new UserNotFoundException("the user is not found to complete deposit");
            }
        }
        System.out.println("this is your current balance \n");
        return returnBalance;

    }

    @Override
    public void deposit(Long userId, double depositValue) throws UserNotFoundException {

        User owner = userService.getById(userId);
        Long id = owner.getId();
        List<Account> accounts = accountRepo.findByOwner(owner);
        for (Account account: accounts) {
            if (account.getOwner().getId()==id){

                System.out.println(account.getBalance());
                 account.setBalance(account.getBalance()+depositValue);
            }else{
                throw new UserNotFoundException("the user is not found to complete deposit");
            }
        }

    }

    @Override
    public void withdraw(Long userId, double withdrawValue) throws UserNotFoundException {
        User owner = userService.getById(userId);
        Long id = owner.getId();
        List<Account> accounts = accountRepo.findByOwner(owner);
        for (Account account: accounts) {
            System.out.println(accounts);
            if (account.getOwner().getId()==id){
                account.setBalance(account.getBalance()-withdrawValue);

            }else{
                throw new UserNotFoundException("the user is not found to complete withdraw ");
            }
        }
    }

    @Override
    public Transaction createTransaction(UUID accountId, double transactionAmount) {
        return null;
    }
}
