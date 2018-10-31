package ru.mydepotbank.accoperator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mydepotbank.accoperator.responses.CustomResponse;
import ru.mydepotbank.accoperator.models.Account;
import ru.mydepotbank.accoperator.services.AccountService;
import java.math.BigDecimal;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@org.springframework.web.bind.annotation.RestController

public class RestController {


    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/bankaccount/{id}", method = POST, produces = "application/json")
    public CustomResponse createAccount(@PathVariable(value = "id") long id) {
        if (accountService.getExist(id)) {
            return new CustomResponse("Account with this 'id' already exist");
        } else {
            if(String.valueOf(id).length() != 5) return new CustomResponse("The length of the 'id' needs to be 5 characters");
            accountService.addAccount(new Account(id));
            return new CustomResponse();
        }
    }

    @RequestMapping(value = "/bankaccount/{id}/deposit", method = PUT, produces = "application/json")
    public CustomResponse putCashToAccount(@PathVariable(value = "id") long id, @RequestParam(value = "sum") long sum) {
        if (!accountService.getExist(id)) {
            return new CustomResponse("Account with this 'id' does not exist");
        } else {
            if (sum < 0) {
                return new CustomResponse("Param 'sum' should not be negative");
            } else {
                Account account = accountService.getById(id);
                account.setBalance(account.getBalance().add(new BigDecimal(sum)));
                accountService.editBalance(account);
                return new CustomResponse();
            }
        }
    }

    @RequestMapping(value = "/bankaccount/{id}/withdraw", method = PUT, produces = "application/json")
    public CustomResponse withDrawFromAccount(@PathVariable(value = "id") long id, @RequestParam(value = "sum") double sum) {
        if (!accountService.getExist(id)) {
            return new CustomResponse("Account with this 'id' does not exist");
        } else {
            if (sum < 0) {
                return new CustomResponse("Param 'sum' should not be negative");
            } else {
                Account account = accountService.getById(id);
                if (account.getBalance().compareTo(new BigDecimal(sum)) < 0) {
                    return new CustomResponse("There is no such amount on the balance");
                } else {
                    account.setBalance(account.getBalance().subtract(new BigDecimal(sum)));
                    accountService.editBalance(account);
                    return new CustomResponse();
                }
            }
        }
    }

    @RequestMapping(value = "/bankaccount/{id}/balance", method = GET, produces = "application/json")
    public CustomResponse getAccountBalance(@PathVariable(value = "id") long id) {
        if (accountService.getExist(id)) {
            Account account = accountService.getById(id);
            return new CustomResponse(CustomResponse.statuses.OK, String.valueOf(account.getBalance()));
        } else {
            return new CustomResponse("Account with this 'id' does not exist");
        }
    }
}
