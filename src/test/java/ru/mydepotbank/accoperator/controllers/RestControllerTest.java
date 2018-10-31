package ru.mydepotbank.accoperator.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mydepotbank.accoperator.models.Account;
import ru.mydepotbank.accoperator.responses.CustomResponse;
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
        CustomResponse response = sut.createAccount(7);
        assertEquals(CustomResponse.statuses.ERR, response.getStatus());

        when(accountService.getExist(anyLong())).thenReturn(false);
        response = sut.createAccount(77777);
        assertEquals(CustomResponse.statuses.OK, response.getStatus());

        verify(accountService).addAccount(any());
    }

    @Test
    public void putCashToAccount() {
        when(accountService.getExist(anyLong())).thenReturn(false);
        CustomResponse response = sut.putCashToAccount(7, 300);
        assertEquals(CustomResponse.statuses.ERR, response.getStatus());

        when(accountService.getExist(anyLong())).thenReturn(true);
        when(accountService.getById(anyLong())).thenReturn(new Account(77777, new BigDecimal(450)));

        response = sut.putCashToAccount(7, -154);
        assertEquals(CustomResponse.statuses.ERR, response.getStatus());

        response = sut.putCashToAccount(7, 289);
        assertEquals(CustomResponse.statuses.OK, response.getStatus());
        verify(accountService).editBalance(any());
    }

    @Test
    public void withDrawFromAccount() {
        when(accountService.getExist(anyLong())).thenReturn(false);
        CustomResponse response = sut.withDrawFromAccount(7, 3567);
        assertEquals(CustomResponse.statuses.ERR, response.getStatus());

        when(accountService.getExist(anyLong())).thenReturn(true);
        when(accountService.getById(anyLong())).thenReturn(new Account(77777, new BigDecimal(450)));

        response = sut.withDrawFromAccount(7, -156);
        assertEquals(CustomResponse.statuses.ERR, response.getStatus());

        response = sut.withDrawFromAccount(7, 750);
        assertEquals(CustomResponse.statuses.ERR, response.getStatus());

        response = sut.withDrawFromAccount(7, 350);
        assertEquals(CustomResponse.statuses.OK, response.getStatus());
        verify(accountService).editBalance(any());
    }

    @Test
    public void getAccountBalance() {
        when(accountService.getExist(anyLong())).thenReturn(true);
        when(accountService.getById(anyLong())).thenReturn(new Account(77777, new BigDecimal(450)));

        CustomResponse response = sut.getAccountBalance(777);
        assertEquals(CustomResponse.statuses.OK, response.getStatus());

        when(accountService.getExist(anyLong())).thenReturn(false);

        response = sut.getAccountBalance(777);
        assertEquals(CustomResponse.statuses.ERR, response.getStatus());
    }
}