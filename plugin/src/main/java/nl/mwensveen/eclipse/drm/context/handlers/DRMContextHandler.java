package nl.mwensveen.eclipse.drm.context.handlers;

import java.util.Arrays;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.handlers.HandlerUtil;

// TODO: Auto-generated Javadoc
/**
 * The DRMContextHandler is the IHandler that is used by the eclipse plugin when the DRM option from the contect menu is selected.
 * It will mark all selected resources as derived.
 */
public class DRMContextHandler extends AbstractHandler implements IHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        // Get the active WorkbenchPage
        IWorkbenchPage activePage = window.getActivePage();

        // Get the Selection from the active WorkbenchPage page
        ISelection selection = activePage.getSelection();
        if (selection instanceof ITreeSelection) {
            TreeSelection treeSelection = (TreeSelection) selection;
            TreePath[] treePaths = treeSelection.getPaths();
            Arrays.stream(treePaths).forEach(tp -> processTreePath(tp));
        }
        return null;
    }

    /**
     * Process TreePath.
     *
     * @param tp the tp
     */
    private void processTreePath(TreePath tp) {
        // The TreePath contains segments that point to an entry in the tree of the package/project explorer.
        // The first segment is the top. For example workingset or project
        // The last segment is the selected resource itself. This can be an IWorkingset, IProject or IResource.
        Object selectedSegment = tp.getLastSegment();

        handleWorkingset(selectedSegment);
        handleProject(selectedSegment);
        handleResource(selectedSegment);
    }

    /**
     * Handle the selected IResources.
     *
     * @param selectedSegment the selected segment
     */
    private void handleResource(Object selectedSegment) {
        IResource resource = Adapters.adapt(selectedSegment, IResource.class);
        // no processing is the resource is a project, that is handled by the handelProject method.
        if (resource != null && IResource.PROJECT != resource.getType()) {
            processResource(resource);
        }
    }

    /**
     * Handle the selected project.
     *
     * @param selectedSegment the selected segment
     */
    private void handleProject(Object selectedSegment) {
        IProject project = Adapters.adapt(selectedSegment, IProject.class);
        if (project != null) {
            processProject(project);
        }
    }

    /**
     * Handle the selected workingset.
     *
     * @param selectedSegment the selected segment
     */
    private void handleWorkingset(Object selectedSegment) {
        IWorkingSet workingSet = Adapters.adapt(selectedSegment, IWorkingSet.class);
        if (workingSet != null) {
            processWorkingSet(workingSet);
        }
    }

    /**
     * Process a resource.
     *
     * @param resource the resource
     */
    private void processResource(IResource resource) {
        try {
            resource.setDerived(true, null);
        } catch (CoreException e) {
            Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.drm.")).log(e.getStatus());
        }
    }

    /**
     * Process project.
     *
     * @param project the project
     */
    private void processProject(IProject project) {
        if (project.isOpen()) {
            try {
                Arrays.stream(project.members()).forEach(r -> processResource(r));
            } catch (CoreException e) {
                Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.drm.")).log(e.getStatus());
            }
        }
    }

    /**
     * Process working set.
     *
     * @param workingSet the working set
     */
    private void processWorkingSet(IWorkingSet workingSet) {
        IAdaptable[] elements = workingSet.getElements();
        Arrays.stream(elements).forEach(e -> {
            handleProject(e);
            handleResource(e);
        });
    }

}
