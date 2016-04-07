package nl.mwensveen.derivedresourcemarker.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class Action implements IWorkbenchWindowActionDelegate {

	private List<DerivedResourceHandler> handlers;

	public Action() {
		handlers = new ArrayList<DerivedResourceHandler>();
		handlers.add(new PomPackagingDerivedResourceHandler());
		handlers.add(new FoldernameDerivedResourceHandler());
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@Override
	public void run(IAction action) {
		// init the handlers first
		for (DerivedResourceHandler derivedResourceHandler : handlers) {
			derivedResourceHandler.init();
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		List<IProject> projects = Arrays.asList(workspace.getRoot().getProjects());

		for (IProject project : projects) {
			if (project.isOpen()) {
				try {
					processProject(project);
				} catch (CoreException e) {
				}
			}
		}

	}

	private void processProject(IProject project) throws CoreException {
		for (DerivedResourceHandler derivedResourceHandler : handlers) {
			derivedResourceHandler.initProject(project);
		}
		List<IResource> resources = Arrays.asList(project.members());
		for (IResource resource : resources) {
			processResource(resource);
		}
	}

	private void processResource(IResource resource) throws CoreException {
		for (DerivedResourceHandler derivedResourceHandler : handlers) {
			if (derivedResourceHandler.isDerived(resource)) {
				resource.setDerived(true, null);
				break;
			}
		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}
}
