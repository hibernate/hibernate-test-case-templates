package org.hibernate.bugs.port.adapter.persistence.hibernate;

import org.hibernate.bugs.port.adapter.persistence.hibernate.HibernateResultListTransformer.ResultCursor;

import java.util.StringJoiner;

public final class JoinOn {

    private final String[] leftAliases;
    private Object currentLeftQualifier;

    public JoinOn(final String... leftAliases) {
        this.leftAliases = leftAliases;
    }

    boolean hasCurrentLeftQualifier(final ResultCursor resultCursor) {
        try {
            Object columnValue = this.getLeftQualifier(resultCursor);

            if (columnValue == null) {
                return false;
            }

            return columnValue.equals(this.currentLeftQualifier);

        } catch (Exception e) {
            return false;
        }
    }

    boolean isJoinedOn(final ResultCursor resultCursor) {
        String leftColumn = null;

        try {
            if (this.isSpecified()) {
                leftColumn = this.getLeftQualifier(resultCursor);
            }
        } catch (Exception e) {
            // ignore
        }

        return leftColumn != null && !leftColumn.isEmpty();
    }

    boolean isSpecified() {
        return this.leftAliases() != null && this.leftAliases().length > 0;
    }

    String[] leftAliases() {
        return this.leftAliases;
    }

    void saveCurrentLeftQualifier(final ResultCursor resultCursor) {
        try {
            this.currentLeftQualifier = this.getLeftQualifier(resultCursor);
        } catch (Exception e) {
            // ignore
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.currentLeftQualifier);
    }

    private String getLeftQualifier(final ResultCursor resultCursor) {
        final StringJoiner leftQualifier = new StringJoiner("_");

        for (String leftAlias : this.leftAliases()) {
            leftQualifier.add(String.valueOf(resultCursor.get(leftAlias)));
        }

        return leftQualifier.toString();
    }
}
