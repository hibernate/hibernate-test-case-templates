package org.hibernate.bugs;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(UnitReferenceCode.DOMAIN)
public class UnitReferenceCode extends ReferenceCode {
    public UnitReferenceCode() {
    }

    public UnitReferenceCode(String code) {
        super(DOMAIN, code);
    }

    public static final String DOMAIN = "UNIT";
}
