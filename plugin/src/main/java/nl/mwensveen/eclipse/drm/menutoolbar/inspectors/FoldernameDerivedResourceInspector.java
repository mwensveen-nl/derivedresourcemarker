package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import nl.mwensveen.eclipse.drm.preferences.Names;
import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public class FoldernameDerivedResourceInspector implements DerivedResourceInspector {

    private Names derivedFolderNames;

    @Override
    public void initProject(IProject project) {
        // nothing to do
    }

    @Override
    public boolean isDerived(IResource resource) {
        return resource.getType() == IResource.FOLDER && derivedFolderNames.contains(resource.getName());
    }

    @Override
    public void init() {
        derivedFolderNames = PreferenceManager.getPreferencesForFolderName();
    }

}
