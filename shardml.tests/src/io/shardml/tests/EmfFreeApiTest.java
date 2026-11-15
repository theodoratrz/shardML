package io.shardml.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// The cost model's records are EMF-free (Plan-phase3 4.4a): Phase 5 builds thousands of candidate
// layouts as plain records. Every public type of io.shardml.cost, and every io.shardml type its
// public API reaches (Placement, Node, SqlType, ...), is scanned: return types, parameters, record
// components, public fields, generic arguments included. No type of EMF, Xtext or the generated
// model packages may appear, except the parameters of the two factories, Layouts.of and Workloads.of, and of the convenience
// form CostModel.evaluate(Database, Workload, Placement).
public class EmfFreeApiTest {

	private static final List<String> FORBIDDEN = List.of("org.eclipse.", "io.shardml.shardML.", "io.shardml.sql.",
			"io.shardml.workload.");
	private static final Set<String> ALLOWED = Set.of("io.shardml.cost.Layouts.of(io.shardml.shardML.Database)",
			"io.shardml.cost.Workloads.of(io.shardml.workload.WorkloadModel)",
			"io.shardml.cost.CostModel.evaluate(io.shardml.shardML.Database)");

	@Test
	public void testNoEmfTypeInThePublicApi() throws Exception {
		Deque<Class<?>> todo = new ArrayDeque<>();
		try (Stream<Path> files = Files.list(Path.of("..", "shardml", "target", "classes", "io", "shardml", "cost"))) {
			for (Path file : files.filter(p -> p.toString().endsWith(".class")).sorted().toList()) {
				String name = file.getFileName().toString().replace(".class", "");
				if (!name.matches("[A-Za-z0-9_$]+"))
					continue; // not a class name (a copy such as "X 2.class" left in target/ by a file sync)
				Class<?> type = Class.forName("io.shardml.cost." + name);
				if (Modifier.isPublic(type.getModifiers()))
					todo.add(type);
			}
		}
		Assertions.assertTrue(todo.size() >= 30, "found only " + todo.size() + " public types");
		Set<Class<?>> seen = new LinkedHashSet<>();
		List<String> offenders = new ArrayList<>();
		Set<String> allowedSeen = new LinkedHashSet<>();
		while (!todo.isEmpty()) {
			Class<?> type = todo.poll();
			if (!seen.add(type))
				continue;
			for (Method m : type.getMethods()) {
				if (m.getDeclaringClass() != type)
					continue;
				String where = type.getName() + "." + m.getName();
				check(where + " returns", m.getGenericReturnType(), offenders, todo);
				for (Type p : m.getGenericParameterTypes()) {
					if (ALLOWED.contains(where + "(" + typeName(p) + ")"))
						allowedSeen.add(where + "(" + typeName(p) + ")");
					else
						check(where + " takes", p, offenders, todo);
				}
			}
			for (Constructor<?> c : type.getConstructors()) {
				for (Type p : c.getGenericParameterTypes())
					check(type.getName() + " constructor takes", p, offenders, todo);
			}
			for (Field field : type.getFields())
				check(type.getName() + "." + field.getName(), field.getGenericType(), offenders, todo);
			if (type.isRecord()) {
				for (RecordComponent rc : type.getRecordComponents())
					check(type.getName() + "." + rc.getName(), rc.getGenericType(), offenders, todo);
			}
			for (Class<?> nested : type.getClasses())
				todo.add(nested);
		}
		Assertions.assertEquals(List.of(), offenders);
		// the two exceptions were met: the scan does see EMF types where they are
		Assertions.assertEquals(ALLOWED, allowedSeen);
		// the scan reached the types the records use from other packages
		Assertions.assertTrue(seen.stream().anyMatch(c -> c.getName().equals("io.shardml.placement.Placement")));
		Assertions.assertTrue(seen.stream().anyMatch(c -> c.getName().equals("io.shardml.schema.SqlTypes$SqlType")));
	}

	private static String typeName(Type t) {
		return t instanceof Class<?> c ? c.getName() : t.getTypeName();
	}

	// records an offending type; queues io.shardml types to scan their API too
	private static void check(String where, Type t, List<String> offenders, Deque<Class<?>> todo) {
		if (t instanceof Class<?> c) {
			while (c.isArray())
				c = c.getComponentType();
			String name = c.getName();
			if (FORBIDDEN.stream().anyMatch(name::startsWith))
				offenders.add(where + " " + name);
			else if (name.startsWith("io.shardml.") && Modifier.isPublic(c.getModifiers()))
				todo.add(c);
		} else if (t instanceof ParameterizedType p) {
			check(where, p.getRawType(), offenders, todo);
			for (Type a : p.getActualTypeArguments())
				check(where, a, offenders, todo);
		} else if (t instanceof WildcardType w) {
			for (Type b : w.getUpperBounds())
				check(where, b, offenders, todo);
			for (Type b : w.getLowerBounds())
				check(where, b, offenders, todo);
		} else if (t instanceof GenericArrayType g) {
			check(where, g.getGenericComponentType(), offenders, todo);
		}
		// a type variable (E extends Enum<E>) is not followed: its bounds can refer to itself, and the
		// types it is instantiated with appear where it is used
	}
}
