package mx.jovannypcg.urlshortener.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a short link in database.
 */
@Entity
public class ShortLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String slug;
    private String destination;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
