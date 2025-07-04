package movwe.domains.clients.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import movwe.domains.movies.entities.Movie;
import movwe.domains.users.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "clients")
public class Client extends User {
    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Movie> movies = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "client_friends",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<Client> friends = new HashSet<>();

    @Embedded
    private Address address;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = true;
}
