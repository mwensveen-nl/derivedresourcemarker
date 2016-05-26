package nl.mwensveen.derivedresourcemarker.action;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;

public class PopupDialog extends Dialog {
	public static final int MARK_UNMARK_ID = IDialogConstants.CLIENT_ID + 2;
	public static final int MARK_ONLY_ID = IDialogConstants.CLIENT_ID + 1;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public PopupDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.APPLICATION_MODAL);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		Label imageLabel = new Label(container, SWT.NONE);
		imageLabel.setImage(ResourceManager.getPluginImage("drm-plugin", "icons/icon32x32.png"));

		Label textLabel = new Label(container, SWT.WRAP | SWT.CENTER);
		GridData gd_textLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textLabel.widthHint = 315;
		gd_textLabel.heightHint = 32;
		textLabel.setLayoutData(gd_textLabel);
		textLabel.setText("Derived Resource Marker.");

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		Button button = createButton(parent, MARK_UNMARK_ID, "Mark / Unmark", false);
		button.setToolTipText("Mark Resources that are derived, unMark the other resources");
		button = createButton(parent, MARK_ONLY_ID, "Mark Only", true);
		button.setToolTipText("Only Mark new Derived Resources, do not change the other resources");
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 162);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (MARK_ONLY_ID == buttonId || MARK_UNMARK_ID == buttonId) {
			setReturnCode(buttonId);
			close();
		} else {
			super.buttonPressed(buttonId);
		}
	}
}
