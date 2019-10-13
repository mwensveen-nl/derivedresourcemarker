package nl.mwensveen.eclipse.drm.menutoolbar.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.mwensveen.eclipse.drm.menutoolbar.inspectors.DerivedResourceInspector;
import nl.mwensveen.eclipse.drm.menutoolbar.inspectors.FilenameDerivedResourceInspector;
import nl.mwensveen.eclipse.drm.menutoolbar.inspectors.FoldernameDerivedResourceInspector;
import nl.mwensveen.eclipse.drm.menutoolbar.inspectors.PomPackagingDerivedResourceInspector;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class DRMMenuToolbarHandler extends AbstractHandler {
    private List<DerivedResourceInspector> inspectors;
    private ExecutionEvent event;

    public DRMMenuToolbarHandler() {
        inspectors = new ArrayList<DerivedResourceInspector>();
        inspectors.add(new PomPackagingDerivedResourceInspector());
        inspectors.add(new FoldernameDerivedResourceInspector());
        inspectors.add(new FilenameDerivedResourceInspector());
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        this.event = event;
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

        PopupDialog popupDialog = new PopupDialog(window.getShell());
        popupDialog.open();
        int returnCode = popupDialog.getReturnCode();
        if (PopupDialog.CANCEL == returnCode) {
            return null;
        }
        boolean unmark = PopupDialog.MARK_UNMARK_ID == returnCode;

        // init the inspectors first
        inspectors.forEach(DerivedResourceInspector::init);

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        Arrays.stream(workspace.getRoot().getProjects()).sequential().filter(p -> p.isOpen()).forEach(p -> processProject(p, unmark));

        return null;
    }

    private void processProject(IProject project, boolean unmark) {
        // initalize the inspectors for this project.
        MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "processProject", project.getName());
        inspectors.stream().forEach(dri -> dri.initProject(project));
        try {
            Arrays.stream(project.members()).forEach(r -> processResource(r, unmark));
        } catch (CoreException e) {
            Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.plugins.drm-plugin")).log(e.getStatus());
        }
    }

    private void processResource(IResource resource, boolean unmark) {
        try {
            boolean derived = inspectors.stream().filter(dri -> dri.isDerived(resource)).findFirst().isPresent();
            MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "processResource", resource.getName() + " " + derived);
            if (derived) {
                resource.setDerived(true, null);
            } else if (unmark) {
                resource.setDerived(false, null);
            }
        } catch (CoreException e) {
            Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.plugins.drm-plugin")).log(e.getStatus());
        }
    }
}
