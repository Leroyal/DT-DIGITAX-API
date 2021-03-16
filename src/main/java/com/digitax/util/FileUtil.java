package com.digitax.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitException;
import com.digitax.exception.ToolkitRuntimeException;

public class FileUtil {
	private static final String className = FileUtil.class.getName();
	protected static Logger logger = LogUtil.getLogger(FileUtil.class.getName());
	public static byte[] fileAsByteArray(File aFile) throws ToolkitException {
		String methodName = "fileAsByteArray()";
		if (aFile == null ||
			!aFile.exists() ||
			!aFile.isFile() ||
			aFile.length() <= 0) {
			String msg = ErrorMessages.MeFClientSDK000004.getErrorMessage() +
			"; " + className + "; " + methodName;
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
			logger.log(Level.SEVERE, msg, tkre);
			throw tkre;
		}
		BufferedInputStream inStream = null;
		byte[] returnBytes = null;
		try {
			inStream = new BufferedInputStream(new FileInputStream(aFile));
			long fileSize = aFile.length();
			if (fileSize > Integer.MAX_VALUE) {
				String msg = ErrorMessages.MeFClientSDK000030.getErrorMessage() +
					"; " + className + "; " + methodName + "; " + aFile.getPath();
				ToolkitException tke = new ToolkitException(msg);
				logger.log(Level.SEVERE, msg, tke);				
				throw tke;
			}
			returnBytes = new byte[(int)fileSize];
			int offset = 0;
			int numread = 0;
			while (offset < returnBytes.length &&
				   (numread = inStream.read(returnBytes, offset, returnBytes.length - offset)) >= 0) {
				offset += numread;
			}
			
			if (offset < returnBytes.length) {
				throw new IOException(ErrorMessages.MeFClientSDK000023.getErrorMessage() +
						"; " + className + "; " + methodName + "; " + aFile.getPath());
			}
		} catch (FileNotFoundException e) {
			String msg = ErrorMessages.MeFClientSDK000004.getErrorMessage() +
				"; " + className + "; " + methodName + "; " + aFile.getPath();
			ToolkitException tke = new ToolkitException(msg, e);
			logger.log(Level.SEVERE, msg, tke);				
			throw tke;
		} catch (IOException e) {
			String msg = ErrorMessages.MeFClientSDK000029.getErrorMessage() +
				"; " + className + "; " + methodName + "; " + aFile.getPath() + "; " + e.getMessage();
			ToolkitException tke = new ToolkitException(msg, e);
			logger.log(Level.SEVERE, msg, tke);				
			throw tke;
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException ignore) {
					logger.warning("Could not close file input stream: " + ignore.getMessage());
				}
			}
		}
		return returnBytes;
	}
	
	/**
	 * Convenience method for testing if a given file system directory is a suitable
	 * for saving files into.
	 * 
	 * @param saveDir a File object that will be tested for the following conditions:
	 * 					<ul>
	 * 					<li>non-null</li>
	 * 					<li>isDirectory()</li>
	 * 					<li>canWrite()</li>
	 * 					</ul>
	 * 
	 * @return <tt>true</tt> if all the above conditions are true, otherwise <tt>false</tt>.
	 */
	public static boolean isValidSaveDirectory(File saveDir) {
		return saveDir != null && saveDir.isDirectory() && saveDir.canWrite();
	}
}
