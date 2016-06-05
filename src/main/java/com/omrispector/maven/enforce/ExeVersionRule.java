package com.omrispector.maven.enforce;


import java.io.File;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.enforcer.AbstractVersionEnforcer;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

/**
 * This rule checks that the Java version is allowed.
 *
 * @author <a href="mailto:omri.spector@gmail.com">Omri Spector</a>
 * @version $Id$
 */
public class ExeVersionRule
    extends AbstractVersionEnforcer
{
  private String checkOn;   // Path to Executable/DLL
  private String component; // Name of component for logging
  private String enable;    // Pass false in cases where rule should be disabled. Defaults to true.

  /*
   * (non-Javadoc)
   *
   * @see org.apache.maven.enforcer.rule.api.EnforcerRule#execute(org.apache.maven.enforcer.rule.api.EnforcerRuleHelper)
   */
  public void execute( EnforcerRuleHelper helper )
      throws EnforcerRuleException
  {
    boolean enforce = enable==null || !"false".equalsIgnoreCase(enable);

    if (enforce) {

      String targetFile;
      try {
        targetFile = helper.evaluate(checkOn).toString();
      } catch (ExpressionEvaluationException e) {
        throw new EnforcerRuleException( "Unable to evaluate checkOn entry: " + checkOn, e );
      }

      File target = new File(targetFile);
      if (!target.exists()) throw new EnforcerRuleException("Could not locate "+ component +" version:"+ checkOn +" not found");

      String file_version = new WindowsFileVersionInfo().getVersion(targetFile);
      Log log = helper.getLog();

      log.debug( component + " version (based on '"+ checkOn +"') +is " + file_version );

      ArtifactVersion detectedFileVersion = new DefaultArtifactVersion( file_version );

      enforceVersion( helper.getLog(), component, getVersion(), detectedFileVersion );
    }
  }

}