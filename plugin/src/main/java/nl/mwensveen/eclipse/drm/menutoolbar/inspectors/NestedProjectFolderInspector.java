package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

public class NestedProjectFolderInspector implements DerivedResourceInspector {

    private static final ILog LOG = Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.plugins.drm-plugin"));
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
            LOG.info("NestedProjectFolder? " + isNestedProjectFolder + " depth " + (depth == null ? "" : depth));
        }
    }

    @Override
    public void initProject(IProject project) {
    }

    @Override
    public boolean isDerived(IResource resource, boolean unmark) {
        boolean result = false;
        if (isNestedProjectFolder && (resource.getType() == IResource.FOLDER)) {
            result = projectPaths.contains(resource.getLocation());
            if (isDebug) {
                LOG.info("NestedProjectFolder processing nested folders depth: " + depth);
            }
            processNestedFolder((IFolder) resource, unmark, 1);
        }
        if (isDebug) {
            LOG.info("NestedProjectFolder result: " + result);
        }
        return result;
    }

    private void processNestedFolder(IFolder folder, boolean unmark, int processDepth) {
        try {
            if (processDepth == 1) {
                nextDepth(folder, unmark, processDepth);
            } else if (processDepth <= depth) {
                boolean derived = projectPaths.contains(folder.getLocation());
                if (isDebug) {
                    LOG.info("NestedProjectFolder nested folder: " + folder.getLocation() + " (" + processDepth + ") " + derived);
                }
                if (derived) {
                    folder.setDerived(true, null);
                } else {
                    if (unmark) {
                        folder.setDerived(false, null);
                    }
                    nextDepth(folder, unmark, processDepth);
                }
            }
        } catch (CoreException e) {
            LOG.error("error getting members for " + folder.getName(), e);
        }

    }

    private void nextDepth(IFolder folder, boolean unmark, int processDepth) throws CoreException {
        IResource[] members = folder.members();
        Arrays.stream(members).filter(m -> m.getType() == IResource.FOLDER).forEach(m -> processNestedFolder((IFolder) m, unmark, processDepth + 1));
    }
}
