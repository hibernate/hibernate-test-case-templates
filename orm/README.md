# Hibernate Test Case Templates: ORM

This repo contains test case templates, useful for reporting bugs against Hibernate ORM.

Here's a running list of what's available (there are two versions of each test case template, one is for Hibernate ORM 4,
one for Hibernate ORM 5):

* ORMUnitTestCase: By far, this one's the most helpful.  ORM includes a built-in unit test framework that does much
of the heavy lifting for you.  All that's required is your entities, logic, and any necessary settings.  Since we nearly
always include a regression test with bug fixes, providing your reproducer using this method simplifies the process.  We
can then directly commit it, without having to mold it in first.  What's even better?  Fork hibernate-orm itself,
add your test case directly to a module's unit tests (using the template class), then submit it as a PR!
* ORMStandaloneTestCase: This template is standalone and will look familiar.  It simply uses a run-of-the-mill ORM setup.
Although it's perfectly acceptable as a reproducer, lean towards ORMUnitTestCase whenever possible.

**For a detailed step-by-step tutorial about how you should be using our test case templates check out the [following article](http://in.relation.to/2016/01/14/hibernate-jpa-test-case-template/)**.
