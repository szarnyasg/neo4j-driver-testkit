package neo4j.driver.reactive.impl;

import neo4j.driver.reactive.data.RecordChangeSet;
import neo4j.driver.reactive.interfaces.RecordChangeSetListener;

public class Neo4jRecordChangeSetListener implements RecordChangeSetListener {

	@Override
	public void notify(RecordChangeSet rcs) {
		System.out.println(rcs + " appeared.");
	}

}
