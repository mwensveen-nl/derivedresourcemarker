package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;

import nl.mwensveen.eclipse.drm.preferences.Names;
import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;
import nl.mwensveen.eclipse.drm.util.FileRegExpUtil;

public class FilenameDerivedResourceInspector implements DerivedResourceInspector {
   private static final ILog LOG = Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.plugins.drm-plugin"));

   private Names derivedFileNames;
   private Boolean fileNameSwitch;
   private Boolean isDebug;

   @Override
   public void initProject(IProject project) {
      // nothing to do
   }

   @Override
   public boolean isDerived(IResource resource, boolean unmark) {
      boolean result = false;
      if (fileNameSwitch) {
         String fileName = resource.getName();
         if (resource.getType() == IResource.FILE) {
            result = derivedFileNames.getNames().stream().filter(dfn -> FileRegExpUtil.matchesWildCard(dfn, fileName)).findAny().isPresent();
         }
      }
      if (isDebug) {
         LOG.info("FileName result: " + result);
      }
      return result;
   }

   @Override
   public void init() {
      isDebug = PreferenceManager.getPreferencesForDebug();
      fileNameSwitch = PreferenceManager.getPreferencesForFileNameSwitch();
      if (isDebug) {
         LOG.info("FileName? " + fileNameSwitch);
      }
      if (fileNameSwitch) {
         derivedFileNames = PreferenceManager.getPreferencesForFileName();
         if (isDebug) {
            StringBuilder sb = new StringBuilder();
            derivedFileNames.getNames().forEach(n -> sb.append(n).append("\n"));
            LOG.info("FileNames: " + sb.toString());
         }
      }

   }

}
