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

@RestController
@RequestMapping("/shortlinks")
public class ShortLinkController {
    @Autowired
    ShortLinkRepository shortLinkRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ShortLink createShortLink(@RequestBody ShortLinkRequest request) {
        int lastIdInserted = (int) shortLinkRepository.count();

        validateDestination(request.getDestination());

        ShortLink newShortLink = new ShortLink();
        newShortLink.setDestination(request.getDestination());
        newShortLink.setSlug(Base62.encode(lastIdInserted  + 1));

        return shortLinkRepository.save(newShortLink);
    }

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
