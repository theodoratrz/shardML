package io.shardml.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.LongUnaryOperator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.RankMapping;
import io.shardml.cost.RowCounts;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.Mapped;
import io.shardml.cost.Workload.TableData;

/* RowCounts.forEachRowGroup (the Phase 4 loader's rows) agrees with forEachKeyValue (what data skew sums)
 * for every mapped column of every table block, at several rank offsets, and enumerates in its stated order. */
public class RowGroupsTest {

	private static Workload example(String schema, String workload, Map<String, String> sets) throws Exception {
		Path dir = Path.of("..", "examples", "cost");
		return new CostFixtures().workloadWith(Files.readString(dir.resolve(schema)),
				Files.readString(dir.resolve(workload)).replaceFirst("import \"[^\"]+\"", "import \"s.msql\""), sets);
	}

	@Test
	public void testGroupsSumToKeyValueCounts() throws Exception {
		List<Workload> workloads = List.of(example("banking.msql", "banking.workload", Map.of("s", "1.2")),
				example("tpcc.msql", "tpcc.workload", Map.of()),
				new CostFixtures().workloadWith(CostFixtures.SCHEMA, CostFixtures.WORKLOAD, Map.of()));
		for (Workload w : workloads) {
			for (long offset : new long[] { 0, 3, 7 }) {
				RankMapping mapping = RankMapping.of(w, offset);
				for (TableData data : w.tables()) {
					RowCounts counts = RowCounts.of(w, mapping, data.table());
					List<String> domains = counts.rowDomains();
					for (Workload.ColumnMapping m : data.columns()) {
						if (!(m instanceof Mapped mapped))
							continue;
						int index = domains.indexOf(mapped.path().domain());
						LongUnaryOperator projector = mapping.projector(mapped.path());
						TreeMap<Long, Long> fromGroups = new TreeMap<>();
						long[] total = { 0 };
						counts.forEachRowGroup((values, rows) -> {
							fromGroups.merge(projector.applyAsLong(values[index]), rows, Long::sum);
							total[0] += rows;
						});
						TreeMap<Long, Long> fromKeys = new TreeMap<>();
						counts.forEachKeyValue(m.column(), (value, rows) -> fromKeys.put(value, rows));
						String where = w.name() + " " + data.table() + "." + m.column() + " offset " + offset;
						Assertions.assertEquals(fromKeys, fromGroups, where);
						Assertions.assertEquals(counts.total(), total[0], where);
					}
				}
			}
		}
	}

	@Test
	public void testOrder() {
		Workload w = new CostFixtures().workloadWith("""
				CREATE TABLE t (a BIGINT, b BIGINT, id BIGINT, PRIMARY KEY (a, b, id))
				""", """
				import "s.msql"
				workload x {
				    domain p { size: 2 }
				    domain q { size: 3 }
				    table t { rows: 2 per p, q  a: p  b: q  unique id }
				    transaction r weight 1 {
				        let v = p uniform
				        read t where a = $v
				    }
				}
				""", Map.of());
		List<String> groups = new ArrayList<>();
		RowCounts.of(w, RankMapping.of(w, 1), "t").forEachRowGroup((values, rows) -> groups.add(values[0] + "," + values[1] + "x" + rows));
		// per p, q: every combination, the last domain fastest, unaffected by the rank offset (values, not ranks)
		Assertions.assertEquals(List.of("1,1x2", "1,2x2", "1,3x2", "2,1x2", "2,2x2", "2,3x2"), groups);
		Assertions.assertEquals(List.of("p", "q"), RowCounts.of(w, RankMapping.of(w, 0), "t").rowDomains());
	}
}
