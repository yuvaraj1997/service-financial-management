package com.yuvaraj.financial.controllers.user;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.exceptions.wallet.WalletAlreadyExistException;
import com.yuvaraj.financial.models.controllers.v1.transaction.wallet.createWallet.CreateWalletRequest;
import com.yuvaraj.financial.models.controllers.v1.transaction.wallet.updateWallet.UpdateWalletRequest;
import com.yuvaraj.financial.services.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.yuvaraj.financial.helpers.ResponseHelper.ok;
import static com.yuvaraj.financial.helpers.ResponseHelper.okAsJson;

@RestController
@RequestMapping(path = "v1/wallet")
@Slf4j
public class WalletController {

    @Autowired
    WalletService walletService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(Authentication authentication, @Valid @RequestBody CreateWalletRequest createWalletRequest) throws WalletAlreadyExistException {
        return ok(walletService.create(createWalletRequest, authentication.getName()));
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(Authentication authentication, @Valid @RequestBody UpdateWalletRequest updateWalletRequest) throws WalletAlreadyExistException, InvalidArgumentException {
        return ok(walletService.update(updateWalletRequest, authentication.getName()));
    }

    @DeleteMapping(path = "{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(Authentication authentication, @PathVariable String walletId) throws InvalidArgumentException {
        walletService.delete(walletId, authentication.getName());
        return okAsJson();
    }

    @GetMapping(path = "{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get(Authentication authentication, @PathVariable String walletId) throws InvalidArgumentException {
        return ok(walletService.findByIdAndUserId(walletId, authentication.getName()));
    }

    @GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAll(Authentication authentication) {
        return ok(walletService.getAllByUserId(authentication.getName()));
    }

}
