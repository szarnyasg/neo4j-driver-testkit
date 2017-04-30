package neo4j.driver.reactive;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import static org.neo4j.driver.v1.Values.parameters;

import org.junit.Assert;
import neo4j.driver.reactive.impl.Neo4jReactiveTransaction;
import neo4j.driver.testkit.EmbeddedTestkitDriver;
import neo4j.driver.transactions.SessionDependentTransaction;
import neo4j.driver.util.PrettyPrinter;

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
		Assert.assertTrue(sessionDependentTransaction.isOpen());//EmbeddedTestKitSession.isOpen always true and this is a wrapper
	}
	@Test
	public void failureTest(){
		try  {
			sessionDependentTransaction.run("CREATE (Petra:PetraPerson {name: $name})", parameters("name", "Petra"));
			sessionDependentTransaction.failure();
			
			

			StatementResult statementResult = sessionDependentTransaction.run("MATCH (n:PetraPerson) RETURN [n]");
			if (statementResult.hasNext()) {
				Record record = statementResult.next();
				Assert.assertEquals("<[n]=[node<0>]>",PrettyPrinter.toString(record));
				//System.out.println(PrettyPrinter.toString(record));
				//System.out.println("GOOD FAILURE TEST");
			}
						
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
