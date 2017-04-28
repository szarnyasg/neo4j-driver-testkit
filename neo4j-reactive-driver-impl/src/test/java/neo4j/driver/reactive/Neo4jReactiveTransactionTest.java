package neo4j.driver.reactive;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;

import org.junit.Assert;
import neo4j.driver.reactive.impl.Neo4jReactiveTransaction;
import neo4j.driver.testkit.EmbeddedTestkitDriver;
import neo4j.driver.transactions.SessionDependentTransaction;

public class Neo4jReactiveTransactionTest {
	
	protected SessionDependentTransaction<org.neo4j.driver.v1.Transaction> sessionDependentTransaction;
	protected Session session;
	protected Transaction internalTransaction;
	protected Driver embeddedTestkitDriver;
	
	@Before
	public void before() {
		embeddedTestkitDriver = new EmbeddedTestkitDriver();
		session = embeddedTestkitDriver.session();
		internalTransaction = session.beginTransaction();
		sessionDependentTransaction = new Neo4jReactiveTransaction(session, internalTransaction);
	}
	@Test
	public void isOpenTest(){
		Assert.assertTrue(sessionDependentTransaction.isOpen());
	}
}
