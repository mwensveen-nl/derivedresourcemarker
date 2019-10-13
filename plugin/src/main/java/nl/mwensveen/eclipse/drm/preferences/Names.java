package nl.mwensveen.eclipse.drm.preferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Names implements Serializable {
	private static final long serialVersionUID = 914921147335397144L;
	private List<String> names = new ArrayList<String>();

	public List<String> getNames() {
		return names;
	}

	public void add(String name) {
		if (!names.contains(name)) {
			names.add(name);
		}
	}

	public void remove(String name) {
		if (names.contains(name)) {
			names.remove(name);
		}
	}

	public void replace(String[] newNames) {
		names.clear();
		for (String newName : newNames) {
			add(newName);
		}
	}

	public boolean contains(String name) {
		return names.contains(name);
	}
}
