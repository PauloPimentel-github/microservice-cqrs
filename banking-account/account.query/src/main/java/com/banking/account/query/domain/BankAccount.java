package com.banking.account.query.domain;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountHolder;
    private LocalDateTime creationDate;
    private AccountType accountType;
    private BigDecimal balance;
}
