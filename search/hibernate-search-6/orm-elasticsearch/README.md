# Hibernate Test Case Templates: Hibernate Search 6 in Hibernate ORM with Elasticsearch

This directory contains a test case template for
Hibernate Search 6 in Hibernate ORM backed by an Elasticsearch cluster.

You can run the integration tests:
* either using the command line with: `mvn verify`: it will automatically launch an instance of Elasticsearch;
* or directly from your IDE. In this case, you need to run an instance of Elasticsearch separately using:
`mvn elasticsearch:runforked  -Des.setAwait=true`.
