package com.digitax.mef;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitRuntimeException;
import com.digitax.util.FileUtil;

public class ApplicationContext {
	 public static final String A2A_TOOLKIT_HOME_PROPERTY = "A2A_TOOLKIT_HOME";

	    
	    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	   
	    public static final String NEWLINE = System.getProperty("line.separator");

	    
	    public static final String TOOLKIT_VERSION = "2020v11.1";

		private static ApplicationContext instance;

		private ConcurrentMap<Object, Object> customProperties;

		private File defaultResponseAttachmentSaveDir;

		private static final String className = ApplicationContext.class.getName();

		private ApplicationContext () {
			this.customProperties = new ConcurrentHashMap<Object, Object>();
			this.defaultResponseAttachmentSaveDir = ApplicationContext.getToolkitHome();
		}

		/**
		 * @return ApplicationContext The ApplicationContext instance.
		 */
		public static ApplicationContext getInstance() {
			if (ApplicationContext.instance == null) {
				createInstance();
			}
			return ApplicationContext.instance;
		}

		private static synchronized void createInstance() {
			if (ApplicationContext.instance == null) {
				ApplicationContext.instance = new ApplicationContext();
			}
		}

		/**
		 * First checks System Properties and if no valid value is found, checks
		 * for an Environment Variable.
		 *
		 * @return A java.io.File object representing the directory specified
		 *           by the System property A2A_TOOLKIT_HOME.
		 * @throws Throws a ToolkitRuntimeException if the
		 * 			 home directory is invalid or not set.
		 */
		public static final File getToolkitHome() {
			final String methodName = "getToolkitHome()";
		    String homePath = System.getProperty(A2A_TOOLKIT_HOME_PROPERTY);
		    if( homePath != null){
		    	// Set
		    	if (!isValidHomeDir(homePath)) {
		    		// But not valid
		    		String msg = ErrorMessages.MeFClientSDK000048.getErrorMessage() +
	    			"; " + className + "; " + methodName + "; "+homePath;
		    		ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
		    		tkre.setLogged(false);
		    	    throw tkre;
		    	} else {
		    		// valid
		    		return new File(homePath);
		    	}
		    } else {
		    	// System property not set. Try environment variable
		    	homePath = System.getenv(A2A_TOOLKIT_HOME_PROPERTY);
		    	if( homePath != null){
		 	    	// Set
		 	    	if (!isValidHomeDir(homePath)) {
		 	    		// But not valid
			    		String msg = ErrorMessages.MeFClientSDK000048.getErrorMessage() +
		    			"; " + className + "; " + methodName + "; "+homePath;
			    		ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
			    		tkre.setLogged(false);
			    	    throw tkre;
		 	    	} else {
		 	    		// valid
		 	    		return new File(homePath);
		 	    	}
		    	} else {
		    		// neither one set
		    		String msg = ErrorMessages.MeFClientSDK000003.getErrorMessage() +
		    			"; " + className + "; " + methodName;
		    		ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
		    		tkre.setLogged(false);
		    	    throw tkre;
		    	}
		    }
		}

		/**
		 * @return A java.io.File object representing the configuration file directory
		 * 			 for the MeF-Client-SDK.
		 */
		public static final File getConfigDir() {
			return new File(getToolkitHome(), "config");
		}

		/**
		 * A convenience method for programatically setting the home directory of the MeF-Client-SDK toolkit.
		 * @param installRootDir The absolute system path to where the MeF-Client-SDK toolkit resides.
		 * @throws ToolkitRuntimeException If the supplied input String, installRootDir, does not resolve to a valid
		 *                            system path.
		 */
		public static final void setToolkitHome(String installRootDir) {

		    if (!isValidHomeDir(installRootDir)) {
				String methodName = "setToolkitHome()";
				String msg = ErrorMessages.MeFClientSDK000003.getErrorMessage() +
					"; " + className + "; " + methodName;
	    		ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
	    		tkre.setLogged(false);
	    	    throw tkre;
		    }
		    System.setProperty(A2A_TOOLKIT_HOME_PROPERTY, installRootDir);
		}

		private static final boolean isValidHomeDir(String homeDir) {
		    boolean valid = homeDir != null;
		    if (valid) {
		        File rootDir = new File(homeDir);
		        if (!rootDir.exists() ||
		            !rootDir.isDirectory() ||
		            !rootDir.isAbsolute()) {
		            valid = false;
		        }
		    }
		    return valid;
		}

		/**
		 * Get a custom property value.
		 * @param key Key to the property value you wish to retrieve.
		 * @return The property value or else null if no object corresponding to the given key exists.
		 */
		public Object getProperty(Object key) {
			return this.customProperties.get(key);
		}

		/**
		 * Remove a custom property value.
		 * @param key Key value of the property to remove.
		 * @return The object that was removed or null if no object for the given
		 * 			 key existed.
		 */
		public Object removeProperty(Object key) {
			return this.customProperties.remove(key);
		}

		/**
		 * Add a custom property to the ApplicationContext.
		 * @param key Key for subsequent lookup or removal of supplied value.
		 * @param value The property value to set.
		 */
		public void setProperty(Object key, Object value) {
			this.customProperties.put(key, value);
		}

		/**
		 * @see #setDefaultResponseAttachmentDir(File defaultResponseAttachmentSaveDir)
		 * @return A java.io.File object representing what should be a valid,
		 * 			 existing, and writeable directory on the file system.
		 */
		public File getDefaultResponseAttachmentDir() {
			return this.defaultResponseAttachmentSaveDir;
		}

		/**
		 * Set a default file system directory to which any attachments to service
		 * response messages will be saved.
		 *
		 * @param defaultResponseAttachmentSaveDir a java.io.File object which represents
		 * 											 what should be a valid, existing, and
		 * 											 writeable directory on the file system.
		 */
		public void setDefaultResponseAttachmentDir(File defaultResponseAttachmentSaveDir) {
			String methodName = "setDefaultResponseAttachmentDir()";
			if (!FileUtil.isValidSaveDirectory(defaultResponseAttachmentSaveDir)) {
				String msg = ErrorMessages.MeFClientSDK000027.getErrorMessage() +
					"; " + className + "; " + methodName + "; " +
					(defaultResponseAttachmentSaveDir == null ? "" : defaultResponseAttachmentSaveDir.getPath());
	    		ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
	    		tkre.setLogged(false);
	    	    throw tkre;
			}
			this.defaultResponseAttachmentSaveDir = defaultResponseAttachmentSaveDir;
		}

		/**
		 * Get a handle to the MeF-Client-SDK toolkit logging.properties file.
		 * @return the logging.properties file for the MeF-Client-SDK toolkit as a
		 * 			 java.io.File object.
		 */
		public static File getLoggingConfigFile() {
			return new File(getConfigDir(), "logging.properties");
		}

}
