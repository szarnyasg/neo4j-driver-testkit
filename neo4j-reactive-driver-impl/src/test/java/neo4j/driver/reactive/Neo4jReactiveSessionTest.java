package neo4j.driver.reactive;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

import org.junit.Assert;
import neo4j.driver.reactive.impl.Neo4jReactiveSession;
import neo4j.driver.testkit.EmbeddedTestkitDriver;

public class Neo4jReactiveSessionTest 
{
	protected Session session;
	protected Driver embeddedTestkitDriver;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void before() {
		embeddedTestkitDriver = new EmbeddedTestkitDriver();
		session = new Neo4jReactiveSession(embeddedTestkitDriver.session());
	}
	
	@Test
	public void isOpenTest(){
		Assert.assertTrue(session.isOpen());//EmbeddedTestKitSession.isOpen always true and this is a wrapper
	}
	@Test(expected=UnsupportedOperationException.class)
	public void lastBookmarkTest(){
		session.lastBookmark();//EmbeddedTestKitSession.lastBookmark always throw Exception and this is a wrapper
	}
}
