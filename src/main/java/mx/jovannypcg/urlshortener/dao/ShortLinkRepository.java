package mx.jovannypcg.urlshortener.dao;

import mx.jovannypcg.urlshortener.model.ShortLink;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Provides the bunch of methods to save short links.
 * As this interface extends <code>CrudRepository</code>, classes that implement
 * this will have methods like save, findAll, delete and so on.
 *
 * Besides the methods provided by CrudRepository, useful methods were added in
 * order to deal with certain data.
 *
 * @author  Jovanny Pablo Cruz Gomez
 *          Software Engineer
 *          jovannypcg@yahoo.com
 */
public interface ShortLinkRepository extends CrudRepository<ShortLink, Integer> {
    /**
     * Retrieves a short link querying by its <code>destination</code>.
     *
     * @param destination Destination to look for.
     * @return Optional object which might contain a short link if found.
     */
    Optional<ShortLink> findByDestination(String destination);

    /**
     * Retrieves the short link with the latest inserted id.
     *
     * @return Short link with the latest inserted id.
     */
    ShortLink findFirstByOrderByIdDesc();
}
