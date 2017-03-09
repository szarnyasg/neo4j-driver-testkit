package neo4j.driver.reactive.entities;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.Value;

import com.google.common.base.Joiner;

public class PrettyPrintingNode extends InternalNode {

	public PrettyPrintingNode(long id) {
		super(id);
	}

	public PrettyPrintingNode(long id, Collection<String> labels, Map<String, Value> properties) {
		super(id, labels, properties);
	}

	@Override
	public String toString() {
		Joiner labelJoiner = Joiner.on("");
		List<String> formattedLabelList = labels() //
				.stream() //
				.map(label -> ":" + label) //
				.collect(Collectors.toList());
		String labels = labelJoiner.join(formattedLabelList);

		Set<Entry<String, Object>> entrySet = asMap().entrySet();

		String properties;
		if (entrySet.isEmpty()) {
			properties = "";
		} else {
			Joiner propertyJoiner = Joiner.on(", ");

			List<String> formattedPropertyList = entrySet //
					.stream() //
					.map(entry -> entry.getKey() + ": " + format(entry.getValue())) //
					.collect(Collectors.toList());
			properties = "{" + propertyJoiner.join(formattedPropertyList) + "}";
		}

		String string = String.format( //
				"(%s%s%s)", //
				labels, //
				labels.isEmpty() || properties.isEmpty() ? "" : " ", //
				properties);
		return string;
	}

	private String format(Object value) {
		if (value instanceof String) {
			return "\"" + value + "\"";
		} else {
			return value.toString();
		}
	}

}
