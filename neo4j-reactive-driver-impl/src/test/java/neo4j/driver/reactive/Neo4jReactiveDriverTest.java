package neo4j.driver.reactive;

import static org.neo4j.driver.v1.Values.parameters;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;

import neo4j.driver.reactive.data.RecordChangeSet;
import neo4j.driver.reactive.impl.Neo4jReactiveDriver;
import neo4j.driver.reactive.interfaces.ReactiveDriver;
import neo4j.driver.reactive.interfaces.ReactiveSession;
import neo4j.driver.testkit.EmbeddedTestkitDriver;

public class Neo4jReactiveDriverTest {

	protected Driver embeddedTestkitDriver;
	protected ReactiveDriver driver;
	protected ReactiveSession session;

	private void runUpdate(ReactiveSession session, String query, String statementTemplate,
			Value parameters) {
		System.out.println("Running query: " + statementTemplate);
		System.out.println("With parameters: " + parameters);

		try (Transaction tx = session.beginTransaction()) {
			tx.run(statementTemplate, parameters);
			tx.success();
		}
	}

	@Before
	public void before() {
		embeddedTestkitDriver = new EmbeddedTestkitDriver();
		driver = new Neo4jReactiveDriver(embeddedTestkitDriver);
		session = driver.session();
	}

	@Test
	public void test1() throws Exception {
		final String PERSONS_QUERY = "persons";

		session.registerQuery(PERSONS_QUERY, "MATCH (a:Person) RETURN a");
		final RecordChangeSet changeSet1 = session.getDeltas(PERSONS_QUERY);

		runUpdate(session, PERSONS_QUERY, "CREATE (a:Person {name: $name, title: $title})",
				parameters("name", "Arthur", "title", "King"));
		runUpdate(session, PERSONS_QUERY, "CREATE (a:Person {name: $name, title: $title})",
				parameters("name", "Arthur", "title", "King"));
		runUpdate(session, PERSONS_QUERY, "MATCH (a:Person {name: $name, title: $title}) DELETE a",
				parameters("name", "Arthur", "title", "King"));
	}

	@Test
	public void test2() throws Exception {
		final String PERSONS_QUERY = "persons";

		session.registerQuery(PERSONS_QUERY, "MATCH (a:Person) RETURN a");
		final RecordChangeSet changeSet1 = session.getDeltas(PERSONS_QUERY);

		runUpdate(session, PERSONS_QUERY, "CREATE (a:Person {name: $name})", parameters("name", "Alice"));
		runUpdate(session, PERSONS_QUERY, "CREATE (a:Person {name: $name})", parameters("name", "Bob"));
	}

	@Test
	public void test3() throws Exception {
		try (Transaction tx = session.beginTransaction()) {
			tx.run("CREATE (a:Person {name: $name})", parameters("name", "Alice"));
			tx.success();
		}

		final String PERSONS_QUERY = "persons";

		session.registerQuery(PERSONS_QUERY, "MATCH (a:Person) RETURN a");
		final RecordChangeSet changeSet1 = session.getDeltas(PERSONS_QUERY);

		runUpdate(session, PERSONS_QUERY, "CREATE (a:Person {name: $name})", parameters("name", "Bob"));
	}

}
