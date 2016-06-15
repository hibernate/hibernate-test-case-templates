# Hibernate Test Case Templates: Hibernate Search

This repo contains test case templates, useful for reporting bugs against Hibernate Search.

Here's a running list of what's available:

* hibernate-search-lucene: a test template for standard Hibernate Search based on Lucene
* hibernate-search-elasticsearch: a test template for Hibernate Search on top of Elasticsearch

Note that in this latter case, the test is an integration test and you can run it:

* either using the command line with:
```
mvn verify
```
It will automatically launch an instance of Elasticsearch.
* or directly from your IDE. In this case, you need to run an instance of Elasticsearch using:
```
mvn elasticsearch:run
```
