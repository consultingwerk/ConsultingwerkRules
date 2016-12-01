package org.sonar.plugins.openedge;

import org.sonar.api.Plugin;

public class ConsultingwerkRules implements Plugin {

  @Override
  public void define(Context context) {
    // Rules
    context.addExtension(ConsultingwerkRulesDefinition.class);
    context.addExtension(ConsultingwerkRulesRegistrar.class);
  }

}
