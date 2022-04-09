package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositFundsCommand extends BaseCommand {

    private BigDecimal amount;
}
