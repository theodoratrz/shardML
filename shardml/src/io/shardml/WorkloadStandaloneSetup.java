package io.shardml;

import org.eclipse.emf.ecore.EPackage;

import com.google.inject.Injector;

import io.shardml.sql.SqlPackage;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class WorkloadStandaloneSetup extends WorkloadStandaloneSetupGenerated {

	public static void doSetup() {
		new WorkloadStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	// Workload.ecore references SQL.ecore, and the workload package looks the SQL package up
	// in the global registry when it initializes. If the SQL package class was loaded while a
	// different registry state was current (Xtext's test injector providers restore the
	// registry between test classes), the lookup returns null and the workload package fails
	// to initialize for the rest of the JVM. So the SQL package is registered first.
	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		if (!EPackage.Registry.INSTANCE.containsKey(SqlPackage.eNS_URI))
			EPackage.Registry.INSTANCE.put(SqlPackage.eNS_URI, SqlPackage.eINSTANCE);
		return super.createInjectorAndDoEMFRegistration();
	}
}
