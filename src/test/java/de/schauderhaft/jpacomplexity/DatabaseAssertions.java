package de.schauderhaft.jpacomplexity;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractIntegerAssert;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

public class DatabaseAssertions extends AbstractAssert<DatabaseAssertions, NamedParameterJdbcOperations> {

	protected DatabaseAssertions(NamedParameterJdbcOperations jdbc) {
		super(jdbc, DatabaseAssertions.class);
	}

	static DatabaseAssertions with(NamedParameterJdbcOperations jdbcOperations) {
		return new DatabaseAssertions(jdbcOperations);
	}

	AbstractIntegerAssert<?> assertCountOf(String tableName){
		return assertThat(actual.queryForObject("select count(*) from " + tableName, emptyMap(),
				Integer.class));
	}
}
