package eu.rssw.cw.rules;

import java.io.IOException;
import java.util.Iterator;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.rule.RuleKey;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EventWithoutCatchTest extends AbstractTest {
  private RuleKey ruleKey;

  @BeforeTest
  public void init() {
    ruleKey = RuleKey.parse("consultingwerk-oe-main:EventWithoutCatchRule");
  }

  @Test
  public void test1() throws IOException {
    InputFile inputFile = getInputFile("eventwithoutcatch01.cls");
    EventWithoutCatch rule = new EventWithoutCatch();
    rule.setContext(ruleKey, context, null);
    rule.initialize();
    rule.sensorExecute(inputFile, getParseUnit(inputFile));

    Assert.assertEquals(context.allIssues().size(), 1);
    Iterator<Issue> allIssues = context.allIssues().iterator();
    Issue issue = allIssues.next();
    Assert.assertEquals(issue.primaryLocation().textRange().start().line(), 95);
  }

}
