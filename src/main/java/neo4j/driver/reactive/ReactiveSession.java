package neo4j.driver.reactive;

import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Statement;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.TypeSystem;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import neo4j.driver.reactive.data.RecordChangeSet;

public class ReactiveSession implements Session {

	final Session session;

	final Map<String, String> querySpecifications = Maps.newHashMap();
	final Map<String, Multiset<Record>> queryResults = Maps.newHashMap();
	final Map<String, Multiset<Record>> deltas = Maps.newHashMap();

	public ReactiveSession(Session session) {
		super();
		this.session = session;
	}

	public RecordChangeSet registerQuery(String queryName, String querySpecification) {
		if (querySpecifications.containsKey(queryName)) {
			throw new IllegalStateException("Query " + queryName + " is already registered.");
		}

		querySpecifications.put(queryName, querySpecification);
		queryResults.put(queryName, HashMultiset.create());
		return getDeltas(queryName);
	}

	public RecordChangeSet getDeltas(String queryName) {
		final String querySpecification = querySpecifications.get(queryName);

		final Multiset<Record> currentResults = queryResults.get(queryName);
		final Multiset<Record> newResults = HashMultiset.create();

		try (Transaction tx = beginTransaction()) {
			final StatementResult statementResult = session.run(querySpecification);
			while (statementResult.hasNext()) {
				final Record record = statementResult.next();
				newResults.add(record);
			}
		}

		final Multiset<Record> positiveChanges = Multisets.difference(newResults, currentResults);
		final Multiset<Record> negativeChanges = Multisets.difference(currentResults, newResults);

		queryResults.put(queryName, newResults);
		System.out.println("current> " + currentResults);
		System.out.println("new....> " + newResults);

		return new RecordChangeSet(positiveChanges, negativeChanges);
	}

	@Override
	public boolean isOpen() {
		return session.isOpen();
	}

	@Override
	public StatementResult run(String statementTemplate, Value parameters) {
		return session.run(statementTemplate, parameters);
	}

	@Override
	public StatementResult run(String statementTemplate, Map<String, Object> statementParameters) {
		return session.run(statementTemplate, statementParameters);
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
		return session.run(statement);
	}

	@Override
	public TypeSystem typeSystem() {
		return session.typeSystem();
	}

	@Override
	public Transaction beginTransaction() {
		return session.beginTransaction();
	}

	@Override
	public Transaction beginTransaction(String bookmark) {
		return session.beginTransaction(bookmark);
	}

	@Override
	public String lastBookmark() {
		return session.lastBookmark();
	}

	@Override
	public void reset() {
		session.reset();
	}

	@Override
	public void close() {
		session.close();
	}

}
