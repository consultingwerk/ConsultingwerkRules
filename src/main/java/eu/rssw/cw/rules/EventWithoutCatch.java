package eu.rssw.cw.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.openedge.api.LicenceRegistrar.Licence;
import org.sonar.plugins.openedge.api.checks.OpenEdgeProparseCheck;
import org.sonar.plugins.openedge.api.model.SqaleConstantRemediation;
import org.sonar.plugins.openedge.api.org.prorefactor.core.JPNode;
import org.sonar.plugins.openedge.api.org.prorefactor.core.NodeTypes;
import org.sonar.plugins.openedge.api.org.prorefactor.treeparser.Parameter;
import org.sonar.plugins.openedge.api.org.prorefactor.treeparser.ParseUnit;
import org.sonar.plugins.openedge.api.org.prorefactor.treeparser.Routine;
import org.sonar.plugins.openedge.api.org.prorefactor.treeparser.Variable;

@Rule(priority = Priority.CRITICAL, name = ".Net event without CATCH block", tags = {"consultingwerk"})
@SqaleConstantRemediation(value = "1h")
public class EventWithoutCatch extends OpenEdgeProparseCheck {

  public EventWithoutCatch(RuleKey ruleKey, SensorContext context, Licence licence, String serverId) {
    super(ruleKey, context, licence, serverId);
  }

  @Override
  public void execute(InputFile file, ParseUnit unit) {
    for (Routine r : unit.getRootScope().getRoutineMap().values()) {
      if (r.getProgressType() == NodeTypes.METHOD) {
        r.getRoutineScope()
        System.out.println("Routine " + r.getProgressType() + " " + r.fullName());
        System.out.println("Params " + r.getParameters().size());
        for (Parameter p : r.getParameters()) {
          Variable v = (Variable) (p.getSymbol());
          System.out.println(p.getSymbol().fullName() + " " + v.getClassName());
        }
      }
    }

    for (JPNode node : unit.getTopNode().query(NodeTypes.SUBSCRIBE)) {
      System.out.println("Node " + node);
    }
     /*for (SymbolScope scope :  unit.getRootScope().getChildScopes()) {
       System.out.println(scope.toString());
       System.out.println(scope.getRootBlock().isMethodBlock());
       
     }
    for (JPNode methd : unit.getTopNode().query(NodeTypes.METHOD)) {
      System.out.println("Method");
      for (JPNode param : methd.query(NodeTypes.Method_parameter)) {
        System.out.println("Parameter");
      }
    }*/
  }

}
