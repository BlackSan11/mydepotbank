package ru.mydepotbank.accoperator.services;

import ru.mydepotbank.accoperator.models.Account;

public interface AccountService {
    Account addAccount(Account account);
    Account getById(long id);
    Account editBalance(Account account);
    boolean getExist(long id);
}
