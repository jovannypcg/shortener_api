package mx.jovannypcg.urlshortener.controllers;

import mx.jovannypcg.urlshortener.dao.ShortLinkRepository;
import mx.jovannypcg.urlshortener.exceptions.BadRequestException;
import mx.jovannypcg.urlshortener.exceptions.DestinationNotFoundException;
import mx.jovannypcg.urlshortener.exceptions.SlugAlreadyExistsException;
import mx.jovannypcg.urlshortener.model.ShortLink;
import mx.jovannypcg.urlshortener.model.ShortLinkRequest;
import mx.jovannypcg.urlshortener.util.Base62;
import mx.jovannypcg.urlshortener.util.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
        logger.info("Request: " + request);

        if (request.getDestination() == null || !URL.isValid(request.getDestination())) {
            throw new BadRequestException("URL destination [" + request.getDestination() + "] is not valid");
        }

        logger.info("Finishing request POST /v1/shortlinks");

        if (request.getSlug() != null) {
            return handleCustomSlug(request);
        } else {
            return handleNewSlug(request);
        }
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

        Optional<ShortLink> retrievedShortLink = shortLinkRepository.findBySlug(slug);

        if (!retrievedShortLink.isPresent()) {
            return new RedirectView("https://cdn-images-1.medium.com/max/800/1*qdFdhbR00beEaIKDI_WDCw.gif");
            // throw new DestinationNotFoundException(slug);
        }

        updateVisitCounter(retrievedShortLink.get());

        logger.info("Redirecting to: " + retrievedShortLink.get().getDestination());
        logger.info("Finishing request GET /" + slug);
        return new RedirectView(retrievedShortLink.get().getDestination());
    }

    @ExceptionHandler(SlugAlreadyExistsException.class)
    void handleSlugAlreadyExistsException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    void handleDestinationNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BadRequestException.class)
    void BadRequestException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    private ShortLink updateVisitCounter(ShortLink shortLink) {
        int visitCounter = shortLink.getVisitCount() + 1;
        shortLink.setVisitCount(visitCounter);

        logger.info("Visit number: " + visitCounter);
        shortLink.setVisitCount(visitCounter);
        shortLinkRepository.save(shortLink);

        return shortLink;
    }

    private ShortLink handleNewSlug(ShortLinkRequest request) {
        int lastInsertedId = 0;

        if (shortLinkRepository.findFirstByOrderByIdDesc() != null) {
            lastInsertedId = shortLinkRepository.findFirstByOrderByIdDesc().getId();
        }

        int toEncode = lastInsertedId + 1;

        ShortLink newShortLink = new ShortLink();
        newShortLink.setDestination(request.getDestination());
        newShortLink.setSlug(Base62.encode(toEncode));

        logger.info("ShortLink created: " + newShortLink.toString());

        return shortLinkRepository.save(newShortLink);
    }

    private ShortLink handleCustomSlug(ShortLinkRequest request) {
        Optional<ShortLink> existingSlug = shortLinkRepository.findBySlug(request.getSlug());

        if (!Base62.containsValidCharacters(request.getSlug())) {
            throw new BadRequestException("Slug [" + request.getSlug() + "] contains invalid characters");
        }

        logger.info("Decoded slug: " + Base62.decode(request.getSlug()));

        ShortLink newShortLink = new ShortLink();
        newShortLink.setDestination(request.getDestination());
        newShortLink.setSlug(request.getSlug());

        if (existingSlug.isPresent() && !existingSlug.get().getDestination().equals(request.getDestination())) {
            throw new SlugAlreadyExistsException(request.getSlug());
        }

        if (existingSlug.isPresent()) {
            newShortLink.setSlug(existingSlug.get().getSlug());
            return existingSlug.get();
        }

        return shortLinkRepository.save(newShortLink);
    }
}
