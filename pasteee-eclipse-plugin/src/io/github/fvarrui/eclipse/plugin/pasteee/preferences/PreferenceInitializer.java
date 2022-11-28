package io.github.fvarrui.eclipse.plugin.pasteee.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import io.github.fvarrui.eclipse.plugin.pasteee.PluginActivator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = PluginActivator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.API_DEV_KEY, "");
	}

}
