package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.NotFound;

import java.util.Objects;

import static org.hibernate.annotations.NotFoundAction.IGNORE;

@Entity
@Table(name = "ITEM_WITH_IGNORE")
@NamedEntityGraph(
        name = "ItemWithIgnore.fetch",
        attributeNodes = @NamedAttributeNode("unit")
)
public class ItemWithIgnore {
    public ItemWithIgnore() {
    }

    public ItemWithIgnore(Long itemId) {
        this.itemId = itemId;
    }

    public ItemWithIgnore(Long itemId, UnitReferenceCode unit) {
        this.itemId = itemId;
        this.unit = unit;
    }

    @Id
    @Column(name = "ITEM_ID")
    private Long itemId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = IGNORE)
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(formula = @JoinFormula(value = "'" + UnitReferenceCode.DOMAIN + "'", referencedColumnName = "domain")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "ITEM_TYPE", referencedColumnName = "code", insertable = false, updatable = false))
    })
    private UnitReferenceCode unit;

    public UnitReferenceCode getUnit() {
        return unit;
    }

    public void setUnit(UnitReferenceCode unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final ItemWithIgnore that = (ItemWithIgnore) o;
        return Objects.equals(getItemId(), that.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getItemId());
    }
}
