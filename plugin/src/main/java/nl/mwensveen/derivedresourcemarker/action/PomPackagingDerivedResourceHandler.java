package nl.mwensveen.derivedresourcemarker.action;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import nl.mwensveen.derivedresourcemarker.preferences.Names;
import nl.mwensveen.derivedresourcemarker.preferences.PreferenceManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PomPackagingDerivedResourceHandler implements DerivedResourceHandler {

	private boolean isDerivedPomPackage;
	private Names pomPackagingNames;

	@Override
	public void initProject(IProject project) {
		IFile pom = null;
		if (project.exists(new Path("/pom.xml"))) {
			pom = project.getFile("/pom.xml");
		}
		if (pom != null) {
			try {
				String packaging = getPomPackaging(pom);
				isDerivedPomPackage = pomPackagingNames.contains(packaging);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			isDerivedPomPackage = false;
		}
	}

	@Override
	public boolean isDerived(IResource resource) {
		return isDerivedPomPackage && !"pom.xml".equals(resource.getName());
	}

	private String getPomPackaging(IFile pomFile) throws Exception {
		String packaging = "";
		InputStream input = pomFile.getContents();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setIgnoringComments(true);
		factory.setValidating(false);

		DocumentBuilder builder = factory.newDocumentBuilder();
		Document pom = builder.parse(input);
		Element docEle = pom.getDocumentElement();

		NodeList elements = docEle.getElementsByTagName("packaging");
		if (elements != null) {
			for (int i = 0; i < elements.getLength(); i++) {
				Node element = elements.item(i);
				Node child = element.getFirstChild();
				String value = child.getTextContent();
				if (value != null && value.trim().length() > 0) {
					packaging = value;
				}
			}
		}
		return packaging;
	}

	@Override
	public void init() {
		pomPackagingNames = PreferenceManager.getPreferencesForPomPackaging();
	}
}
