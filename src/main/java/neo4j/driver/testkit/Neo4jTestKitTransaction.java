package neo4j.driver.testkit;

import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Statement;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.TypeSystem;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

import neo4j.driver.testkit.data.Neo4jTestKitStatementResult;

public class Neo4jTestKitTransaction implements org.neo4j.driver.v1.Transaction {

	final GraphDatabaseService gds;
	final Transaction internalTransaction;

	public Neo4jTestKitTransaction(GraphDatabaseService gds, Transaction internalTransaction) {
		this.gds = gds;
		this.internalTransaction = internalTransaction;
	}

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public StatementResult run(String statementTemplate, Value parameters) {
    	return run(statementTemplate, parameters.asMap());
    }

    @Override
    public StatementResult run(String statementTemplate, Record statementParameters) {
        return run(statementTemplate, statementParameters.asMap());
    }

    @Override
    public StatementResult run(String statementTemplate, Map<String, Object> statementParameters) {
    	final Result internalResult = gds.execute(statementTemplate, statementParameters);
		final Neo4jTestKitStatementResult driverResult = new Neo4jTestKitStatementResult(internalResult);
		return driverResult;
    }

    @Override
    public StatementResult run(String statementTemplate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatementResult run(Statement statement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeSystem typeSystem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void success() {
        // TODO Auto-generated method stub

    }

    @Override
    public void failure() {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

}
