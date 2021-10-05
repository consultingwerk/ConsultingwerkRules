package eu.rssw.cw.rules;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.prorefactor.core.ABLNodeType;
import org.prorefactor.core.JPNode;
import org.prorefactor.proparse.antlr4.Proparse;
import org.prorefactor.treeparser.Parameter;
import org.prorefactor.treeparser.ParseUnit;
import org.prorefactor.treeparser.symbols.Routine;
import org.prorefactor.treeparser.symbols.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.openedge.api.checks.OpenEdgeProparseCheck;
import org.sonar.plugins.openedge.api.model.SqaleConstantRemediation;

@Rule(priority = Priority.CRITICAL, name = ".Net event handler without CATCH block", tags = {"consultingwerk"})
@SqaleConstantRemediation(value = "1h")
public class EventWithoutCatch extends OpenEdgeProparseCheck {
  private static final Logger LOG = LoggerFactory.getLogger(EventWithoutCatch.class);

  @Override
  public void execute(InputFile file, ParseUnit unit) {
    Set<String> subscribedEvents = getSubscribedEvents(unit.getTopNode());

    for (Routine routine : unit.getRootScope().getRoutines()) {
      if (routine.getProgressType() != Proparse.METHOD)
        continue;
      if (!subscribedEvents.contains(routine.getName().toLowerCase(Locale.ENGLISH)))
        continue;
      LOG.trace("Inspecting method {}, {} parameters", routine.fullName(), routine.getParameters().size());

      // Only two parameters
      if (routine.getParameters().size() != 2)
        continue;

      Parameter firstParam = routine.getParameters().get(0);
      // Look for information of first parameter, and specifically datatype
      Variable var = (Variable) firstParam.getSymbol();
      if ("System.Object".equalsIgnoreCase(var.getDataType().getClassName()) && !hasCatchBlock(routine.getDefineNode())) {
        reportIssue(file, routine.getDefineNode(), "No CATCH block in .Net event handler " + routine.getName());
      }
    }
  }

  /**
   * Returns true if a method has a CATCH block at the root level
   */
  public boolean hasCatchBlock(JPNode codeBlock) {
    // Find outer code block node
    JPNode outerCodeBlock = codeBlock.query(ABLNodeType.CODE_BLOCK).get(0);
    // Shouldn't be null, but better avoid NPE
    if (outerCodeBlock == null)
      return false;

    // Return CATCH one level below
    JPNode catchNode = outerCodeBlock.findDirectChild(Proparse.CATCH);
    return (catchNode != null);
  }

  /**
   * Returns set of method names, which appear in subscribe() calls
   */
  private Set<String> getSubscribedEvents(JPNode topNode) {
    Set<String> list = new HashSet<>();

    // As SUBSCRIBE is a keyword, we have to look for this specific node type
    for (JPNode node : topNode.query(ABLNodeType.SUBSCRIBE)) {
      LOG.trace("Found SUBSCRIBE node at {}:{} ...", node.getLine(), node.getColumn());

      // Make sure there's something *after* SUBSCRIBE, and that's it's a method_param_list object, with one children
      // (and two parenthesis)
      if (node.getNextSibling() == null)
        continue;
      if (node.getNextSibling().getType() != Proparse.Method_param_list)
        continue;
      if (node.getNextSibling().getDirectChildren().size() != 3)
        continue;
      LOG.trace(" ... with the right number of argument ...");

      // Make sure the first (and only) param is an 'INPUT widget_ref', where first part is THIS-OBJECT and second part
      // is an ID
      JPNode param = node.getNextSibling().getDirectChildren().get(1);
      if (param.getType() != Proparse.INPUT)
        continue;
      if (param.getFirstChild().getNodeType() != ABLNodeType.ATTRIBUTE_REF)
        continue;
      if (param.getFirstChild().getFirstChild().getNodeType() != ABLNodeType.SYSTEM_HANDLE_REF)
        continue;
      if (param.getFirstChild().getFirstChild().getFirstChild().getNodeType() != ABLNodeType.THISOBJECT)
        continue;
      LOG.trace(" ... and the right type of argument ! Adding {} ",
          param.getFirstChild().getDirectChildren().get(2).getText());

      // Adding method name as lower-case (for future search)
      list.add(param.getFirstChild().getDirectChildren().get(2).getText().toLowerCase(Locale.ENGLISH));
    }

    return list;
  }
}
