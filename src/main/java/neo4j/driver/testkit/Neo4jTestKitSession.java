package neo4j.driver.testkit;

import java.util.Map;

import org.neo4j.driver.v1.AccessMode;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Statement;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.TypeSystem;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;

import neo4j.driver.testkit.data.Neo4jTestKitStatementResult;

public class Neo4jTestKitSession implements Session {

    final GraphDatabaseService gds;

    public Neo4jTestKitSession(GraphDatabaseService gds, AccessMode mode) {
        this.gds = gds;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public StatementResult run(String statementTemplate, Value parameters) {
        return null;
    }

    @Override
    public StatementResult run(String statementTemplate, Map<String, Object> statementParameters) {
        return null;
    }

    @Override
    public StatementResult run(String statementTemplate) {
        return null;
    }

    @Override
    public StatementResult run(String statementTemplate, Record statementParameters) {
        return null;
    }

    @Override
    public StatementResult run(Statement statement) {
    	final Result internalResult = gds.execute(statement.text());
    	final Neo4jTestKitStatementResult driverResult = new Neo4jTestKitStatementResult(internalResult);

    	return driverResult;
    }

    @Override
    public TypeSystem typeSystem() {
        throw new UnsupportedOperationException("Typesystem is not supported.");
    }

    @Override
    public Transaction beginTransaction() {
        org.neo4j.graphdb.Transaction transaction = gds.beginTx();
		return new Neo4jTestKitTransaction(gds, transaction);
    }

    @Override
    public Transaction beginTransaction(String bookmark) {
        throw new UnsupportedOperationException("Bookmarks are not supported.");
    }

    @Override
    public String lastBookmark() {
        throw new UnsupportedOperationException("Bookmarks are not supported.");
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset not supported.");
    }

    @Override
    public void close() {
    }

}
