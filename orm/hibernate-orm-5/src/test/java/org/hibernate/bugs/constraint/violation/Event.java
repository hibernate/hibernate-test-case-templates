package org.hibernate.bugs.constraint.violation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Vydruth Pulluri
 * @author Shelley J. Baker
 * 
 */
@Table(name = "events_table", uniqueConstraints = { @UniqueConstraint(name = "uq1_event", columnNames = { "location", "display_name" }) })
@Entity
public class Event {

	/** Unique auto genetared id */
	@Id
	@GeneratedValue
	private Long id;

	/** Display name of the event */
	@Column(name = "display_name", nullable = false)
	private String displayName;

	/** Location where the event is happening */ 
	@Column(name = "location", nullable = false)
	private String location;

	public Event() {}

	public Event(String displayName, String location) {
		this.displayName = displayName;
		this.location = location;
	}
}
