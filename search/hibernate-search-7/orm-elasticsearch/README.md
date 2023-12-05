# Hibernate Test Case Templates: Hibernate Search 7 in Hibernate ORM with Elasticsearch

This directory contains a test case template for
Hibernate Search 7 in Hibernate ORM backed by an Elasticsearch cluster.

You can run the integration tests:
* either using the command line with: `mvn verify`;
* or directly from your IDE. 

Make sure to have `docker` or `podman` installed,
as the test will automatically start an Elasticsearch container using testcontainers.
See `SearchTestBase` for details or to change the version of Elasticsearch to test against.