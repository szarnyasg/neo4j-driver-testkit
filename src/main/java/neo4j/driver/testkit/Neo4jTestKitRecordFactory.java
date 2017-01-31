package neo4j.driver.testkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.driver.internal.InternalRecord;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;

public class Neo4jTestKitRecordFactory {

	public static Record create(Map<String, Object> element) {
		final List<String> keys = new ArrayList<>(element.size());
		final List<Value> values = new ArrayList<>(element.size());

		for (Entry<String, Object> entry : element.entrySet()) {
			keys.add(entry.getKey());
			final Value value = Values.value(entry.getValue());
			values.add(value);
		}

		return new InternalRecord(keys, values.toArray(new Value[values.size()]));
	}

}
