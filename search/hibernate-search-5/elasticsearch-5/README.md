# Hibernate Test Case Templates: Hibernate Search 5 with Elasticsearch 5

This directory contains a test case template for Hibernate Search 5
backed with an Elasticsearch 5 cluster.

You can run the integration tests:
* either using the command line with: `mvn verify`: it will automatically launch an instance of Elasticsearch 5;
* or directly from your IDE. In this case, you need to run an instance of Elasticsearch separately using:
`mvn elasticsearch:runforked  -Des.setAwait=true`.
