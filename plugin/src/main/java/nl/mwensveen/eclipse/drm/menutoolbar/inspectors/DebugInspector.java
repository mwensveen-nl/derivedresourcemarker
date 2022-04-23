package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import java.util.Arrays;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;

public class DebugInspector implements DerivedResourceInspector {

    @Override
    public void init() {
        Platform.getLog(getClass()).info("Initializing Inspectors");
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IProject[] projects = root.getProjects();
        Arrays.stream(projects).forEach(p->Platform.getLog(getClass()).info("  --> " + p.getName() + " " + p.getLocation()));
    }

    @Override
    public void initProject(IProject project) {
        Platform.getLog(getClass()).info("Initializing project " + project.getName() + " " + project.getLocation());
        ;
    }

    @Override
    public boolean isDerived(IResource resource) {
        Platform.getLog(getClass()).info("Initializing resource " + resource.getName() + " " + resource.getLocation());
        return false;
    }

}
