package org.sonar.plugins.openedge;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.openedge.api.AnnotationBasedRulesDefinition;
import org.sonar.plugins.openedge.api.Constants;

public class ConsultingwerkRulesDefinition implements RulesDefinition {
  public static final String REPOSITORY_KEY = "consultingwerk-oe-main";
  public static final String DB_REPOSITORY_KEY = "consultingwerk-oedb-main";
  public static final String REPOSITORY_NAME = "OpenEdge rules (Consultingwerk)";

  private final SonarRuntime runtime;

  public ConsultingwerkRulesDefinition(SonarRuntime runtime) {
    this.runtime = runtime;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void define(Context context) {
    var repository = context.createRepository(REPOSITORY_KEY, Constants.LANGUAGE_KEY).setName(
        REPOSITORY_NAME);
    var annotationLoader = new AnnotationBasedRulesDefinition(repository, Constants.LANGUAGE_KEY, runtime);
    List<Class> list = new ArrayList<>();
    list.addAll(ConsultingwerkRulesChecksProvider.ppCheckClasses());
    annotationLoader.addRuleClasses(false, list);

    repository.done();

    var repository2 = context.createRepository(DB_REPOSITORY_KEY, Constants.DB_LANGUAGE_KEY).setName(
        REPOSITORY_NAME);
    var annotationLoader2 = new AnnotationBasedRulesDefinition(repository2, Constants.DB_LANGUAGE_KEY, runtime);
    list = new ArrayList<>();
    list.addAll(ConsultingwerkRulesChecksProvider.dbCheckClasses());
    annotationLoader2.addRuleClasses(false, list);
    repository2.done();
  }
}
