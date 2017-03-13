package neo4j.driver.testkit;

import java.util.Map;

import org.neo4j.driver.internal.types.InternalTypeSystem;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Statement;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.TypeSystem;
import org.neo4j.graphdb.Transaction;

public class EmbeddedTestkitTransaction implements org.neo4j.driver.v1.Transaction {

	final EmbeddedTestkitSession session;
	final Transaction internalTransaction;

	public EmbeddedTestkitTransaction(EmbeddedTestkitSession session, Transaction internalTransaction) {
		this.session = session;
		this.internalTransaction = internalTransaction;
	}

    @Override
    public boolean isOpen() {
        throw new UnsupportedOperationException();
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
    	return InternalTypeSystem.TYPE_SYSTEM;
    }

    @Override
    public void success() {
    	internalTransaction.success();
    }

    @Override
    public void failure() {
        internalTransaction.failure();
    }

    @Override
    public void close() {
    	internalTransaction.close();
    }

}
