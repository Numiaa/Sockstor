package me.numiaa.sockstor.service;

import lombok.RequiredArgsConstructor;
import me.numiaa.sockstor.domain.Operation;
import me.numiaa.sockstor.domain.Sockstor;
import me.numiaa.sockstor.repo.SockstorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SocksService {

    private final SockstorRepository sockstorRepository;

    public Long getSocksQuantity(String color, Operation operation, Byte cottonPart) {
        color = color.toLowerCase(Locale.ROOT);
        List<Sockstor> sockstorList = new ArrayList<>();

        switch (operation) {
            case equal:
                sockstorList = sockstorRepository.findAllByColorAndCottonPart(color, cottonPart);
                break;
            case lessThan:
                sockstorList = sockstorRepository.findAllByColorAndCottonPartLessThan(color, cottonPart);
                break;
            case moreThan:
                sockstorList = sockstorRepository.findAllByColorAndCottonPartGreaterThan(color, cottonPart);
                break;
        }

        Long quantity = 0L;

        for (Sockstor sockstor : sockstorList) {
            quantity += sockstor.getQuantity();
        }

        return quantity;
    }

    public void addSocks(Sockstor income) {
        Sockstor sockstor = sockstorRepository.findByColorAndCottonPart(
                income.getColor().toLowerCase(Locale.ROOT),
                income.getCottonPart());

        if (sockstor == null) {
            income.setColor(income.getColor().toLowerCase(Locale.ROOT));
            sockstor = income;
        } else {
            sockstor.setQuantity(income.getQuantity() + sockstor.getQuantity());
        }

        sockstorRepository.save(sockstor);
    }

    public void withdrawSocks(Sockstor withdraw) {
        Sockstor sockstor = sockstorRepository.findByColorAndCottonPart(
                withdraw.getColor().toLowerCase(Locale.ROOT),
                withdraw.getCottonPart());

        if (!(sockstor != null && sockstor.getQuantity() - withdraw.getQuantity() >= 0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            sockstor.setQuantity(sockstor.getQuantity() - withdraw.getQuantity());
        }

        sockstorRepository.save(sockstor);
    }
}
