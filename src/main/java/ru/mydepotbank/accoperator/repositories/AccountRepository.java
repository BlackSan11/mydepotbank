package ru.mydepotbank.accoperator.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.mydepotbank.accoperator.models.Account;


public interface AccountRepository extends CrudRepository<Account, Long> {
}
