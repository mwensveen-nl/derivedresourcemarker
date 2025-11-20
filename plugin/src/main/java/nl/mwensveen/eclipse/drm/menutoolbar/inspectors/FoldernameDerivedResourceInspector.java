package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;

import nl.mwensveen.eclipse.drm.preferences.Names;
import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;

public class FoldernameDerivedResourceInspector implements DerivedResourceInspector {
   private static final ILog LOG = Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.plugins.drm-plugin"));

   private Boolean folderNameSwitch;
   private Names derivedFolderNames;
   private Boolean isDebug;

   @Override
   public void initProject(IProject project) {
      // nothing to do
   }

   @Override
   public boolean isDerived(IResource resource, boolean unmark) {
      boolean result = false;
      if (folderNameSwitch) {
         result = (resource.getType() == IResource.FOLDER) && derivedFolderNames.contains(resource.getName());
      }
      if (isDebug) {
         LOG.info("Foldername result " + result);
      }
      return result;
   }

   @Override
   public void init() {
      isDebug = PreferenceManager.getPreferencesForDebug();
      folderNameSwitch = PreferenceManager.getPreferencesForFolderNameSwitch();
      if (folderNameSwitch) {
         derivedFolderNames = PreferenceManager.getPreferencesForFolderName();
      }
      if (isDebug) {
         LOG.info("Foldername? " + folderNameSwitch);
      }
   }

}
