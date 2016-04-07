package nl.mwensveen.derivedresourcemarker.action;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public interface DerivedResourceHandler {

	/**
	 * Initialize the Handler when processing starts.
	 */
	void init();

	/**
	 * Initialize the Handler when a project is processed.
	 * 
	 * @param project
	 */
	void initProject(IProject project);

	/**
	 * Must the resource be marked as derived?
	 * 
	 * @param resource
	 * @return true is the resource must be marked.
	 */
	boolean isDerived(IResource resource);
}
