package neo4j.driver.reactive;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

import org.junit.Assert;
import neo4j.driver.reactive.impl.Neo4jReactiveSession;
import neo4j.driver.testkit.EmbeddedTestkitDriver;

public class Neo4jReactiveSessionTest 
{
	protected Session session;
	protected Driver embeddedTestkitDriver;
	
	@Before
	public void before() {
		embeddedTestkitDriver = new EmbeddedTestkitDriver();
		session = new Neo4jReactiveSession(embeddedTestkitDriver.session());
	}
	
	@Test
	public void isOpenTest(){
		Assert.assertTrue(session.isOpen());//EmbeddedTestKitSession.isOpen always true and this is a wrapper
	}
}
