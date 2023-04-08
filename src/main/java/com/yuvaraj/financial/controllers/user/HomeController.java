package com.yuvaraj.financial.controllers.user;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.helpers.FrequencyHelper;
import com.yuvaraj.financial.services.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.yuvaraj.financial.helpers.FrequencyHelper.validateFrequencyForCustom;
import static com.yuvaraj.financial.helpers.ResponseHelper.ok;

@RestController
@RequestMapping(path = "v1/home")
@Slf4j
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping(path = "total-spending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> totalSpending(Authentication authentication, @Valid @RequestParam @NotNull(message = "Period cannot be empty / blank") FrequencyHelper.Frequency frequency,
                                                @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate) throws InvalidArgumentException {

        validateFrequencyForCustom(frequency, startDate, endDate);

        return ok(homeService.getTotalSpending(frequency, authentication.getName()));
    }

    @GetMapping(path = "wallets-with-transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> wallets(Authentication authentication, @Valid @RequestParam @NotNull(message = "Period cannot be empty / blank") FrequencyHelper.Frequency frequency,
                                          @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate) throws InvalidArgumentException {

        validateFrequencyForCustom(frequency, startDate, endDate);

        return ok(homeService.getWalletsWithTransaction(frequency, authentication.getName()));
    }

}
