package neo4j.driver.reactive;

import java.io.File;

import org.neo4j.driver.v1.AccessMode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.test.TestGraphDatabaseFactory;

/**
 * Extends Neo4j driver features with incremental features: clients can register queries
 * and get the change set caused by the latest update operation.
 */
public class Neo4jReactiveDriver implements Driver {

    final GraphDatabaseService gds;

    public Neo4jReactiveDriver() {
    	gds = new TestGraphDatabaseFactory().newImpermanentDatabase();
    }

    public Neo4jReactiveDriver(final File storeDir) {
    	gds = new GraphDatabaseFactory().newEmbeddedDatabase(storeDir);
    }

    @Override
    public boolean isEncrypted() {
        return false;
    }

    @Override
    public Neo4jReactiveSession session() {
        return session(AccessMode.WRITE);
    }

    @Override
    public Neo4jReactiveSession session(AccessMode mode) {
        return new Neo4jReactiveSession(gds, mode);
    }

    @Override
    public void close() {
    }

}
