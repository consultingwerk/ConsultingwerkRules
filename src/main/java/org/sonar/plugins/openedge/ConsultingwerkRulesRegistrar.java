package org.sonar.plugins.openedge;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.openedge.api.CheckRegistrar;
import org.sonar.plugins.openedge.api.checks.OpenEdgeDumpFileCheck;
import org.sonar.plugins.openedge.api.checks.OpenEdgeProparseCheck;
import org.sonar.plugins.openedge.api.checks.OpenEdgeXrefCheck;

import eu.rssw.cw.rules.EventWithoutCatch;

public class ConsultingwerkRulesRegistrar implements CheckRegistrar {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConsultingwerkRulesRegistrar.class);

  /**
   * Register the classes that will be used to instantiate checks during analysis.
   */
  @Override
  public void register(RegistrarContext registrarContext) {
    LOGGER.debug("Registering CheckRegistrar {}", ConsultingwerkRulesRegistrar.class.toString());

    // Call to registerClassesForRepository to associate the classes with the correct repository key
    registrarContext.registerClassesForRepository(ConsultingwerkRulesDefinition.REPOSITORY_KEY,
        Arrays.asList(xrefCheckClasses()), Arrays.asList(ppCheckClasses()), Arrays.asList(dbCheckClasses()));
  }

  /**
   * Lists all the XREF checks provided by the plugin
   */
  @SuppressWarnings("unchecked")
  public static Class<? extends OpenEdgeXrefCheck>[] xrefCheckClasses() {
    return new Class[] {};
  }

  /**
   * Lists all the checks provided by the plugin
   */
  @SuppressWarnings("unchecked")
  public static Class<? extends OpenEdgeProparseCheck>[] ppCheckClasses() {
    return new Class[] {EventWithoutCatch.class};
  }

  @SuppressWarnings("unchecked")
  public static Class<? extends OpenEdgeDumpFileCheck>[] dbCheckClasses() {
    return new Class[] {};
  }
}
