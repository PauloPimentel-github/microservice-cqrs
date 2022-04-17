package com.banking.account.cmd.api.controllers;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.cmd.api.dto.OpenAccountResponse;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {

    @Autowired
    private CommandDispatcher commonDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            this.commonDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("La cuenta del banco se ha creado exitosamente", id), HttpStatus.CREATED);
        } catch (IllegalStateException illegalStateException) {
            log.error(MessageFormat.format("The bank account has been successfully created - {0}", illegalStateException.toString()));
            return new ResponseEntity<>(new BaseResponse(illegalStateException.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            var safeErrorMessage = MessageFormat.format("Errors when processing the request - {0}", id);
            log.error(safeErrorMessage, exception);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
