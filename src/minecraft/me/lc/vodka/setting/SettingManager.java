package me.lc.vodka.setting;

import java.util.ArrayList;

import me.lc.vodka.module.Module;

public class SettingManager {

	/** The ArrayList that holds all the settings. **/
	private ArrayList<Setting> settings;

	public SettingManager() {
		this.settings = new ArrayList<Setting>();
	}

	/** Adds a setting to the ArrayList that holds them. **/
	public void addSetting(Setting s) {
		this.settings.add(s);
	}

	/** Gets a setting for the module provided and the name provided. **/
	public Setting getSetting(Module m, String name) {
		for (Setting s : this.settings) {
			if (s.getModule().equals(m) && s.getName().equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}

	/** Returns an ArrayList of all the settings. **/
	public ArrayList<Setting> getSettings() {
		return this.settings;
	}

	/** Returns the settings that belong to the module provided. **/
	public ArrayList<Setting> getSettingsForModule(Module m) {
		ArrayList<Setting> settings = new ArrayList<Setting>();

		/**
		 * Goes through all the settings and returns the settings that belong to that
		 * module.
		 **/
		for (Setting s : this.settings) {
			if (s.getModule().equals(m))
				settings.add(s);
		}

		/** Checks if no settings were found and returns null. **/
		if (settings.isEmpty())
			return null;

		return settings;
	}

	public Setting getSettingByName(String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name)){
				return set;
			}
		}
		return null;
	}

}
