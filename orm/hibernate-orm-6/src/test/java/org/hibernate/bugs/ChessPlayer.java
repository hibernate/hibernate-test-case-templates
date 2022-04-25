package org.hibernate.bugs;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity
public class ChessPlayer {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

    @OneToMany(mappedBy = "playerWhite")
    private Set<ChessGame> gamesWhite;

    @OneToMany(mappedBy = "playerBlack")
    private Set<ChessGame> gamesBlack;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public Set<ChessGame> getGamesWhite() {
        return gamesWhite;
    }

    public void setGamesWhite(Set<ChessGame> gamesWhite) {
        this.gamesWhite = gamesWhite;
    }

    public Set<ChessGame> getGamesBlack() {
        return gamesBlack;
    }

    public void setGamesBlack(Set<ChessGame> gamesBlack) {
        this.gamesBlack = gamesBlack;
    }

    public int getVersion() {
        return version;
    }

}