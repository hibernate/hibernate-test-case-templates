package org.hibernate.bugs;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TimeZoneStorageExample {
    
    @Id
    @GeneratedValue
    private Long id;

    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE_UTC)
    private ZonedDateTime zonedDateTimeUtc;

    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE_UTC)
    private OffsetDateTime offsetDateTimeUtc;

    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
    private ZonedDateTime zonedDateTimeLocal;

    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
    private OffsetDateTime offsetDateTimeLocal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getZonedDateTimeUtc() {
        return zonedDateTimeUtc;
    }

    public void setZonedDateTimeUtc(ZonedDateTime zonedDateTimeUtc) {
        this.zonedDateTimeUtc = zonedDateTimeUtc;
    }

    public OffsetDateTime getOffsetDateTimeUtc() {
        return offsetDateTimeUtc;
    }

    public void setOffsetDateTimeUtc(OffsetDateTime offsetDateTimeUtc) {
        this.offsetDateTimeUtc = offsetDateTimeUtc;
    }

    public ZonedDateTime getZonedDateTimeLocal() {
        return zonedDateTimeLocal;
    }

    public void setZonedDateTimeLocal(ZonedDateTime zonedDateTimeLocal) {
        this.zonedDateTimeLocal = zonedDateTimeLocal;
    }

    public OffsetDateTime getOffsetDateTimeLocal() {
        return offsetDateTimeLocal;
    }

    public void setOffsetDateTimeLocal(OffsetDateTime offsetDateTimeLocal) {
        this.offsetDateTimeLocal = offsetDateTimeLocal;
    }
}
