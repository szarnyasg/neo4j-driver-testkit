package neo4j.driver.reactive;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import org.junit.Assert;
import neo4j.driver.reactive.impl.Neo4jReactiveSession;
import neo4j.driver.testkit.EmbeddedTestkitDriver;
import neo4j.driver.util.PrettyPrinter;

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
	public void lastBookmarkTest1(){
		
	
		session.lastBookmark();//EmbeddedTestKitSession.lastBookmark always throw Exception and this is a wrapper
		
	}
	
	
	
	
	@Test
	public void run1Test(){
		
		Transaction transaction = session.beginTransaction();
		session.run("CREATE (n:Label)");
		transaction.success();
		StatementResult statementResult = session.run("MATCH (n:Label) RETURN n");
		while (statementResult.hasNext()) {
			Record record = statementResult.next();
			System.out.println(PrettyPrinter.toString(record));
			System.out.println("GOOD run(String s) test");
		}
	}
	
	@Test
	public void isEncryptedTest(){
		//We always get false from this method
		Assert.assertFalse(embeddedTestkitDriver.isEncrypted());
	}
}
