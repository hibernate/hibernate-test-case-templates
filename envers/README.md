# Hibernate Test Case Templates: Envers

This repo contains test case templates, useful for reporting bugs against Hibernate Envers.

* EnversUnitTestCase: This provides built-in unit test framework that does much of the heavy lifting for you.  All
that is required is your entities, logic, and any necessary settings.  Since we nearly always include a regression
test with bug fixes, providing your reproducer using this class simplifies that process.  We can then directly add
it, without having to mold it into our existing framework.  You're also welcomed to fork hibernate-orm itself, add
your test case direectly to the Enver's module test cases (using the template class), then submit it as a pull 
request.