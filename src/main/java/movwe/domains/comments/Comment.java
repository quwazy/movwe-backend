package movwe.domains.comments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import movwe.domains.clients.entities.Client;
import movwe.domains.movies.entities.Movie;

import java.io.Serializable;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private Movie movies;

    @ManyToOne
    private Client client;
}
