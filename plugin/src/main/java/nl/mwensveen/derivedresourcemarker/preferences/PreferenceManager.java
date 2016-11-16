package nl.mwensveen.derivedresourcemarker.preferences;

import com.google.common.io.BaseEncoding;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import nl.mwensveen.derivedresourcemarker.plugin.Plugin;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceManager {

	public static Names getDefaultPreferencesForFolderNames() {
		Names names = new Names();
		names.add("bin");
		names.add("target");

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

	public static void savePreferencesForFolderNames(Names names) {
		try {
			IPreferenceStore store = Plugin.getDefault().getPreferenceStore();
			String namesString = serialize(names);
			store.setValue(PreferenceConstants.FOLDER_NAME, namesString);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void savePreferencesForPomPackaging(Names names) {
		try {
			IPreferenceStore store = Plugin.getDefault().getPreferenceStore();
			String namesString = serialize(names);
			store.setValue(PreferenceConstants.POM_PACKAGING, namesString);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void savePreferencesForFileNames(Names names) {
		try {
			IPreferenceStore store = Plugin.getDefault().getPreferenceStore();
			String namesString = serialize(names);
			store.setValue(PreferenceConstants.FILE_NAME, namesString);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String serialize(Names names) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(names);
		String namesString = BaseEncoding.base64().encode(bos.toByteArray());
		return namesString;
	}

	public static Names getPreferencesForFolderName() {
		IPreferenceStore store = Plugin.getDefault().getPreferenceStore();
		String value = store.getString(PreferenceConstants.FOLDER_NAME);
		Names names = deserialize(value);
		if (names == null) {
			return getDefaultPreferencesForFolderNames();
		}
		return names;
	}

	public static Names getPreferencesForPomPackaging() {
		IPreferenceStore store = Plugin.getDefault().getPreferenceStore();
		String value = store.getString(PreferenceConstants.POM_PACKAGING);
		Names names = deserialize(value);
		if (names == null) {
			return getDefaultPreferencesForPomPackaging();
		}
		return names;
	}

	public static Names getPreferencesForFileName() {
		IPreferenceStore store = Plugin.getDefault().getPreferenceStore();
		String value = store.getString(PreferenceConstants.FILE_NAME);
		Names names = deserialize(value);
		if (names == null) {
			return getDefaultPreferencesForFileNames();
		}
		return names;
	}

	private static Names deserialize(String value) {
		Names names = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(BaseEncoding.base64().decode(value));
			ObjectInputStream ois = new ObjectInputStream(bis);
			names = (Names) ois.readObject();
		} catch (UnsupportedEncodingException e) {
			// ignore
		} catch (IOException e) {
			// ignore
		} catch (ClassNotFoundException e) {
			// ignore
		}
		return names;
	}
}
