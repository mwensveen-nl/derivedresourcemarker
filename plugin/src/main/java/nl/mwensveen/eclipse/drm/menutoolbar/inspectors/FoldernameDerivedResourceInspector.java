package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import nl.mwensveen.eclipse.drm.preferences.Names;
import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;

public class FoldernameDerivedResourceInspector implements DerivedResourceInspector {

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
            Platform.getLog(getClass()).info("Foldername result " + result);
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
            Platform.getLog(getClass()).info("Foldername? " + folderNameSwitch);
        }
    }

}
