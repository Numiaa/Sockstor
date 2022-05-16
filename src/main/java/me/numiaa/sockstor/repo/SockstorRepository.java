package me.numiaa.sockstor.repo;

import me.numiaa.sockstor.domain.Sockstor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SockstorRepository extends JpaRepository<Sockstor, Long> {

    Sockstor findByColorAndCottonPart(String color, Byte cottonPart);
    List<Sockstor> findAllByColorAndCottonPart(String color, Byte cottonPart);
    List<Sockstor> findAllByColorAndCottonPartLessThan(String color, Byte cottonPart);
    List<Sockstor> findAllByColorAndCottonPartGreaterThan(String color, Byte cottonPart);
}
