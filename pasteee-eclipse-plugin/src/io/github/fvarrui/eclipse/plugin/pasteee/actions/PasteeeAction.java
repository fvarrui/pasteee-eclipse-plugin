package io.github.fvarrui.eclipse.plugin.pasteee.actions;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.texteditor.ITextEditor;

import io.github.fvarrui.eclipse.plugin.pasteee.PluginActivator;
import io.github.fvarrui.eclipse.plugin.pasteee.api.PasteApi;
import io.github.fvarrui.eclipse.plugin.pasteee.preferences.PreferenceConstants;

public class PasteeeAction extends ActionDelegate implements IEditorActionDelegate {
	
	private ILog log = Platform.getLog(getClass());
	private String selectionText = null;
	private Shell shell;

	@Override
	public void run(IAction action) {
		
		log.info("running action: " + action.getId());

		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();			
		
		String filename = editor.getTitle();			

		// gets api key from preferences
		IPreferenceStore prefs = new ScopedPreferenceStore(InstanceScope.INSTANCE, PluginActivator.PLUGIN_ID);
		String apiKey = prefs.getString(PreferenceConstants.API_DEV_KEY);
		
		try {
			
			String url = PasteApi.paste(apiKey, selectionText, filename);
			
			copyToClipboard(url);
			
			MessageDialog.openInformation(
					shell, 
					PluginActivator.PLUGIN_NAME, 
					"Your selection has been copied to Paste.ee." + "\n" +
					"The URL " + url + " is on your clipboard."
			);
			
		} catch (IllegalArgumentException e) { 

			MessageDialog.openError(
					shell, 
					PluginActivator.PLUGIN_NAME, 
					"You must set an API KEY in Paste.ee Plugin preferences page."
			);
			
		} catch (Exception e) {

			MessageDialog.openError(
					shell, 
					PluginActivator.PLUGIN_NAME, 
					e.getMessage()
			);
			
		}

		log.info("action finished!");
		
	}

	private void copyToClipboard(String content) {
		Clipboard cb = new Clipboard(this.shell.getDisplay());
		cb.setContents(new Object[] { content }, new Transfer[] { TextTransfer.getInstance() });
		cb.dispose();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (ITextSelection.class.isAssignableFrom(selection.getClass())) {
			ITextSelection txtSelection = (ITextSelection) selection;
			if (txtSelection == null || txtSelection.isEmpty() || txtSelection.getText().trim().equals("")) {
				IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			    IEditorInput input = (IEditorInput) editor.getEditorInput();
				IDocument document = ((ITextEditor)editor).getDocumentProvider().getDocument(input);
				this.selectionText = document.get();				
			} else {
				this.selectionText = txtSelection.getText();
			}
		}
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart editorPart) {
		shell = editorPart.getSite().getShell();
	}

}
