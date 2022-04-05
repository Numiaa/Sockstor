package me.numiaa.sockstor.repo;

import me.numiaa.sockstor.domain.Sockstor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SockstorRepository extends JpaRepository<Sockstor, Long> {

    Sockstor findByColorAndCottonPart(String color, Byte cottonPart);

    List<Sockstor> findAllByColorAndCottonPart(String color, Byte cottonPart);
    List<Sockstor> findAllByColorAndCottonPartLessThan(String color, Byte cottonPart);
    List<Sockstor> findAllByColorAndCottonPartGreaterThan(String color, Byte cottonPart);
}
