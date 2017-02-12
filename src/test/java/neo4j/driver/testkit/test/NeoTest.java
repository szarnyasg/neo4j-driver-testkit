package neo4j.driver.testkit.test;

import org.junit.Test;
import org.neo4j.driver.internal.InternalRecord;
import org.neo4j.driver.v1.Value;

import com.google.common.collect.ImmutableList;

public class NeoTest {

	@Test
	public void x() {
		ImmutableList<String> keys = ImmutableList.of("a", "b");
		Value[] values = new Value[] {};
		InternalRecord internalRecord = new InternalRecord(keys, values);
		System.out.println(internalRecord.hashCode());
	}

}
