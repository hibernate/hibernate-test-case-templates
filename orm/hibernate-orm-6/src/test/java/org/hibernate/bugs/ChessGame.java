package org.hibernate.bugs;

import java.time.LocalDate;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class ChessGame {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.EXCEPTION)
    private ChessPlayer playerWhite;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private ChessPlayer playerBlack;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public ChessPlayer getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(ChessPlayer playerWhite) {
        this.playerWhite = playerWhite;
    }

    public ChessPlayer getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(ChessPlayer playerBlack) {
        this.playerBlack = playerBlack;
    }
}