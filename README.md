# neo4j-reactive-driver

[![Build Status](https://travis-ci.org/szarnyasg/neo4j-reactive-driver.svg?branch=master)](https://travis-ci.org/szarnyasg/neo4j-reactive-driver)

A driver that provides reactive (or incremental) change notifications

The driver can operator with two Neo4j backends:

1. [`ImpermanentGraphDatabase`](https://github.com/neo4j/neo4j/blob/3.2/community/kernel/src/test/java/org/neo4j/test/ImpermanentGraphDatabase.java) (default)
2. [`EmbeddedGraphDatabase`](https://github.com/neo4j/neo4j/blob/3.2/community/kernel/src/main/java/org/neo4j/kernel/internal/EmbeddedGraphDatabase.java) (if the client specifies the `storeDir` parameter)
