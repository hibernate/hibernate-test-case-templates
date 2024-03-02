package org.hibernate.bugs.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
public class UserEntity {

	@Id private UUID userId;
	@JdbcTypeCode(SqlTypes.JSON)
	private UserSettings rules;
}