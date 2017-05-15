package eu.rssw.cw.rules;

import org.prorefactor.refactor.RefactorException;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.rule.RuleKey;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NoOpRuleTest extends AbstractTest {
  private RuleKey ruleKey;

  @BeforeTest
  public void init() {
    ruleKey = RuleKey.parse("consultingwerk-oe-main:NoOpRule");
  }

  @Test
  public void test1() throws RefactorException {
    InputFile inputFile = getInputFile("noop01.p");
    NoOpRule rule = new NoOpRule(ruleKey, context, null, "");
    rule.execute(inputFile, getParseUnit(inputFile));

    Assert.assertEquals(context.allIssues().size(), 0);
  }

}
