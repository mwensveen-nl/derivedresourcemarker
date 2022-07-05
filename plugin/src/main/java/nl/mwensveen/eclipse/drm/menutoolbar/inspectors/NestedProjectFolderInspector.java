package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

public class NestedProjectFolderInspector implements DerivedResourceInspector {

    private List<IPath> projectPaths;
    private boolean isNestedProjectFolder;
    private Boolean isDebug;
    private Integer depth;

    @Override
    public void init() {
        isDebug = PreferenceManager.getPreferencesForDebug();
        isNestedProjectFolder = PreferenceManager.getPreferencesForNestedProjectFolders();
        if (isNestedProjectFolder) {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IWorkspaceRoot root = workspace.getRoot();
            IProject[] projects = root.getProjects();
            projectPaths = Arrays.stream(projects).map(p -> p.getLocation()).collect(Collectors.toList());
            depth = PreferenceManager.getPreferencesForNestedProjectFoldersDepth();
        }
        if (isDebug) {
            Platform.getLog(getClass()).info("NestedProjectFolder? " + isNestedProjectFolder + " depth " + (depth == null ? "" : depth));
        }
    }

    @Override
    public void initProject(IProject project) {
    }

    @Override
    public boolean isDerived(IResource resource) {
        boolean result = false;
        if (isNestedProjectFolder) {
            result = projectPaths.contains(resource.getLocation());
        }
        if (isDebug) {
            Platform.getLog(getClass()).info("NestedProjectFolder result: " + result);
        }
        return result;
    }

}
