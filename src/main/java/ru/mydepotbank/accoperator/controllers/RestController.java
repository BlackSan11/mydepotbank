package ru.mydepotbank.accoperator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ru.mydepotbank.accoperator.models.Account;
import ru.mydepotbank.accoperator.services.AccountService;

import javax.servlet.ServletException;
import java.math.BigDecimal;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@org.springframework.web.bind.annotation.RestController

public class RestController {


    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/bankaccount/{id}", method = POST, produces = "application/json")
    public String createAccount(@PathVariable(value = "id") long id) {
        if (accountService.getExist(id)) {
            return "ERROR:Account with this 'id' already exist";
        } else {
            if (String.valueOf(id).length() != 5)
                return "ERROR:The length of the 'id' needs to be 5 characters";
            accountService.addAccount(new Account(id));
            return "OK";
        }
    }

    @RequestMapping(value = "/bankaccount/{id}/deposit", method = PUT, produces = "application/json")
    public String putCashToAccount(@PathVariable(value = "id") long id, @RequestParam(value = "sum") double sum) {
        if (!accountService.getExist(id)) {
            return "ERROR:Account with this 'id' does not exist";
        } else {
            if (sum < 0) {
                return "ERROR:Param 'sum' should not be negative";
            } else {
                Account account = accountService.getById(id);
                account.setBalance(account.getBalance().add(new BigDecimal(sum)));
                accountService.editBalance(account);
                return "OK";
            }
        }
    }

    @RequestMapping(value = "/bankaccount/{id}/withdraw", method = PUT, produces = "application/json")
    public String withDrawFromAccount(@PathVariable(value = "id") long id, @RequestParam(value = "sum") double sum) {
        if (!accountService.getExist(id)) {
            return "ERROR:Account with this 'id' does not exist";
        } else {
            if (sum < 0) {
                return "ERROR:Param 'sum' should not be negative";
            } else {
                Account account = accountService.getById(id);
                if (account.getBalance().compareTo(new BigDecimal(sum)) < 0) {
                    return "ERROR:There is no such amount on the balance";
                } else {
                    account.setBalance(account.getBalance().subtract(new BigDecimal(sum)));
                    accountService.editBalance(account);
                    return "OK";
                }
            }
        }
    }

    @RequestMapping(value = "/bankaccount/{id}/balance", method = GET, produces = "application/json")
    public String getAccountBalance(@PathVariable(value = "id") long id) {
        if (accountService.getExist(id)) {
            Account account = accountService.getById(id);
            return String.valueOf(account.getBalance());
        } else {
            return "ERROR:Account with this 'id' does not exist";
        }
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    protected String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return "ERROR:" + e.getMessage();
    }

}
