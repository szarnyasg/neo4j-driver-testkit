package neo4j.driver.reactive;

import java.util.Collections;
import java.util.HashMap;
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

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import neo4j.driver.reactive.data.ChangeSet;
import neo4j.driver.reactive.data.Neo4jReactiveStatementResult;

public class Neo4jReactiveSession implements Session {

    final GraphDatabaseService gds;
    final Map<String, String> querySpecifications = new HashMap<>();
    final Map<String, Multiset<Record>> queryResults = new HashMap<>();
    final Map<String, Multiset<Record>> deltas = new HashMap<>();

    public Neo4jReactiveSession(GraphDatabaseService gds, AccessMode mode) {
        this.gds = gds;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public StatementResult run(String statementTemplate, Map<String, Object> statementParameters) {
        final Result internalResult = gds.execute(statementTemplate, statementParameters);
        final Neo4jReactiveStatementResult driverResult = new Neo4jReactiveStatementResult(internalResult);
        return driverResult;
    }

    @Override
    public StatementResult run(String statementTemplate) {
        return run(statementTemplate, Collections.emptyMap());
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
    public StatementResult run(Statement statement) {
    	final Result internalResult = gds.execute(statement.text());
    	final Neo4jReactiveStatementResult driverResult = new Neo4jReactiveStatementResult(internalResult);

    	return driverResult;
    }

    @Override
    public TypeSystem typeSystem() {
        throw new UnsupportedOperationException("Typesystem is not supported.");
    }

    @Override
    public Transaction beginTransaction() {
        org.neo4j.graphdb.Transaction transaction = gds.beginTx();
		return new Neo4jReactiveTransaction(this, transaction);
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

    public ChangeSet registerQuery(String queryName, String querySpecification) {
    	if (querySpecifications.containsKey(queryName)) {
    		throw new IllegalStateException("Query " + queryName + " is already registered.");
    	}

    	querySpecifications.put(queryName, querySpecification);
    	queryResults.put(queryName, HashMultiset.create());
    	return getDeltas(queryName);
    }

    public ChangeSet getDeltas(String queryName) {
    	final String querySpecification = querySpecifications.get(queryName);

    	final StatementResult statementResult = run(querySpecification);

    	final Multiset<Record> currentResults = queryResults.get(queryName);
    	final Multiset<Record> newResults = HashMultiset.create();
    	while (statementResult.hasNext()) {
    		final Record record = statementResult.next();
    		newResults.add(record);
    	}

    	final Multiset<Record> positiveChanges = Multisets.difference(newResults, currentResults);
    	final Multiset<Record> negativeChanges = Multisets.difference(currentResults, newResults);

    	queryResults.put(queryName, newResults);
    	System.out.println("current> " + currentResults);
    	System.out.println("  new  > " + newResults);

    	return new ChangeSet(positiveChanges, negativeChanges);
    }

}
