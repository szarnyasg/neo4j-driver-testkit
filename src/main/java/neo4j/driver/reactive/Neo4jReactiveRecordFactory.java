package neo4j.driver.reactive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalRecord;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;
import org.neo4j.kernel.impl.core.NodeProxy;

public class Neo4jReactiveRecordFactory {

    public static Record create(Map<String, Object> element) {
        final List<String> keys = new ArrayList<>(element.size());
        final List<Value> values = new ArrayList<>(element.size());

        for (Entry<String, Object> entry : element.entrySet()) {
            keys.add(entry.getKey());
            final Value value = convert(entry.getValue());
            values.add(value);
        }

        return new InternalRecord(keys, values.toArray(new Value[values.size()]));
    }

    private static Value convert(Object value) {
        final Object myValue;
        if (value instanceof NodeProxy) {
            final NodeProxy nodeProxy = (NodeProxy) value;

            final long id = nodeProxy.getId();
            final List<String> labels = StreamSupport.stream(nodeProxy.getLabels().spliterator(), false)
                    .map(x -> x.toString()).collect(Collectors.toList());

            final Map<String, Value> properties = new HashMap<>();
            for (final Map.Entry<String, Object> entry : nodeProxy.getAllProperties().entrySet()) {
                properties.put(entry.getKey(), convert(entry.getValue()));
            }

            myValue = new InternalNode(id, labels, properties);
        } else {
            myValue = value;
        }

        return Values.value(myValue);
    }

}
