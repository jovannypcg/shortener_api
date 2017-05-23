package mx.jovannypcg.urlshortener.dao;

import mx.jovannypcg.urlshortener.model.ShortLink;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortLinkRepository extends CrudRepository<ShortLink, Integer> {
    Optional<ShortLink> findByDestination(String destination);
}
