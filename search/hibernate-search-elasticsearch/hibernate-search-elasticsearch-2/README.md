# Hibernate Test Case Templates: Hibernate Search with Elasticsearch 2

This directory contains a test case template for Hibernate Search
backed with an Elasticsearch 2.x cluster.

You can run the integration tests:
* either using the command line with: `mvn verify`: it will automatically launch an instance of Elasticsearch 2;
* or directly from your IDE. In this case, you need to run an instance of Elasticsearch separately using:
`mvn elasticsearch:run`.
