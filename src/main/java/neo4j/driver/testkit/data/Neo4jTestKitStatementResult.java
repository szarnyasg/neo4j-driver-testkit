package neo4j.driver.testkit.data;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.NoSuchRecordException;
import org.neo4j.driver.v1.summary.ResultSummary;
import org.neo4j.driver.v1.util.Function;
import org.neo4j.graphdb.Result;

import neo4j.driver.testkit.Neo4jTestKitRecordFactory;

public class Neo4jTestKitStatementResult implements StatementResult {

	final Result result;

	public Neo4jTestKitStatementResult(final Result result) {
		this.result = result;
	}

	@Override
	public List<String> keys() {
		return null;
	}

	@Override
	public boolean hasNext() {
		return result.hasNext();
	}

	@Override
	public Record next() {
		final Map<String, Object> element = result.next();
		return Neo4jTestKitRecordFactory.create(element);
	}

	@Override
	public Record single() throws NoSuchRecordException {
		if (result.hasNext()) {
			return next();
		} else {
			throw new NoSuchRecordException("Result is empty");
		}
	}

	@Override
	public Record peek() {
		throw new UnsupportedOperationException("Peek not supported");
	}

	@Override
	public List<Record> list() {
		return null;
	}

	@Override
	public <T> List<T> list(Function<Record, T> mapFunction) {
		return null;
	}

	@Override
	public ResultSummary consume() {
		return null;
	}

	@Override
	public ResultSummary summary() {
		return null;
	}

}
