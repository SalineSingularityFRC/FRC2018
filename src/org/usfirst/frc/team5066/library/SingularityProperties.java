package org.usfirst.frc.team5066.library;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Class for reading and writing to properties files.
 * 
 * @author Saline Singularity 5066
 * 
 * @version 2.0
 */

public class SingularityProperties {

	private Properties defaultProps;
	private Properties props;
	private String propFileURL;

	/**
	 * Constructor for SingularityPropsReader
	 * 
	 * @param propFileURL
	 *            Which file to use as the properties file
	 * @throws IOException
	 *             If the file is invalid or lacks read access
	 */
	public SingularityProperties(String propFileURL) throws IOException {
		this.propFileURL = propFileURL;
		props = readProperties(propFileURL);
		defaultProps = new Properties();
	}

	public SingularityProperties() {
		this.propFileURL = null;
		defaultProps = new Properties();
	}

	/**
	 * Used to get the file URL
	 * 
	 * @return Which file is being read
	 */
	public String getFileURL() {
		return propFileURL;
	}

	/**
	 * Used to add a property to the default properties. A default property is
	 * used when a property fails to load from the properties file. Default
	 * properties are only backups in case a true property fails to load.
	 * 
	 * @param propName
	 *            The name of the default property. Must match the name of the
	 *            property in the properties file that this value is the default
	 *            for
	 * @param value
	 *            The value to which the default property will be set The value
	 *            to which the default property will be set
	 */
	public void addDefaultProp(String propName, int value) {
		defaultProps.setProperty(propName, "" + value);
	}

	/**
	 * Used to add a property to the default properties. A default property is
	 * used when a property fails to load from the properties file. Default
	 * properties are only backups in case a true property fails to load.
	 * 
	 * @param propName
	 *            The name of the default property. Must match the name of the
	 *            property in the properties file that this value is the default
	 *            for
	 * @param value
	 *            The value to which the default property will be set
	 */
	public void addDefaultProp(String propName, double value) {
		defaultProps.setProperty(propName, "" + value);
	}

	/**
	 * Used to add a property to the default properties. A default property is
	 * used when a property fails to load from the properties file. Default
	 * properties are only backups in case a true property fails to load.
	 * 
	 * @param propName
	 *            The name of the default property. Must match the name of the
	 *            property in the properties file that this value is the default
	 *            for
	 * @param value
	 *            The value to which the default property will be set
	 */
	public void addDefaultProp(String propName, float value) {
		defaultProps.setProperty(propName, "" + value);
	}

	/**
	 * Used to add a property to the default properties. A default property is
	 * used when a property fails to load from the properties file. Default
	 * properties are only backups in case a true property fails to load.
	 * 
	 * @param propName
	 *            The name of the default property. Must match the name of the
	 *            property in the properties file that this value is the default
	 *            for
	 * @param value
	 *            The value to which the default property will be set
	 */
	public void addDefaultProp(String propName, boolean value) {
		defaultProps.setProperty(propName, "" + value);
	}

	/**
	 * Used to add a property to the default properties. A default property is
	 * used when a property fails to load from the properties file. Default
	 * properties are only backups in case a true property fails to load.
	 * 
	 * @param propName
	 *            The name of the default property. Must match the name of the
	 *            property in the properties file that this value is the default
	 *            for
	 * @param value
	 *            The value to which the default property will be set
	 */
	public void addDefaultProp(String propName, String value) {
		if (defaultProps.containsKey(propName)) {
			DriverStation.reportError("Default property with name \"" + propName + "\n aready exists... Overwriting",
					false);
		}
		defaultProps.setProperty(propName, value);
	}

	/**
	 * Rereads file properties
	 * 
	 * @throws IOException
	 *             If the file is invalid or lacks read access
	 */
	public void reloadProperties() throws IOException {
		props = readProperties(propFileURL);
	}

