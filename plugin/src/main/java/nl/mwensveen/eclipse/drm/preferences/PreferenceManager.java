package nl.mwensveen.eclipse.drm.preferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Optional;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceManager {

    public static final String PREFERENCE_STORE_QUALIFIER = "nl.mwensveen.eclipse.plugins.drm-plugin";

    public static Names getDefaultPreferencesForFolderNames() {
        Names names = new Names();
        names.add("bin");
        names.add("target");
        names.add(".settings");

        return names;
    }

    public static Names getDefaultPreferencesForPomPackaging() {
        Names names = new Names();
        names.add("pom");

        return names;
    }

    public static Names getDefaultPreferencesForFileNames() {
        Names names = new Names();
        names.add(".project");
        names.add("*.classpath");

        return names;
    }

    public static Boolean getDefaultPreferencesForDebug() {
        return Boolean.FALSE;
    }

    public static void savePreferencesForFolderNames(Names names) {
        try {
            save(names, DRMPreferenceConstants.FOLDER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePreferencesForFolderNameSwitch(boolean b) {
        try {
            save(b, DRMPreferenceConstants.FOLDER_NAME_SWITCH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePreferencesForFileNameSwitch(boolean b) {
        try {
            save(b, DRMPreferenceConstants.FILE_NAME_SWITCH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePreferencesForPomPackagingSwitch(boolean b) {
        try {
            save(b, DRMPreferenceConstants.POM_PACKAGING_SWITCH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void save(Names names, String preferenceName) throws IOException {
        String namesString = serialize(names);
        save(namesString, preferenceName);
    }

    private static void save(boolean b, String preferenceName) throws IOException {
        save(Boolean.valueOf(b).toString(), preferenceName);
    }

    private static void save(String s, String preferenceName) throws IOException {
        IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, PREFERENCE_STORE_QUALIFIER);
        store.setValue(preferenceName, s);
        if (store.needsSaving() && (store instanceof IPersistentPreferenceStore)) {
            try {
                ((IPersistentPreferenceStore) store).save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.drm.")).info("No IPersistentPreferenceStore");
        }
    }

    private static String read(String preferenceName) {
        IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, PREFERENCE_STORE_QUALIFIER);
        return store.getString(preferenceName);
    }

    private static Optional<Names> readNames(String preferenceName) {
        String value = read(preferenceName);
        return Optional.ofNullable(deserialize(value));
    }

    private static Optional<Boolean> readBoolean(String preferenceName) {
        String value = read(preferenceName);
        return value.equals("") ? Optional.empty() : Optional.of(Boolean.valueOf(value));
    }

    private static Optional<Integer> readInteger(String preferenceName) {
        String value = read(preferenceName);
        return value.equals("") ? Optional.empty() : Optional.of(Integer.valueOf(value));
    }

    public static void savePreferencesForPomPackaging(Names names) {
        try {
            save(names, DRMPreferenceConstants.POM_PACKAGING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePreferencesForFileNames(Names names) {
        try {
            save(names, DRMPreferenceConstants.FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePreferencesDebug(boolean b) {
        try {
            save(b, DRMPreferenceConstants.DEBUG_SWITCH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePreferencesNestedProjectFolder(boolean b) {
        try {
            save(b, DRMPreferenceConstants.NESTED_PROJECT_FOLDERS_SWITCH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePreferencesNestedProjectFolderDepth(Integer depth) {
        try {
            save(depth.toString(), DRMPreferenceConstants.NESTED_PROJECT_FOLDERS_DEPTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String serialize(Names names) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(names);
            String namesString = Base64.getEncoder().encodeToString(bos.toByteArray());
            return namesString;
        } catch (IOException e) {
            return null;
        }
    }

    public static Names getPreferencesForFolderName() {
        return readNames(DRMPreferenceConstants.FOLDER_NAME).orElse(getDefaultPreferencesForFolderNames());
    }

    public static Names getPreferencesForPomPackaging() {
        return readNames(DRMPreferenceConstants.POM_PACKAGING).orElse(getDefaultPreferencesForPomPackaging());
    }

    public static Names getPreferencesForFileName() {
        return readNames(DRMPreferenceConstants.FILE_NAME).orElse(getDefaultPreferencesForFileNames());
    }

    public static Boolean getPreferencesForDebug() {
        return readBoolean(DRMPreferenceConstants.DEBUG_SWITCH).orElse(getDefaultPreferencesForDebug());
    }

    public static Boolean getPreferencesForFolderNameSwitch() {
        return readBoolean(DRMPreferenceConstants.FOLDER_NAME_SWITCH).orElse(getDefaultPreferencesForFolderNameSwitch());
    }

    public static Boolean getDefaultPreferencesForFolderNameSwitch() {
        return Boolean.TRUE;
    }

    public static boolean getPreferencesForNestedProjectFolders() {
        return readBoolean(DRMPreferenceConstants.NESTED_PROJECT_FOLDERS_SWITCH).orElse(getDefaultPreferencesForNestedProjectFolders());
    }

    public static Integer getPreferencesForNestedProjectFoldersDepth() {
        return readInteger(DRMPreferenceConstants.NESTED_PROJECT_FOLDERS_DEPTH).orElse(getDefaultPreferencesForNestedProjectFoldersDepth());
    }

    private static Names deserialize(String value) {
        Names names = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(value));
            ObjectInputStream ois = new ObjectInputStream(bis);
            names = (Names) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            // ignore
        }
        return names;
    }

    public static Boolean getDefaultPreferencesForNestedProjectFolders() {
        return Boolean.TRUE;
    }

    public static boolean getPreferencesForPomPackagingSwitch() {
        return readBoolean(DRMPreferenceConstants.POM_PACKAGING_SWITCH).orElse(getDefaultPreferencesForPomPackagingSwitch());
    }

    public static Boolean getDefaultPreferencesForPomPackagingSwitch() {
        return Boolean.TRUE;
    }

    public static boolean getPreferencesForFileNameSwitch() {
        return readBoolean(DRMPreferenceConstants.FILE_NAME_SWITCH).orElse(getDefaultPreferencesForFileNameSwitch());
    }

    public static Boolean getDefaultPreferencesForFileNameSwitch() {
        return Boolean.TRUE;
    }

    public static Integer getDefaultPreferencesForNestedProjectFoldersDepth() {
        return 2;
    }

}
