package neo4j.driver.reactive;

import org.neo4j.driver.v1.Driver;

/**
 * Extends Neo4j driver features with incremental features: clients can register queries
 * and get the change set caused by the latest update operation.
 */
public class ReactiveDriver {

	protected final Driver driver;

    public ReactiveDriver(Driver driver) {
    	this.driver = driver;
    }

    public ReactiveSession session() {
    	return new ReactiveSession(driver.session());
    }


}
