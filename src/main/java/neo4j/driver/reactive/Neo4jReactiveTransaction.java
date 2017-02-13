package neo4j.driver.reactive;

import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Statement;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.TypeSystem;
import org.neo4j.graphdb.Transaction;

public class Neo4jReactiveTransaction implements org.neo4j.driver.v1.Transaction {

	final Neo4jReactiveSession session;
	final Transaction internalTransaction;

	public Neo4jReactiveTransaction(Neo4jReactiveSession session, Transaction internalTransaction) {
		this.session = session;
		this.internalTransaction = internalTransaction;
	}

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public StatementResult run(String statementTemplate, Map<String, Object> statementParameters) {
        return session.run(statementTemplate, statementParameters);
    }

    @Override
    public StatementResult run(String statementTemplate, Value parameters) {
        return session.run(statementTemplate, parameters);
    }

    @Override
    public StatementResult run(String statementTemplate, Record statementParameters) {
        return session.run(statementTemplate, statementParameters);
    }

    @Override
    public StatementResult run(String statementTemplate) {
        return session.run(statementTemplate);
    }

    @Override
    public StatementResult run(Statement statement) {
        return session.run(statement.text());
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
