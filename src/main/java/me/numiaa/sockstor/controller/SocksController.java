package me.numiaa.sockstor.controller;

import lombok.RequiredArgsConstructor;
import me.numiaa.sockstor.domain.Operation;
import me.numiaa.sockstor.domain.Sockstor;
import me.numiaa.sockstor.service.SocksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/socks")
@RequiredArgsConstructor
public class SocksController {

    private final SocksService socksService;

    @GetMapping
    public String getSocksQuantity(@RequestParam String color,
                                   @RequestParam Operation operation,
                                   @RequestParam Byte cottonPart){
        return socksService.getSocksQuantity(color, operation, cottonPart).toString();
    }

    @PostMapping(path="income", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addSocks(@RequestBody @Valid Sockstor income) {

        socksService.addSocks(income);
        return new ResponseEntity("Socks added successfully", HttpStatus.OK);
    }

    @PostMapping(path = "outcome", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void withdrawSocks(@RequestBody @Valid Sockstor withdraw){
        socksService.withdrawSocks(withdraw);
    }
}
