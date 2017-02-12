package neo4j.driver.testkit;

import org.neo4j.driver.v1.AccessMode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

public class Neo4jTestKitDriver implements Driver {

    final GraphDatabaseService gds = new TestGraphDatabaseFactory().newImpermanentDatabase();

    public Neo4jTestKitDriver() {
    }

    @Override
    public boolean isEncrypted() {
        return false;
    }

    @Override
    public Neo4jTestKitSession session() {
        return session(AccessMode.WRITE);
    }

    @Override
    public Neo4jTestKitSession session(AccessMode mode) {
        return new Neo4jTestKitSession(gds, mode);
    }

    @Override
    public void close() {
    }

}
