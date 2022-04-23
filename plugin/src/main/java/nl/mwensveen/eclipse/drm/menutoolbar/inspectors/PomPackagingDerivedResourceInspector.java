package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nl.mwensveen.eclipse.drm.preferences.Names;
import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;
import org.eclipse.core.commands.operations.OperationStatus;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PomPackagingDerivedResourceInspector implements DerivedResourceInspector {

    private boolean isDerivedPomPackage;
    private Names derivedPomPackagingNames;
    private boolean pomPackagingSwitch;

    @Override
    public void initProject(IProject project) {
        if (pomPackagingSwitch) {
            isDerivedPomPackage = false;
            IFile pomFile = null;
            if (project.exists(new Path("/pom.xml"))) {
                pomFile = project.getFile("/pom.xml");
                try {
                    String packaging = getPomPackaging(pomFile);
                    isDerivedPomPackage = derivedPomPackagingNames.contains(packaging);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    @Override
    public boolean isDerived(IResource resource) {
        if (pomPackagingSwitch) {
            return isDerivedPomPackage && !"pom.xml".equals(resource.getName());
        }
        return false;
    }

    private String getPomPackaging(IFile pomFile) {
        String packaging = "";
        try {
            InputStream input = pomFile.getContents();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setValidating(false);

            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document pomDocument = builder.parse(input);
            Element docElement = pomDocument.getDocumentElement();

            NodeList elements = docElement.getElementsByTagName("packaging");
            if (elements != null) {
                for (int i = 0; (i < elements.getLength()) && "".equals(packaging); i++) {
                    Node element = elements.item(i);
                    Node child = element.getFirstChild();
                    String value = child.getTextContent();
                    if ((value != null) && (value.trim().length() > 0)) {
                        packaging = value;
                    }
                }
            }
        } catch (CoreException e) {
            Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.plugins.drm-plugin")).log(e.getStatus());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.plugins.drm-plugin"))
                    .log(new OperationStatus(OperationStatus.OPERATION_INVALID, "nl.mwensveen.eclipse.plugins.drm-plugin", 0, "Problem parsing pom.xml", e));
        }
        return packaging;
    }

    @Override
    public void init() {
        pomPackagingSwitch = PreferenceManager.getPreferencesForPomPackagingSwitch();
        if (pomPackagingSwitch) {
            derivedPomPackagingNames = PreferenceManager.getPreferencesForPomPackaging();
        }
    }
}
