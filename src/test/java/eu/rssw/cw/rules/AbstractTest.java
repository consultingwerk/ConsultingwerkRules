package eu.rssw.cw.rules;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.prorefactor.core.schema.Schema;
import org.prorefactor.refactor.RefactorException;
import org.prorefactor.refactor.RefactorSession;
import org.prorefactor.refactor.settings.ProparseSettings;
import org.prorefactor.treeparser.ParseUnit;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.plugins.openedge.api.Constants;
import org.testng.annotations.BeforeMethod;

import com.google.common.io.Files;

public abstract class AbstractTest {
  private static final String BASEDIR = "src/test/resources/";

  protected SensorContextTester context;
  private RefactorSession session;
  private Schema schema;

  @BeforeMethod
  public void initContext() throws IOException {
    schema = new Schema("src/test/resources/sp2k.schema");
    session = new RefactorSession(new ProparseSettings("src/test/resources"), schema);
  }

  @BeforeMethod
  public void initTest() throws IOException {
    context = SensorContextTester.create(new File(BASEDIR));

    // Common include files have to be referenced in the FileSystem object
    for (File file : new File(BASEDIR + "inc").listFiles()) {
      DefaultInputFile inputFile = new TestInputFileBuilder(BASEDIR, "inc/" + file.getName()).setLanguage(
          Constants.LANGUAGE_KEY).setType(Type.MAIN).initMetadata(
              Files.toString(file, Charset.defaultCharset())).build();
      context.fileSystem().add(inputFile);
    }
  }

  public InputFile getInputFile(String fileName) throws IOException {
    DefaultInputFile inputFile = new TestInputFileBuilder(BASEDIR, fileName).setLanguage(
        Constants.LANGUAGE_KEY).setType(Type.MAIN).initMetadata(
            Files.toString(new File(BASEDIR + fileName), Charset.defaultCharset())).build();
    context.fileSystem().add(inputFile);

    return inputFile;
  }

  public ParseUnit getParseUnit(InputFile file) {
    ParseUnit unit = new ParseUnit(file.file(), session);
    try {
      unit.treeParser01();
    } catch (RefactorException caught) {
      throw new RuntimeException("Unable to parse file", caught);
    }
    return unit;
  }
}
