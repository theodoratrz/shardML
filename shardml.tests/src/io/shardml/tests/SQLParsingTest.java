package io.shardml.tests;

import java.util.List;

import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

import io.shardml.sql.Column;
import io.shardml.sql.PrimaryKey;
import io.shardml.sql.Schema;
import io.shardml.sql.SqlPackage;
import io.shardml.sql.Table;
import io.shardml.sql.UniqueKey;

@ExtendWith(InjectionExtension.class)
@InjectWith(SQLInjectorProvider.class)
public class SQLParsingTest {

	@Inject
	private ParseHelper<Schema> parseHelper;

	@Inject
	private ValidationTestHelper validationTestHelper;

	@Test
	public void testTablePrimaryKeyAndUnique() throws Exception {
		Schema schema = parseHelper.parse("""
				CREATE TABLE accounts (
				    id BIGINT NOT NULL,
				    customer_id BIGINT NOT NULL,
				    iban VARCHAR(34),
				    PRIMARY KEY (customer_id, id),
				    UNIQUE (customer_id, iban)
				)
				""");
		validationTestHelper.assertNoErrors(schema);
		Table table = schema.getTables().get(0);
		Assertions.assertEquals(3, table.getColumns().size());
		Assertions.assertEquals(2, table.getConstraints().size());
		PrimaryKey pk = (PrimaryKey) table.getConstraints().get(0);
		Assertions.assertEquals(List.of("customer_id", "id"), pk.getColumns().stream().map(Column::getName).toList());
		Assertions.assertSame(table, pk.getColumns().get(0).eContainer());
		UniqueKey unique = (UniqueKey) table.getConstraints().get(1);
		Assertions.assertEquals(List.of("customer_id", "iban"), unique.getColumns().stream().map(Column::getName).toList());
	}

	@Test
	public void testTableConstraintBeforeColumns() throws Exception {
		Schema schema = parseHelper.parse("""
				CREATE TABLE t (
				    PRIMARY KEY (id),
				    id INT
				)
				""");
		validationTestHelper.assertNoErrors(schema);
	}

	// `id` exists in both tables; the constraint must link to its own table's column
	@Test
	public void testTableConstraintColumnsScopedToOwnTable() throws Exception {
		Schema schema = parseHelper.parse("""
				CREATE TABLE customers (
				    id BIGINT PRIMARY KEY
				)
				CREATE TABLE accounts (
				    customer_id BIGINT,
				    id BIGINT,
				    PRIMARY KEY (customer_id, id)
				)
				""");
		validationTestHelper.assertNoErrors(schema);
		Table accounts = schema.getTables().get(1);
		Column id = accounts.getConstraints().get(0).getColumns().get(1);
		Assertions.assertSame(accounts, id.eContainer());
	}

	@Test
	public void testTableConstraintColumnOfOtherTable() throws Exception {
		Schema schema = parseHelper.parse("""
				CREATE TABLE customers (
				    id BIGINT PRIMARY KEY,
				    email VARCHAR(255)
				)
				CREATE TABLE accounts (
				    id BIGINT,
				    UNIQUE (email)
				)
				""");
		validationTestHelper.assertError(schema, SqlPackage.Literals.UNIQUE_KEY, Diagnostic.LINKING_DIAGNOSTIC, "email");
	}
}
