# Hibernate Test Case Templates: Reactive

This repo contains test case templates, useful for reporting bugs
against [Hibernate Reactive](https://hibernate.org/reactive/).

The template contains examples of tests written using both the Mutiny and Stage API.
The bug report can be written either with one or the other.

The database is started via [Testcontainers](https://java.testcontainers.org/).
It's possible to select a different database by changing the value of the constant `SELECT_DB`.

The package [org.hibernate.reactive.util](src/test/java/org/hibernate/reactive/util) contains:

* `ConnectionUrl`: The JDBC urls to connect to databases started following the instructions in the
  [PODMAN.md](https://github.com/hibernate/hibernate-reactive/blob/main/podman.md) file
  in the [Hibernate Reactive GitHub repository](https://github.com/hibernate/hibernate-reactive)
* `Database`: Containers configuration via [Testcontainers](https://java.testcontainers.org/)
* `DockerImage`: Utility class for the creation of a `DockerImageName` to use with Testcontainers
