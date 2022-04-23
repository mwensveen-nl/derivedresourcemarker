package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import nl.mwensveen.eclipse.drm.preferences.Names;
import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;
import nl.mwensveen.eclipse.drm.util.FileRegExpUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public class FilenameDerivedResourceInspector implements DerivedResourceInspector {

    private Names derivedFileNames;
    private Boolean fileNameSwitch;

    @Override
    public void initProject(IProject project) {
        // nothing to do
    }

    @Override
    public boolean isDerived(IResource resource) {
        if (fileNameSwitch) {
            String fileName = resource.getName();
            if (resource.getType() == IResource.FILE) {
                return derivedFileNames.getNames().stream().filter(dfn -> FileRegExpUtil.matchesWildCard(dfn, fileName)).findAny().isPresent();
            }
        }
        return false;
    }

    @Override
    public void init() {
        fileNameSwitch = PreferenceManager.getPreferencesForFileNameSwitch();
        if (fileNameSwitch) {
            derivedFileNames = PreferenceManager.getPreferencesForFileName();
        }
    }

}
