package ru.mydepotbank.accoperator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mydepotbank.accoperator.models.Account;
import ru.mydepotbank.accoperator.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account addAccount(Account account) {
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    @Override
    public Account getById(long id){
        return accountRepository.findById(id).get();
    }

    @Override
    public Account editBalance(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean getExist(long id) {
        return accountRepository.existsById(id);
    }

}
