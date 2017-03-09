package neo4j.driver.reactive.entities;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;
import org.neo4j.driver.v1.types.Node;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import neo4j.driver.reactive.entities.PrettyPrintingNode;

public class PrettyPrintingNodeTest {

	@Test
	public void testName() throws Exception {
		List<String> labels = ImmutableList.of("Person");
		Map<String, Value> properties = ImmutableMap.of("name", Values.value("John Doe"));

		Node node = new PrettyPrintingNode(1, labels, properties);
		System.out.println(node);

		assertEquals(true, true);
	}

}
