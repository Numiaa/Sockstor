package me.numiaa.sockstor.controller;

import me.numiaa.sockstor.domain.Operation;
import me.numiaa.sockstor.domain.Sockstor;
import me.numiaa.sockstor.service.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/socks")
public class SocksController {

    @Autowired
    SocksService socksService;

    @GetMapping
    public String getSocksQuantity(@RequestParam String color,
                                   @RequestParam Operation operation,
                                   @RequestParam Byte cottonPart){
        return socksService.getSocksQuantity(color, operation, cottonPart).toString();
    }

    @PostMapping(path="income", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addSocks(@RequestBody @Valid Sockstor income) {
        socksService.addSocks(income);
    }

    @PostMapping(path = "outcome", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void withdrawSocks(@RequestBody @Valid Sockstor withdraw){
        socksService.withdrawSocks(withdraw);
    }
}
