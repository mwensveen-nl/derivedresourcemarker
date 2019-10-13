package nl.mwensveen.eclipse.drm.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class DRMPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
    public DRMPreferencePage() {
    }

    // The list that displays the current folderNames
    private List folderNameList;
    // The newFolderNameText is the text where new folderNames are specified
    private Text newfolderNameText;

    // The list that displays the current fileNames
    private List fileNameList;
    // The newfileNameText is the text where new folderNames are specified
    private Text newFileNameText;

    // The list that displays the current pomPackagings
    private List pomPackagingList;
    // The newPomPackagingText is the text where new pomPackaging are specified
    private Text newPomPackagingText;
    private GridData data_1;

    /*
     * @see PreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {

        Composite entryTable = new Composite(parent, SWT.NULL);

        // Create a data that takes up the extra space in the dialog .
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        entryTable.setLayoutData(data);

        GridLayout layout = new GridLayout();
        entryTable.setLayout(layout);

        Label label = new Label(entryTable, SWT.NONE);
        label.setText("Folder Names");

        folderNameList = new List(entryTable, SWT.BORDER + SWT.V_SCROLL);
        folderNameList.setItems(getFolderNamePrefs());

        // Create a data that takes up the extra space in the dialog and spans
        // both columns.
        data_1 = new GridData(GridData.FILL_BOTH);
        data_1.widthHint = 352;
        folderNameList.setLayoutData(data_1);

        Composite buttonComposite = new Composite(entryTable, SWT.NULL);

        GridLayout buttonLayout = new GridLayout();
        buttonLayout.numColumns = 2;
        buttonComposite.setLayout(buttonLayout);

        // Create a data that takes up the extra space in the dialog and spans
        // both columns.
        data = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING);
        buttonComposite.setLayoutData(data);

        Button addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);

        addButton.setText("Add to List"); //$NON-NLS-1$
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                folderNameList.add(newfolderNameText.getText(), folderNameList.getItemCount());
                newfolderNameText.setText("");
            }
        });

        newfolderNameText = new Text(buttonComposite, SWT.BORDER);
        // Create a data that takes up the extra space in the dialog .
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        newfolderNameText.setLayoutData(data);

        Button removeButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);

        removeButton.setText("Remove Selection"); //$NON-NLS-1$
        removeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                folderNameList.remove(folderNameList.getSelectionIndex());
            }
        });

        data = new GridData();
        data.horizontalSpan = 2;
        removeButton.setLayoutData(data);

        /////////////////////////////////////////
        label = new Label(entryTable, SWT.NONE);
        label.setText("Pom Packaging");

        pomPackagingList = new List(entryTable, SWT.BORDER + SWT.V_SCROLL);
        pomPackagingList.setItems(getPomPackagingPrefs());

        // Create a data that takes up the extra space in the dialog and spans
        // both columns.
        data = new GridData(GridData.FILL_BOTH);
        pomPackagingList.setLayoutData(data);

        buttonComposite = new Composite(entryTable, SWT.NULL);

        buttonLayout = new GridLayout();
        buttonLayout.numColumns = 2;
        buttonComposite.setLayout(buttonLayout);

        // Create a data that takes up the extra space in the dialog and spans
        // both columns.
        data = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING);
        buttonComposite.setLayoutData(data);

        addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);

        addButton.setText("Add to List"); //$NON-NLS-1$
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                pomPackagingList.add(newPomPackagingText.getText(), pomPackagingList.getItemCount());
                newPomPackagingText.setText("");
                ;
            }
        });

        newPomPackagingText = new Text(buttonComposite, SWT.BORDER);
        // Create a data that takes up the extra space in the dialog .
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        newPomPackagingText.setLayoutData(data);

        removeButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);

        removeButton.setText("Remove Selection"); //$NON-NLS-1$
        removeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                pomPackagingList.remove(pomPackagingList.getSelectionIndex());
            }
        });

        data = new GridData();
        data.horizontalSpan = 2;
        removeButton.setLayoutData(data);

        ////////////////////////////////////////////////////

        label = new Label(entryTable, SWT.NONE);
        label.setText("File Names");

        fileNameList = new List(entryTable, SWT.BORDER + SWT.V_SCROLL);
        fileNameList.setItems(getFileNamePrefs());

        // Create a data that takes up the extra space in the dialog and spans
        // both columns.
        data_1 = new GridData(GridData.FILL_BOTH);
        data_1.widthHint = 352;
        fileNameList.setLayoutData(data_1);

        buttonComposite = new Composite(entryTable, SWT.NULL);

        buttonLayout = new GridLayout();
        buttonLayout.numColumns = 2;
        buttonComposite.setLayout(buttonLayout);

        // Create a data that takes up the extra space in the dialog and spans
        // both columns.
        data = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING);
        buttonComposite.setLayoutData(data);

        addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);

        addButton.setText("Add to List"); //$NON-NLS-1$
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                fileNameList.add(newFileNameText.getText(), fileNameList.getItemCount());
                newFileNameText.setText("");
            }
        });

        newFileNameText = new Text(buttonComposite, SWT.BORDER);
        // Create a data that takes up the extra space in the dialog .
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        newFileNameText.setLayoutData(data);

        removeButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);

        removeButton.setText("Remove Selection"); //$NON-NLS-1$
        removeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                fileNameList.remove(fileNameList.getSelectionIndex());
            }
        });

        data = new GridData();
        data.horizontalSpan = 2;
        removeButton.setLayoutData(data);

        return entryTable;
    }

    /*
     * @see IWorkbenchPreferencePage#init(IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "nl.mwensveen.eclipse.plugins.drm-plugin"));
    }

    /**
     * Performs special processing when this page's Restore Defaults button has
     * been pressed. Sets the contents of the nameEntry field to be the default
     */
    @Override
    protected void performDefaults() {
        Names defaultPreferences = PreferenceManager.getDefaultPreferencesForFolderNames();
        folderNameList.setItems(defaultPreferences.getNames().toArray(new String[0]));
        defaultPreferences = PreferenceManager.getDefaultPreferencesForPomPackaging();
        pomPackagingList.setItems(defaultPreferences.getNames().toArray(new String[0]));
        defaultPreferences = PreferenceManager.getDefaultPreferencesForFileNames();
        fileNameList.setItems(defaultPreferences.getNames().toArray(new String[0]));
    }

    /**
     * Method declared on IPreferencePage. Save the author name to the
     * preference store.
     */
    @Override
    public boolean performOk() {
        Names names = new Names();
        names.replace(folderNameList.getItems());
        PreferenceManager.savePreferencesForFolderNames(names);
        names = new Names();
        names.replace(pomPackagingList.getItems());
        PreferenceManager.savePreferencesForPomPackaging(names);
        names = new Names();
        names.replace(fileNameList.getItems());
        PreferenceManager.savePreferencesForFileNames(names);
        return super.performOk();
    }

    private String[] getFolderNamePrefs() {
        Names folderNames = PreferenceManager.getPreferencesForFolderName();
        return folderNames.getNames().toArray(new String[0]);
    }

    private String[] getPomPackagingPrefs() {
        Names pomPackaging = PreferenceManager.getPreferencesForPomPackaging();
        return pomPackaging.getNames().toArray(new String[0]);
    }

    private String[] getFileNamePrefs() {
        Names folderNames = PreferenceManager.getPreferencesForFileName();
        return folderNames.getNames().toArray(new String[0]);
    }
}