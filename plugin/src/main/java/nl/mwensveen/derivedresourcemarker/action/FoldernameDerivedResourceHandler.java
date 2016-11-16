package nl.mwensveen.derivedresourcemarker.action;

import nl.mwensveen.derivedresourcemarker.preferences.Names;
import nl.mwensveen.derivedresourcemarker.preferences.PreferenceManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public class FoldernameDerivedResourceHandler implements DerivedResourceHandler {

	private Names folderNames;

	@Override
	public void initProject(IProject project) {
		// nothing to do
	}

	@Override
	public boolean isDerived(IResource resource) {
		return resource.getType() == IResource.FOLDER && folderNames.contains(resource.getName());
	}

	@Override
	public void init() {
		folderNames = PreferenceManager.getPreferencesForFolderName();
	}

}
