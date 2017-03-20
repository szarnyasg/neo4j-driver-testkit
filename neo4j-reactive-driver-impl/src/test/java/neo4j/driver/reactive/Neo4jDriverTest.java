package neo4j.driver.reactive;

import static org.neo4j.driver.v1.Values.parameters;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;

import neo4j.driver.reactive.data.RecordChangeSet;
import neo4j.driver.reactive.impl.Neo4jReactiveDriver;
import neo4j.driver.reactive.interfaces.ReactiveDriver;
import neo4j.driver.reactive.interfaces.ReactiveSession;
import neo4j.driver.testkit.EmbeddedTestkitDriver;

public class Neo4jDriverTest {

	protected Driver embeddedTestkitDriver;
	protected ReactiveDriver driver;
	protected ReactiveSession session;

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
		System.out.println(changeSet1);
		System.out.println();

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
		System.out.println(changeSet1);
		System.out.println();

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
		System.out.println(changeSet1);
		System.out.println();

		runUpdate(session, PERSONS_QUERY, "CREATE (a:Person {name: $name})", parameters("name", "Bob"));
	}

	@Test
	public void test4() throws Exception {
		StatementResult result = session.run("RETURN [1, 2, 3]");
		System.out.println(result.next().get(0).getClass());
	}

	private void runUpdate(ReactiveSession session, final String PERSONS_QUERY, String statementTemplate,
			Value parameters) {
		System.out.println("Running query: " + statementTemplate);
		System.out.println("With parameters: " + parameters);

		try (Transaction tx = session.beginTransaction()) {
			tx.run(statementTemplate, parameters);
			tx.success();
		}

		final RecordChangeSet changeSet = session.getDeltas(PERSONS_QUERY);
		System.out.println(changeSet);
		System.out.println();
	}

}
