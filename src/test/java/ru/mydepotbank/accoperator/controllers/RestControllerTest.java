package ru.mydepotbank.accoperator.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mydepotbank.accoperator.models.Account;
import ru.mydepotbank.accoperator.services.AccountService;
import java.math.BigDecimal;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestControllerTest {
    @Mock
    private AccountService accountService;
    @InjectMocks
    private RestController sut;

    @Test
    public void createAccount() {
        when(accountService.getExist(anyLong())).thenReturn(true);
        String response = sut.createAccount(7777);
        assertEquals("ERROR:Account with this 'id' already exist", response);

        when(accountService.getExist(anyLong())).thenReturn(false);
        response = sut.createAccount(7777);
        assertEquals("ERROR:The length of the 'id' needs to be 5 characters", response);

        sut.createAccount(77777);
        verify(accountService).addAccount(any());
    }

    @Test
    public void putCashToAccount() {
        when(accountService.getExist(anyLong())).thenReturn(false);
        String response = sut.putCashToAccount(7, 300);
        assertEquals("ERROR:Account with this 'id' does not exist", response);

        when(accountService.getExist(anyLong())).thenReturn(true);
        when(accountService.getById(anyLong())).thenReturn(new Account(77777, new BigDecimal(450)));

        response = sut.putCashToAccount(7, -154);
        assertEquals("ERROR:Param 'sum' should not be negative", response);

        response = sut.putCashToAccount(7, 289);
        assertEquals("OK", response);
        verify(accountService).editBalance(any());
    }

    @Test
    public void withDrawFromAccount() {
        when(accountService.getExist(anyLong())).thenReturn(false);
        String response = sut.withDrawFromAccount(7, 3567);
        assertEquals("ERROR:Account with this 'id' does not exist", response);

        when(accountService.getExist(anyLong())).thenReturn(true);
        when(accountService.getById(anyLong())).thenReturn(new Account(77777, new BigDecimal(450)));

        response = sut.withDrawFromAccount(7, -156);
        assertEquals("ERROR:Param 'sum' should not be negative", response);

        response = sut.withDrawFromAccount(7, 750);
        assertEquals("ERROR:There is no such amount on the balance", response);

        response = sut.withDrawFromAccount(7, 350);
        assertEquals("OK", response);
        verify(accountService).editBalance(any());
    }

    @Test
    public void getAccountBalance() {
        when(accountService.getById(anyLong())).thenReturn(new Account(77777, new BigDecimal(450)));

        when(accountService.getExist(anyLong())).thenReturn(true);
        String response = sut.getAccountBalance(777);
        verify(accountService).getById(anyLong());

        when(accountService.getExist(anyLong())).thenReturn(false);
        response = sut.getAccountBalance(777);
        assertEquals("ERROR:Account with this 'id' does not exist", response);
    }
}
