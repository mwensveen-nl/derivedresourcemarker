package nl.mwensveen.derivedresourcemarker.action;

import nl.mwensveen.derivedresourcemarker.preferences.Names;
import nl.mwensveen.derivedresourcemarker.preferences.PreferenceManager;
import nl.mwensveen.derivedresourcemarker.util.FileRegExpUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public class FilenameDerivedResourceHandler implements DerivedResourceHandler {

	private Names fileNames;

	@Override
	public void initProject(IProject project) {
		// nothing to do
	}

	@Override
	public boolean isDerived(IResource resource) {
		String fileName = resource.getName();
		if (resource.getType() == IResource.FILE) {
			for (String name : fileNames.getNames()) {
				if (FileRegExpUtil.matchesWildCard(name, fileName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void init() {
		fileNames = PreferenceManager.getPreferencesForFileName();
	}

}
