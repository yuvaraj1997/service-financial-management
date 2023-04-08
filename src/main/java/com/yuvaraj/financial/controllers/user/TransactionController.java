package com.yuvaraj.financial.controllers.user;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.helpers.ErrorCode;
import com.yuvaraj.financial.helpers.FrequencyHelper;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.createTransaction.CreateTransactionRequest;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.updateTransaction.UpdateTransactionRequest;
import com.yuvaraj.financial.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

import static com.yuvaraj.financial.helpers.FrequencyHelper.validateFrequencyForCustom;
import static com.yuvaraj.financial.helpers.ResponseHelper.ok;
import static com.yuvaraj.financial.helpers.ResponseHelper.okAsJson;

@RestController
@RequestMapping(path = "v1/transaction")
@Slf4j
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(Authentication authentication, @Valid @RequestBody CreateTransactionRequest createTransactionRequest) throws InvalidArgumentException {
        if (createTransactionRequest.getAmount() == 0) {
            throw new InvalidArgumentException("Zero is not allowed", ErrorCode.INVALID_ARGUMENT);
        }
        return ok(transactionService.create(createTransactionRequest, authentication.getName()));
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(Authentication authentication, @Valid @RequestBody UpdateTransactionRequest updateTransactionRequest) throws InvalidArgumentException {
        if (updateTransactionRequest.getAmount() == 0) {
            throw new InvalidArgumentException("Zero is not allowed", ErrorCode.INVALID_ARGUMENT);
        }
        return ok(transactionService.update(updateTransactionRequest, authentication.getName()));
    }

    @GetMapping(path = "{walletId}/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get(Authentication authentication, @PathVariable String walletId, @PathVariable String transactionId) throws InvalidArgumentException {
        return ok(transactionService.findByWalletIdAndTransactionId(walletId, transactionId, authentication.getName()));
    }

    @DeleteMapping(path = "{walletId}/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(Authentication authentication, @PathVariable String walletId, @PathVariable String transactionId) throws InvalidArgumentException {
        transactionService.delete(walletId, transactionId, authentication.getName());
        return okAsJson();
    }

    @GetMapping(path = "{walletId}/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> summary(Authentication authentication, @PathVariable String walletId, @RequestParam("type") FrequencyHelper.Frequency frequency,
                                          @RequestParam(name = "startDate", required = false) Date startDate, @RequestParam(name = "endDate", required = false) Date endDate) throws InvalidArgumentException {

        validateFrequencyForCustom(frequency, startDate, endDate);

        return ok(transactionService.summary(walletId, frequency, authentication.getName()));
    }


    @GetMapping(path = "{walletId}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> transactions(Authentication authentication, @PathVariable String walletId, @RequestParam FrequencyHelper.Frequency frequency,
                                               @RequestParam(name = "startDate", required = false) Date startDate, @RequestParam(name = "endDate", required = false) Date endDate) throws InvalidArgumentException {

        validateFrequencyForCustom(frequency, startDate, endDate);

        return ok(transactionService.transactions(walletId, frequency, authentication.getName()));
    }
}