	/**
	 * Actually reads the property file
	 * 
	 * @param propFileURL
	 *            Which file to read
	 * @return An object which encapsulates the properties found in the given
	 *         file
	 * @throws IOException
	 *             If the file lacks read access
	 */
	private Properties readProperties(String propFileURL) throws IOException {
		Properties prop = new Properties();
		String propURL = propFileURL;
		FileInputStream fileInputStream;

		fileInputStream = new FileInputStream(propURL);
		prop.load(fileInputStream);
		fileInputStream.close();

		return prop;
	}

	/**
	 * Sets a certain property in the file to a certain object
	 * 
	 * @param propName
	 *            Which property to change
	 * @param o
	 *            What to change it to (uses the {@code .toString()} method)
	 * @throws IOException
	 *             If file is not valid or does not allow write access
	 */
	public void setProperty(String propName, Object o) throws IOException {
		
		//TODO fix this method. Right now it saves all Properties to the file in a pattern that
		//is in no particular order. It also delets all comments and original spacing.
		
		FileOutputStream out = new FileOutputStream(propFileURL);

		props.setProperty(propName, o.toString());
		props.store(out, null);
		out.close();
		reloadProperties();
	}

	/**
	 * Method to find access a certain string in the properties file
	 * 
	 * @param name
	 *            Which string to get
	 * @return The value of the string
	 * @throws SingularityPropertyNotFoundException
	 */
	public String getString(String name) throws SingularityPropertyNotFoundException {
		return loadProperty(name);
	}

	/**
	 * Method to find access a certain integer in the properties file
	 * 
	 * @param name
	 *            Which integer to get
	 * @return The value of the integer
	 * @throws SingularityPropertyNotFoundException
	 * @throws NumberFormatException
	 */
	public int getInt(String name) throws NumberFormatException, SingularityPropertyNotFoundException {
		return Integer.parseInt(loadProperty(name));
	}

	/**
	 * Method to find access a certain float in the properties file
	 * 
	 * @param name
	 *            Which float to get
	 * @return The value of the float
	 * @throws SingularityPropertyNotFoundException
	 * @throws NumberFormatException
	 */
	public float getFloat(String name) throws NumberFormatException, SingularityPropertyNotFoundException {
		return Float.parseFloat(loadProperty(name));
	}

	/**
	 * Method to find access a certain double in the properties file
	 * 
	 * @param name
	 *            Which double to get
	 * @return The value of the double
	 * @throws SingularityPropertyNotFoundException
	 * @throws NumberFormatException
	 */
	public double getDouble(String name) throws NumberFormatException, SingularityPropertyNotFoundException {
		return Double.parseDouble(loadProperty(name));
	}

	/**
	 * Method to find access a certain boolean in the properties file
	 * 
	 * @param name
	 *            Which boolean to get
	 * @return The value of the boolean
	 * @throws SingularityPropertyNotFoundException
	 */
	public boolean getBoolean(String name) throws SingularityPropertyNotFoundException {
		return Boolean.parseBoolean(loadProperty(name));
	}

	private String loadProperty(String name) throws SingularityPropertyNotFoundException {
		String prop;
		if (propFileURL != null) {
			prop = props.getProperty(name);
		} else {
			prop = null;
		}
		// TODO - accomplish this better with a do{} - while()?
		if (prop != null) {
			return prop;
		} else {
			// Note - all messages such as the following are automatically
			// logged by DriverStation
			DriverStation.reportError("Failed to find property in file: " + name
					+ "\n - Resorting to default property for " + name + "\n \n .", false);
			prop = defaultProps.getProperty(name);
			if (prop != null) {
				return prop;
			} else {
				// Note - all messages such as the following are automatically
				// logged by DriverStation
				DriverStation.reportError(
						"Failed to find property in defaults: " + name + "\n - THROWING EXCEPTION!!! \n \n ...", false);

				SingularityPropertyNotFoundException spnfe = new SingularityPropertyNotFoundException(name);
				throw spnfe;
			}
		}

		// TODO make it so that all properties that are missing can be found and
		// pointed out if using a single loadProperties() method.
	}
}