package neo4j.driver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.parser.Entity;

import org.junit.Test;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.v1.AccessMode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


import neo4j.driver.util.PrettyPrinter;
import org.neo4j.driver.*;


public class PrettyPrintingNodeTest {

	@Test
	public void testName() throws Exception {
		List<String> labels = ImmutableList.of("Person");
		Map<String, Value> nodeProperties = ImmutableMap.of("", Values.value("John Doe"));
		Node node = new InternalNode(1, labels, nodeProperties);
		System.out.println(PrettyPrinter.toString(node));

		Map<String, Value> relationshipProperties = ImmutableMap.of("", Values.value(2));
		Relationship rel = new InternalRelationship(5, 1, 2, "REL", relationshipProperties);
		System.out.println(PrettyPrinter.toString(rel));
	}
	
	@Test
	public void toStringListTest(){
		List<String> labels = ImmutableList.of("Person1");
		List<String> labels2 = ImmutableList.of("Person2");
		Map<String, Value> relationshipProperties = ImmutableMap.of("weight", Values.value(2));
		Map<String, Value> nodeProperties = ImmutableMap.of("name", Values.value("John Doe"));
		List<org.neo4j.driver.v1.types.Entity> testList=new ArrayList<>();
		testList.add(new InternalNode(1,labels,nodeProperties));
		testList.add(new InternalRelationship(5, 1, 2, "REL", relationshipProperties));
		testList.add(new InternalNode(1,labels2,nodeProperties));
		System.out.println("Test: toStringListTest "+PrettyPrinter.toString(testList));
	}
	
	@Test
	public void toStringRelationshipTest(){
		Map<String, Value> relationshipProperties = ImmutableMap.of("weight", Values.value(2));
		Relationship rel = new InternalRelationship(5, 1, 2, "REL", relationshipProperties);
		System.out.println("Test: toStringRelationshipTest "+PrettyPrinter.toString(rel));
	}
	
	/*@Test
	public void toStringRecordTest(){
		
		Driver driver = new Driver() ;
			
	
			Session session = driver.session();
				Transaction transaction = session.beginTransaction();
					session.run("CREATE (n:Label)");
					StatementResult statementResult = session.run("RETURN [1, 2] AS list");
					while (statementResult.hasNext()) {
						Record record = statementResult.next();
						System.out.println(PrettyPrinter.toString(record));
					}
		
		}
	
	@Test (expected=UnsupportedOperationException.class )
	public void toStringPathTest(){
		Path p=new Path()
	}*/
	
	@Test
	public void toStringPropertiesMapTest(){
		Map<String, Object> nodeProperties=new HashMap<>() ;
		System.out.println("üres map-nél üres stringgel térünk vissza:" +PrettyPrinter.toString(nodeProperties));
		nodeProperties= ImmutableMap.of("name", Values.value("John Doe"));
		System.out.println("test: toStringPropertiesMapTest"+PrettyPrinter.toString(nodeProperties));

	}
	
	@Test
	public void valueTest(){
		
	}

}
