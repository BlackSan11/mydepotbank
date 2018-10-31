package ru.mydepotbank.accoperator.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private long id;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account() {
    }

    public Account(long id) {
        this.id = id;
    }

    public Account(long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance.setScale(2, BigDecimal.ROUND_CEILING);
    }

    public long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(2, BigDecimal.ROUND_CEILING);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
