package org.hibernate.bugs;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class Resolver implements CurrentTenantIdentifierResolver<Long> {
  @Override
  public Long resolveCurrentTenantIdentifier() {
    return 0L;
  }
  @Override
  public boolean validateExistingCurrentSessions() {
    return false;
  }
}