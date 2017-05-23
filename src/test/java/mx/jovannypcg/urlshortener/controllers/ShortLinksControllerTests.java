package mx.jovannypcg.urlshortener.controllers;

import mx.jovannypcg.urlshortener.dao.ShortLinkRepository;
import mx.jovannypcg.urlshortener.exceptions.DestinationAlreadyExistsException;
import mx.jovannypcg.urlshortener.model.ShortLink;
import mx.jovannypcg.urlshortener.model.ShortLinkRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortLinksControllerTests {
    @Autowired
    private ShortLinksController controller;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ShortLinkRepository shortLinkRepository;

    @Mock ShortLink mockShortLink;
    private ShortLink fakeShortLink;
    private String fakeDestination;
    private ShortLinkRequest fakeRequest;

    @Before
    public void init() {
        fakeDestination = "http://www.aws.com";

        fakeRequest = new ShortLinkRequest();
        fakeRequest.setDestination(fakeDestination);

        fakeShortLink = new ShortLink();
        fakeShortLink.setId(1000);
        fakeShortLink.setSlug("Dvw1Z");
        fakeShortLink.setDestination(fakeDestination);
    }

    @Test(expected = DestinationAlreadyExistsException.class)
    public void createShortLinkShouldThrowException() {
        when(shortLinkRepository.findByDestination(anyString())).thenReturn(Optional.of(fakeShortLink));

        controller.createShortLink(fakeRequest);
    }
}
