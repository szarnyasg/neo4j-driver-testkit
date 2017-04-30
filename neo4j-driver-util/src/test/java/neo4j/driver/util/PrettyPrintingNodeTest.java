package neo4j.driver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.Test;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalRecord;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.internal.value.NodeValue;
import org.neo4j.driver.internal.value.RelationshipValue;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.junit.Assert;
import neo4j.driver.util.PrettyPrinter;


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
		
		Assert.assertEquals("[(:Person1 {name: \"John Doe\"}),(1)-[:REL {weight: 2}]-(2),(:Person2 {name: \"John Doe\"})]", PrettyPrinter.toString(testList));
	}
	
	@Test
	public void toStringValueTestSimpleValue(){
		Value value = Values.value(5000);
		String valueString = PrettyPrinter.toString(value);
		Assert.assertEquals(valueString,"5000");
	}
	
	@Test
	public void toStringValueTestRelationshipValue(){
		Map<String, Value> relationshipProperties = ImmutableMap.of("weight", Values.value(2));
		RelationshipValue relationshipValue = new RelationshipValue(new InternalRelationship(5, 1, 2, "REL", relationshipProperties));
		Assert.assertEquals("(1)-[:REL {weight: 2}]-(2)", PrettyPrinter.toString(relationshipValue));
	}
	
	@Test
	public void toStringValueTestNodeValue(){
		List<String> labels = ImmutableList.of("Person1");
		Map<String, Value> nodeProperties = ImmutableMap.of("name", Values.value("John Doe"));
		NodeValue nodeValue = new NodeValue(new InternalNode(1,labels,nodeProperties));
		Assert.assertEquals(PrettyPrinter.toString(nodeValue), "(:Person1 {name: \"John Doe\"})");
	}
	/*@Test
	public void toStringValuePathValueTest(){
		PathValue pathValue = new PathValue(new InternalPath());
		List<String> labels = ImmutableList.of("Person1");
		Map<String, Value> nodeProperties = ImmutableMap.of("name", Values.value("John Doe"));
		Entity entinity = new InternalNode(1, labels, nodeProperties);
		
		List<String> labels2 = ImmutableList.of("Person2");
		Map<String, Value> nodeProperties2 = ImmutableMap.of("name", Values.value("Long John Silver"));
		Entity entinity2 = new InternalNode(2, labels2, nodeProperties2);
		List<Entity> entityList = new ArrayList<>();
		entityList.add(entinity);
		entityList.add(entinity2);
		
		InternalPath path = new InternalPath(entityList);
		//TODO fix IllegalArgumentException
	}*/
	
	
	
	@Test
	public void toStringRelationshipTest(){
		Map<String, Value> relationshipProperties = ImmutableMap.of("weight", Values.value(2));
		Relationship rel = new InternalRelationship(5, 1, 2, "REL", relationshipProperties);
		System.out.println("Test: toStringRelationshipTest "+PrettyPrinter.toString(rel));
	}

	
	@Test
	public void toStringRecordTest(){
		List<String> keyList = new ArrayList<String>();
		keyList.add("Apple");
		keyList.add("Guitar");
		Value[] values = {Values.value(5000),Values.value(666)};
		Record record = new InternalRecord(keyList, values);
		
		Assert.assertEquals("<Apple=5000, Guitar=666>", PrettyPrinter.toString(record));
	}
	/*
	@Test (expected=UnsupportedOperationException.class )
	public void toStringPathTest(){
		Path p=new Path()
	}*/
	
	@Test
	public void toStringPropertiesMapTest(){
		//megnézzük, hogy a map értékeit hogy iratja ki
		Map<String, Object> nodeProperties=new HashMap<>() ;
		System.out.println("üres map-nél üres stringgel térünk vissza:" +PrettyPrinter.toString(nodeProperties));
		nodeProperties= ImmutableMap.of("name", Values.value("John Doe"));
		Assert.assertEquals("{name: \"John Doe\"}", PrettyPrinter.toString(nodeProperties));
		System.out.println("test: toStringPropertiesMapTest"+PrettyPrinter.toString(nodeProperties));

	}
	
	@Test
	public void valueTest(){
		
	}

}
