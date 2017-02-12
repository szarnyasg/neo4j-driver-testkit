package neo4j.driver.testkit.test;

import static org.neo4j.driver.v1.Values.parameters;

import org.junit.Test;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;

import neo4j.driver.testkit.Neo4jTestKitDriver;
import neo4j.driver.testkit.Neo4jTestKitSession;
import neo4j.driver.testkit.data.ChangeSet;

public class Neo4jDriverTest {

	@Test
	public void test1() throws Exception {
		try (Neo4jTestKitDriver driver = new Neo4jTestKitDriver()) {
			try (Neo4jTestKitSession session = driver.session()) {
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

	private void runUpdate(Neo4jTestKitSession session, final String PERSONS_QUERY, String statementTemplate,
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
