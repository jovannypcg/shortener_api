package mx.jovannypcg.urlshortener.controllers;

import mx.jovannypcg.urlshortener.dao.ShortLinkRepository;
import mx.jovannypcg.urlshortener.exceptions.DestinationAlreadyExistsException;
import mx.jovannypcg.urlshortener.model.ShortLink;
import mx.jovannypcg.urlshortener.model.ShortLinkRequest;
import mx.jovannypcg.urlshortener.util.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Contains the actions to deal with short links.
 *
 * @author  Jovanny Pablo Cruz Gomez
 *          Software Engineer
 *          jovannypcg@yahoo.com
 */
@RestController
@RequestMapping("/shortlinks")
public class ShortLinksController {
    @Autowired
    ShortLinkRepository shortLinkRepository;

    /**
     * Creates a new short link.
     *
     * @param request JSON request that includes the <code>destination</code> URL.
     * @return The created ShortLink in JSON format.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ShortLink createShortLink(@RequestBody ShortLinkRequest request) {
        int lastIdInserted = (int) shortLinkRepository.count();

        validateDestination(request.getDestination());

        ShortLink newShortLink = new ShortLink();
        newShortLink.setDestination(request.getDestination());
        newShortLink.setSlug(Base62.encode(lastIdInserted  + 1));

        return shortLinkRepository.save(newShortLink);
    }

    /**
     * Validates the given destination does not exist in database.
     * If it exists, a <code>DestinationAlreadyExistsException</code> is thrown.
     *
     * @param destination Destination to validate.
     */
    private void validateDestination(String destination) {
        if (shortLinkRepository.findByDestination(destination).isPresent()) {
            throw new DestinationAlreadyExistsException(destination);
        }
    }

    @ExceptionHandler(DestinationAlreadyExistsException.class)
    void handleDestinationAlreadyExistsException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }
}
