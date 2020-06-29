# Hibernate Test Case Templates: Hibernate Search

This directory contains test case templates, useful for reporting bugs against Hibernate Search.

Here's a running list of what's available:

* `hibernate-search-6`: test case templates for Hibernate Search 6
  * `hibernate-search-6/orm-lucene`: a test case template for Hibernate Search 6 in Hibernate ORM backed by an embedded Lucene instance.
  * `hibernate-search-6/orm-elasticsearch`: a test case template for Hibernate Search 6 in Hibernate ORM backed by an Elasticsearch cluster.
* `hibernate-search-5`: test case templates for Hibernate Search 5
  * `hibernate-search-5/lucene`: a test case template for Hibernate Search 5 in Hibernate ORM backed by an embedded Lucene instance.
  * `hibernate-search-5/elasticsearch-2`: a test case template for Hibernate Search 5 in Hibernate ORM backed by an Elasticsearch 2 cluster.
  * `hibernate-search-5/elasticsearch-5`: a test case template for Hibernate Search 5 in Hibernate ORM backed by an Elasticsearch 5 cluster.

Please refer to each subdirectory for instructions on how to run the tests.

Directories `hibernate-search-elasticsearch` and `hibernate-search-lucene` are remnants of
the former directory structure, only kept to redirect people following old links.

