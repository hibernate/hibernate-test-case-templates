package org.hibernate.bugs;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPAUnitTestCase {

  private static final LocalDate DATE = LocalDate.of(2023, 10, 18);
  private static final Currency CURRENCY = Currency.getInstance("EUR");

  private EntityManagerFactory entityManagerFactory;

  @Before
  public void init() {
    entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
  }

  @After
  public void destroy() {
    entityManagerFactory.close();
  }

  @Test
  public void shouldProvideAllParameters() {
    EntityManager manager = entityManagerFactory.createEntityManager();
    manager.getTransaction().begin();
    Company parent = new Company();
    parent.add(
      new MarketData(
        DATE,
        new Amount(1., CURRENCY),
        new Amount(1000., CURRENCY)
      )
    );
    manager.persist(parent);
    manager.getTransaction().commit();

    manager.getTransaction().begin();
    parent.add(
      new MarketData(
        DATE,
        new Amount(10., CURRENCY),
        new Amount(10000., CURRENCY)
      )
    );
    manager.getTransaction().commit();
    manager.close();
  }

  @Entity
  private static class Company {

    @Id
    private final UUID id;

    @ElementCollection
    @MapKeyColumn(name = "date", insertable = false, updatable = false)
    private final Map<LocalDate, MarketData> data = new HashMap<>();

    public Company() {
      id = UUID.randomUUID();
    }

    public void add(MarketData data) {
      this.data.put(data.getDate(), data);
    }
  }

  @Embeddable
  private static class MarketData {

    private LocalDate date;

    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Amount price;

    @AttributeOverrides(
      // prettier-ignore
      {
        @AttributeOverride(name = "value", column = @Column(name = "capitalization")),
        @AttributeOverride(
          name = "currency",
          column = @Column(name = "currency", insertable = false, updatable = false)
        ),
      }
    )
    private Amount capitalization;

    protected MarketData() {}

    public MarketData(LocalDate date, Amount price, Amount capitalization) {
      this.date = date;
      this.price = price;
      this.capitalization = capitalization;
    }

    public LocalDate getDate() {
      return date;
    }
  }

  @Embeddable
  private static class Amount {

    private Double value;
    private Currency currency;

    protected Amount() {}

    public Amount(Double value, Currency currency) {
      this.value = value;
      this.currency = currency;
    }
  }
}
