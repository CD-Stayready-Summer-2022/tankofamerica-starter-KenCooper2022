package com.codedifferently.tankofamerica.domain.account.services;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

public interface AccountService {
    Account create(Long userId, Account account,Double balance) throws UserNotFoundException;
    Account getById(UUID id)throws AccountNotFoundException;



    String getAllFromUser(Long userId) throws UserNotFoundException;
    Account update(Account account);
    Boolean delete(String id);
    String deposit(UUID id, Double amount) throws AccountNotFoundException;
    String withdrawal(UUID id, Double ammount) throws AccountNotFoundException;


    String viewBalance(UUID uuid) throws AccountNotFoundException,UserNotFoundException;
}
