package neo4j.driver.testkit.data;

import java.util.stream.Collectors;

import org.neo4j.driver.v1.Record;

import com.google.common.collect.Multiset;

public class ChangeSet {

	final Multiset<Record> positive;
	final Multiset<Record> negative;

	public ChangeSet(Multiset<Record> positive, Multiset<Record> negative) {
		super();
		this.positive = positive;
		this.negative = negative;
	}

	public Multiset<Record> getPositive() {
		return positive;
	}

	public Multiset<Record> getNegative() {
		return negative;
	}

	@Override
	public String toString() {
		return String.format("ChangeSet [\n  positive = { %s }\n  negative = { %s }\n]", formatRecords(positive), formatRecords(negative));
	}

	private String formatRecords(Multiset<Record> records) {
		return records.stream() //
				.map(r -> r.toString()) //
				.collect(Collectors.joining(", ")); //
	}

}
