I've added two files, ./ordered.txt and ./unordered.txt which show two explain
plans.  These plans are actually for the same query.  The only difference is the
number of rows in the tables.  For the ordered explain plan, the query is
returning the results ordered by subscription_id and Hibernate behaves as
expected.  For the unordered explain plan, the rows are in no particular order
and Hibernate returns duplicate objects.

```sql
select
    s1_0.start_date,
    s1_0.subscription_id,
    s1_0.account_number,
    s1_0.billing_account_id,
    s1_0.billing_provider,
    s1_0.billing_provider_id,
    s1_0.end_date,
    o1_0.sku,
    o1_0.cores,
    o1_0.derived_sku,
    o1_0.description,
    o1_0.has_unlimited_usage,
    o1_0.hypervisor_cores,
    o1_0.hypervisor_sockets,
    o1_0.product_family,
    p1_0.sku,
    p1_0.oid,
    o1_0.product_name,
    o1_0.role,
    o1_0.sla,
    o1_0.sockets,
    o1_0.usage,
    s1_0.org_id,
    s1_0.quantity,
    s2_0.start_date,
    s2_0.subscription_id,
    s2_0.measurement_type,
    s2_0.metric_id,
    s2_0.value,
    s1_0.subscription_number,
    s4_0.start_date,
    s4_0.subscription_id,
    s4_0.product_id
from
    subscription s1_0
left join
    offering o1_0
        on o1_0.sku=s1_0.sku
left join
    sku_oid p1_0
        on o1_0.sku=p1_0.sku
left join
    subscription_measurements s2_0
        on s1_0.start_date=s2_0.start_date
        and s1_0.subscription_id=s2_0.subscription_id
left join
    subscription_product_ids s4_0
        on s1_0.start_date=s4_0.start_date
        and s1_0.subscription_id=s4_0.subscription_id
where
        s1_0.org_id='16775631';
```
