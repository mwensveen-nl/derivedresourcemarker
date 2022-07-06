package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public interface DerivedResourceInspector {

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
     * @param unmark
     * @return true is the resource must be marked.
     */
    boolean isDerived(IResource resource, boolean unmark);
}
