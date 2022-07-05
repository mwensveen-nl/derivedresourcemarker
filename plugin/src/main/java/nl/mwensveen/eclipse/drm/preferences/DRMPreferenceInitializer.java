package nl.mwensveen.eclipse.drm.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class DRMPreferenceInitializer extends AbstractPreferenceInitializer {

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, PreferenceManager.PREFERENCE_STORE_QUALIFIER);
        store.setDefault(DRMPreferenceConstants.FILE_NAME, PreferenceManager.serialize(PreferenceManager.getDefaultPreferencesForFileNames()));
        store.setDefault(DRMPreferenceConstants.FOLDER_NAME, PreferenceManager.serialize(PreferenceManager.getDefaultPreferencesForFolderNames()));
        store.setDefault(DRMPreferenceConstants.POM_PACKAGING, PreferenceManager.serialize(PreferenceManager.getDefaultPreferencesForPomPackaging()));
        store.setDefault(DRMPreferenceConstants.NESTED_PROJECT_FOLDERS_SWITCH, PreferenceManager.getDefaultPreferencesForNestedProjectFolders());
        store.setDefault(DRMPreferenceConstants.NESTED_PROJECT_FOLDERS_DEPTH, PreferenceManager.getDefaultPreferencesForNestedProjectFoldersDepth());
        store.setDefault(DRMPreferenceConstants.DEBUG_SWITCH, PreferenceManager.getDefaultPreferencesForDebug());
    }

}
