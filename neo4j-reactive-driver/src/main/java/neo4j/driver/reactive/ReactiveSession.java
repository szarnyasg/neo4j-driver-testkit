package neo4j.driver.reactive;

import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import neo4j.driver.reactive.data.RecordChangeSet;

public interface ReactiveSession extends Session{

	StatementResult registerQuery(String queryName, String querySpecification);

	RecordChangeSet getDeltas(String queryName);

}
