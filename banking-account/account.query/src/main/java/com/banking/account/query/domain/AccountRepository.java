package com.banking.account.query.domain;

import com.banking.cqrs.core.domain.BaseEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountHolder(String accountHolder);

    List<BaseEntity> findByBalanceGreaterThan(BigDecimal balance);

    List<BaseEntity> findByBalanceLessThan(BigDecimal balance);
}
