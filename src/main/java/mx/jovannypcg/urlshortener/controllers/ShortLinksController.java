package mx.jovannypcg.urlshortener.controllers;

import mx.jovannypcg.urlshortener.dao.ShortLinkRepository;
import mx.jovannypcg.urlshortener.exceptions.DestinationAlreadyExistsException;
import mx.jovannypcg.urlshortener.exceptions.DestinationNotFoundException;
import mx.jovannypcg.urlshortener.model.ShortLink;
import mx.jovannypcg.urlshortener.model.ShortLinkRequest;
import mx.jovannypcg.urlshortener.util.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Contains the actions to deal with short links.
 *
 * @author  Jovanny Pablo Cruz Gomez
 *          Software Engineer
 *          jovannypcg@yahoo.com
 */
@RestController
public class ShortLinksController {
    Logger logger = Logger.getLogger(ShortLink.class.toString());

    @Autowired
    ShortLinkRepository shortLinkRepository;

    /**
     * Creates a new short link.
     * If the destination in <code>request</code> is already stored in database, it is
     * not created another record. Instead, the existing record is retrieved and returned.
     *
     * @param request JSON request that includes the <code>destination</code> URL.
     * @return The created ShortLink in JSON format.
     */
    @RequestMapping(value = "/v1/shortlinks", method = RequestMethod.POST)
    public ShortLink createShortLink(@RequestBody ShortLinkRequest request) {
        logger.info("Starting request POST /v1/shortlinks");

        int lastInsertedId = 0;

        if (shortLinkRepository.findFirstByOrderByIdDesc() != null) {
             lastInsertedId = shortLinkRepository.findFirstByOrderByIdDesc().getId();
        }

        Optional<ShortLink> existingShortLink = shortLinkRepository.findByDestination(request.getDestination());

        if (existingShortLink.isPresent()) {
            logger.info("Existing short link retrieved: " + existingShortLink.get().toString());
            logger.info("Finishing request POST /v1/shortlinks");
            return existingShortLink.get();
        }

        ShortLink newShortLink = new ShortLink();
        newShortLink.setDestination(request.getDestination());
        newShortLink.setSlug(Base62.encode(lastInsertedId  + 1));

        logger.info("ShortLink created: " + newShortLink.toString());
        logger.info("Finishing request POST /v1/shortlinks");

        return shortLinkRepository.save(newShortLink);
    }

    /**
     * Retrieves the whole set of short_link records.
     *
     * @return List of records found in database.
     */
    @RequestMapping(value = "/v1/shortlinks", method = RequestMethod.GET)
    public List<ShortLink> getShortLinks() {
        logger.info("Starting request GET /v1/shortlinks");
        List<ShortLink> links = new ArrayList<>();

        shortLinkRepository.findAll().forEach(links::add);

        logger.info("Finishing request GET /v1/shortlinks");
        return links;
    }

    /**
     * Returns the specific ShortLink entity for the given <code>slug</code>.
     *
     * @param slug Slug to be translated.
     * @return ShortLink which has the destination in JSON format.
     */
    @RequestMapping(value = "/v1/shortlinks/{slug}", method = RequestMethod.GET)
    public ShortLink getShortLinkDetails(@PathVariable String slug) {
        logger.info("Starting request GET /v1/shortlinks/" + slug);

        int destinationId = Base62.decode(slug);

        ShortLink retrievedShortLink = shortLinkRepository.findOne(destinationId);

        if (retrievedShortLink == null) {
            throw new DestinationNotFoundException(slug);
        }

        logger.info("Short link retrieved: " + retrievedShortLink.toString());
        logger.info("Finishing request GET /v1/shortlinks/" + slug);
        return retrievedShortLink;
    }

    /**
     * Retrieves the destination based on the given slug and redirects to it.
     *
     * @param slug Slug to retrieve the destination.
     * @return RedirectView to the destination.
     */
    @RequestMapping(value = "/{slug}", method = RequestMethod.GET)
    public RedirectView redirectFrom(@PathVariable String slug) {
        logger.info("Starting request GET /" + slug);

        int destinationId = Base62.decode(slug);

        ShortLink retrievedShortLink = shortLinkRepository.findOne(destinationId);

        if (retrievedShortLink == null) {
            throw new DestinationNotFoundException(slug);
        }

        logger.info("Redirecting to: " + retrievedShortLink.getDestination());
        logger.info("Finishing request GET /" + slug);
        return new RedirectView(retrievedShortLink.getDestination());
    }

    @ExceptionHandler(DestinationAlreadyExistsException.class)
    void handleDestinationAlreadyExistsException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    void handleDestinationNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}
