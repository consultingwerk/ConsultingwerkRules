package org.sonar.plugins.openedge;

import java.util.Arrays;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.openedge.api.AnnotationBasedRulesDefinition;
import org.sonar.plugins.openedge.api.Constants;

public class ConsultingwerkRulesDefinition implements RulesDefinition {
  public static final String REPOSITORY_KEY = "consultingwerk-oe-main";
  public static final String DB_REPOSITORY_KEY = "consultingwerk-oedb-main";
  public static final String REPOSITORY_NAME = "OpenEdge rules (Consultingwerk)";

  @SuppressWarnings("rawtypes")
  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository(REPOSITORY_KEY, Constants.LANGUAGE_KEY).setName(
        REPOSITORY_NAME);
    AnnotationBasedRulesDefinition annotationLoader = new AnnotationBasedRulesDefinition(repository,
        Constants.LANGUAGE_KEY);
    annotationLoader.addRuleClasses(false, Arrays.<Class> asList(ConsultingwerkChecksRegistration.ppCheckClasses()));
    repository.done();

    NewRepository dbRepository = context.createRepository(DB_REPOSITORY_KEY, Constants.DB_LANGUAGE_KEY).setName(
        REPOSITORY_NAME);
    AnnotationBasedRulesDefinition annotationLoader2 = new AnnotationBasedRulesDefinition(dbRepository,
        Constants.DB_LANGUAGE_KEY);
    annotationLoader2.addRuleClasses(false, Arrays.<Class> asList(ConsultingwerkChecksRegistration.dbCheckClasses()));
    dbRepository.done();
  }
}