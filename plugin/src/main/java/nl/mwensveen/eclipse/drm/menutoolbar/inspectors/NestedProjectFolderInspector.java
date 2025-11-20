package nl.mwensveen.eclipse.drm.menutoolbar.inspectors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

import nl.mwensveen.eclipse.drm.preferences.PreferenceManager;

public class NestedProjectFolderInspector implements DerivedResourceInspector {

   private static final ILog LOG = Platform.getLog(Platform.getBundle("nl.mwensveen.eclipse.plugins.drm-plugin"));
   private List<IPath> projectPaths;
   private boolean isNestedProjectFolder;
   private Boolean isDebug;
   private Integer depth;
   private List<IFolder> nestedFolders;

   @Override
   public void init() {
      isDebug = PreferenceManager.getPreferencesForDebug();
      isNestedProjectFolder = PreferenceManager.getPreferencesForNestedProjectFolders();
      if (isNestedProjectFolder) {
         IWorkspace workspace = ResourcesPlugin.getWorkspace();
         IWorkspaceRoot root = workspace.getRoot();
         IProject[] projects = root.getProjects();
         projectPaths = Arrays.stream(projects).map(p -> p.getLocation()).collect(Collectors.toList());
         depth = PreferenceManager.getPreferencesForNestedProjectFoldersDepth();
         nestedFolders = new java.util.ArrayList<>();
      }
      if (isDebug) {
         LOG.info("NestedProjectFolder? " + isNestedProjectFolder + " depth " + (depth == null ? "" : depth));
         LOG.info("NestedProjectFolder -> ProjectPaths: " + projectPaths);
      }
   }

   @Override
   public void initProject(IProject project) {
   }

   @Override
   public boolean isDerived(IResource resource, boolean unmark) {
      boolean result = false;
      if (isNestedProjectFolder && (resource.getType() == IResource.FOLDER)) {
         if (isDebug) {
            LOG.info("NestedProjectFolder -> processing nested folders depth: " + depth);
         }
         processNestedFolder((IFolder) resource, 1);
      }
      if (isDebug) {
         LOG.info("NestedProjectFolder result: " + result + " resourcetype " + resource.getType());
      }
      return result;
   }

   public void finish(IProject[] workspaceProjects) {
      Map<IPath, IProject> workspaceProjectsMap = Arrays.stream(workspaceProjects).collect(Collectors.toMap(IProject::getLocation, Function.identity()));

      LOG.info("NestedProjectFolder finish");
      try {
         nestedFolders.stream().forEach(folder -> {
            LOG.info("NestedProjectFolder finishing folder: " + folder.getLocation());
            if (workspaceProjectsMap.containsKey(folder.getLocation())) {
               LOG.info("NestedProjectFolder processingflder: " + folder.getLocation());
               try {
                  folder.setDerived(false, null);
               } catch (CoreException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               IProject project = workspaceProjectsMap.get(folder.getLocation());
               LOG.info("NestedProjectFolder equals project : " + workspaceProjectsMap.get(folder.getLocation()).getName());
               try {
                  Map<IPath, IResource> projectMembers =
                        Arrays.stream(project.members()).collect(Collectors.toMap(IResource::getLocation, Function.identity()));
                  Arrays.stream(folder.members()).forEach(m -> {
                     IResource projectMember = projectMembers.get(m.getLocation());
                     try {
                        m.setDerived(projectMember.isDerived(), null);
                     } catch (CoreException e) {
                        LOG.error("error finishing ", e);
                     }
                  });
               } catch (CoreException e) {
                  LOG.error("error finishing ", e);
               }
            } else {
               LOG.info("NestedProjectFolder not found");
            }
         });
      } catch (Exception e) {
         LOG.error("error finishing ", e);
      }
   }

   private void processNestedFolder(IFolder folder, int processDepth) {
      try {
         if (processDepth <= depth) {
            if (isDebug) {
               LOG.info("NestedProjectFolder -> checking nested folder: " + folder.getLocation() + " (" + processDepth + ") ");
            }
            boolean nested = projectPaths.contains(folder.getLocation());
            if (isDebug) {
               LOG.info("NestedProjectFolder -> " + nested);
            }
            if (nested) {
               nestedFolders.add(folder);
            } else {
               nextDepth(folder, processDepth);
            }
         }
      } catch (CoreException e) {
         LOG.error("error getting members for " + folder.getName(), e);
      }

   }

   private void nextDepth(IFolder folder, int processDepth) throws CoreException {
      IResource[] members = folder.members();
      Arrays.stream(members).filter(m -> m.getType() == IResource.FOLDER).forEach(m -> processNestedFolder((IFolder) m, processDepth + 1));
   }
}
