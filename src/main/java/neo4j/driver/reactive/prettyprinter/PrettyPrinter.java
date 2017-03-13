package neo4j.driver.reactive.prettyprinter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;

import com.google.common.base.Joiner;

public class PrettyPrinter {

	public static String toString(Node node) {
		Joiner labelJoiner = Joiner.on("");
		List<String> formattedLabelList = StreamSupport //
				.stream(node.labels().spliterator(), false) //
				.map(label -> ":" + label) //
				.collect(Collectors.toList());
		String labelsString = labelJoiner.join(formattedLabelList);

		String propertiesString = toString(node.asMap());

		return String.format( //
				"(%s%s%s)", //
				labelsString, //
				labelsString.isEmpty() || propertiesString.isEmpty() ? "" : " ", //
				propertiesString);
	}

	public static String toString(Relationship relationship) {
		String propertiesString = toString(relationship.asMap());

		return String.format("[:%s%s%s]", relationship.type(), //
				propertiesString.isEmpty() ? "" : " ", //
				propertiesString);
	}

	private static String toString(Map<String, Object> propertiesMap) {
		if (propertiesMap.isEmpty()) {
			return "";
		} else {
			Joiner propertyJoiner = Joiner.on(", ");

			List<String> formattedPropertyList = propertiesMap //
					.entrySet() //
					.stream() //
					.map(entry -> entry.getKey() + ": " + format(entry.getValue())) //
					.collect(Collectors.toList());
			return "{" + propertyJoiner.join(formattedPropertyList) + "}";
		}
	}

	private static String format(Object value) {
		if (value instanceof String) {
			return "\"" + value + "\"";
		} else {
			return value.toString();
		}
	}

}
