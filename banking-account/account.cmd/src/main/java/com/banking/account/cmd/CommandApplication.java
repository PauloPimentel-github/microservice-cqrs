package com.banking.account.cmd;

import com.banking.account.cmd.api.command.*;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {

		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers(){
		this.commandDispatcher.registerHandler(OpenAccountCommand.class, this.commandHandler::handle);
		this.commandDispatcher.registerHandler(DepositFundsCommand.class, this.commandHandler::handle);
		this.commandDispatcher.registerHandler(WithdrawFundsCommand.class, this.commandHandler::handle);
		this.commandDispatcher.registerHandler(CloseAccountCommand.class, this.commandHandler::handle);
	}
}
