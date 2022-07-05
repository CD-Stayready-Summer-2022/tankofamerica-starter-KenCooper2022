package com.codedifferently.tankofamerica.domain.account.services;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.transactions.models.Transaction;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;

import java.util.UUID;

public interface AccountService {
    Account create(Long userId, Account account) throws UserNotFoundException;
    String getById(String id);
    String getAllFromUser(Long userId) throws UserNotFoundException;
    Account update(Account account);
    Boolean delete(String id);
    Double viewBalance(Long userId)throws UserNotFoundException;
    void deposit(Long userId,double depositValue) throws UserNotFoundException;
    void withdraw(Long userId, double withdrawValue) throws UserNotFoundException;
    Transaction createTransaction(UUID accountId,double transactionAmmount);
}
