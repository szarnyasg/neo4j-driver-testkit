package neo4j.driver.reactive.test;

import static org.neo4j.driver.v1.Values.parameters;

import org.junit.Test;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;

import neo4j.driver.reactive.Neo4jReactiveDriver;
import neo4j.driver.reactive.Neo4jReactiveSession;
import neo4j.driver.reactive.data.ChangeSet;

public class Neo4jDriverTest {

	@Test
	public void test1() throws Exception {
		try (Neo4jReactiveDriver driver = new Neo4jReactiveDriver()) {
			try (Neo4jReactiveSession session = driver.session()) {
				final String PERSONS_QUERY = "persons";

				final ChangeSet changeSet1 = session.registerQuery(PERSONS_QUERY, "MATCH (a:Person) RETURN a");
				System.out.println(changeSet1);
				System.out.println();

				runUpdate(session, PERSONS_QUERY, "CREATE (a:Person {name: $name, title: $title})",         parameters("name", "Arthur", "title", "King"));
				runUpdate(session, PERSONS_QUERY, "CREATE (a:Person {name: $name, title: $title})",         parameters("name", "Arthur", "title", "King"));
				runUpdate(session, PERSONS_QUERY, "MATCH (a:Person {name: $name, title: $title}) DELETE a", parameters("name", "Arthur", "title", "King"));
			}
		}
	}

	private void runUpdate(Neo4jReactiveSession session, final String PERSONS_QUERY, String statementTemplate,
			Value parameters) {
		System.out.println("Running query: " + statementTemplate);

		try (Transaction tx = session.beginTransaction()) {
			tx.run(statementTemplate, parameters);
			tx.success();
		}

		final ChangeSet changeSet = session.getDeltas(PERSONS_QUERY);
		System.out.println(changeSet);
		System.out.println();
	}

}
