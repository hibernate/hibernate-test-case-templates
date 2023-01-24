package org.hibernate.bugs.entities.user;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.bugs.entities.common.BaseEntity;

@Entity
@Table(name = "T_USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class User extends BaseEntity<UserId> {

    @EmbeddedId
    private final UserId userId;

    @Version
    private int version;

    protected User() {
        this.userId = null;
    }

    public User(UserId userId) {
        this.userId = userId;
    }

    @Override
    public UserId getId() {
        return this.userId;
    }

}
