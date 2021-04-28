package com.digitax.exception;

public enum ErrorMessages {
	MeFClientSDK000001 ("A submission archive for a submission id cannot be found"),
	
	
	MeFClientSDK000002 ("Audit Log not found"),
	
	
	MeFClientSDK000003 ("Directory location not specified"),
	
	
	MeFClientSDK000004 ("File not found"),
	
	
	MeFClientSDK000005 ("Invalid EFIN"),
	
	
	MeFClientSDK000006 ("Invalid EIN"),
	
	
	MeFClientSDK000007 ("Invalid ETIN"),
	
	MeFClientSDK000008 ("Invalid Message ID"),
	
	
	MeFClientSDK000009 ("Invalid Postmark"),
	
	
	MeFClientSDK000010 ("Invalid reporting period"),
	
	
	MeFClientSDK000011 ("Invalid Submission ID"),
	
	
	MeFClientSDK000012 ("Invalid VIN"),
	
	
	MeFClientSDK000013 ("Invalid XML Schema"),
	
	
	MeFClientSDK000014 ("Logging configuration file was not found"),
	
	
	MeFClientSDK000015 ("MaxResults parameter is invalid"),
	
	
	MeFClientSDK000016 ("MaxResults size exceeds max limit"),
	
	
	MeFClientSDK000017 ("Mismatch in IRSData versus actual response attachment contents"),
	
	
	MeFClientSDK000018 ("Number of specified VINs exceeds max limit"),
	
	
	MeFClientSDK000019 ("Parser Error"),
	
	
	MeFClientSDK000020 ("The endpoint URL configuration file was not found"),
	
	
	MeFClientSDK000021 ("The SOAP message security configuration descriptor file could not be found"),
	
	
	MeFClientSDK000022 ("Toolkit operation can not proceed with logging level OFF"),
	
	
	MeFClientSDK000023 ("Unable to access file"),
	
	
	MeFClientSDK000024 ("Unable to access logging configuration file"),
	
	
	MeFClientSDK000025 ("Unable to create directory "),
	
	
	MeFClientSDK000026 ("Unable to load endpoint URLs from the properties file"),
	
	
	MeFClientSDK000027 ("Unable to save file"),
	
	
	MeFClientSDK000028 ("Unable to create zip archive"),
	
	
	MeFClientSDK000029 ("Unexpected Error"),
	
	
	MeFClientSDK000030 ("File attachment exceeds maximum size"),
	
	
	MeFClientSDK000031 ("Invalid parameter value"),
	
	
	MeFClientSDK000032 ("Client security configuration error"),
	
	
	MeFClientSDK000033 ("Message level security error"),
	
	
	MeFClientSDK000034 ("Unable to create security processor factory"),
	
	
	MeFClientSDK000036 ("Unable to create MeF Header"),
	
	
	MeFClientSDK000037 ("List of postmarked submissions archive can not be empty"),
	
	
	MeFClientSDK000038 ("Successful login is required prior to using this service"),
	
	
	MeFClientSDK000039 ("Invalid request parameters"),
	
	
	MeFClientSDK000040 ("The KeyStore is not loaded."),
	
	
	MeFClientSDK000041 ("No suitable Windows KeyStore provider could be found installed in the system."),
	
	
	MeFClientSDK000042 ("An error occurred while trying to load the Windows KeyStore."),
	
	
	MeFClientSDK000043 ("Password for KeyStore file cannot be null."),
	
	
	MeFClientSDK000044 ("A general error occurred loading the selected KeyStore file."),
	
	
	MeFClientSDK000045 ("An unexpected non-fatal exception occurred."),
	
	
	MeFClientSDK000046 ("Please check the keystore file password."),
	
	
	MeFClientSDK000047 ("The KeyStore file could not be loaded. Unsupported KeyStore type."),

	
	MeFClientSDK000048 ("Directory location specified but not valid."),
	
	
	MeFClientSDK000049 ("Private key not found in KeyStore file for alias."),
	
	
	MeFClientSDK000050 ("Certificate not found in KeyStore file for alias."),

	
	MeFClientSDK000051 ("File based method called on in-memory based object instance."),

	
	MeFClientSDK000052 ("In-memory based method called on file based object instance."),
	
	
	MeFClientSDK000053 ("Entry is outside of the target directory. Extracting may produce undesirable and potentially dangerous results. "),
	
	
	MeFClientSDK000054 ("ZIP Entry name contains invalid characters set forth in MeF business rule X0000-031.  See IRS Publication 4164 for details on allowed ZIP entry character values.");
	
	private final String message;
	
	ErrorMessages(String message) {
		this.message = message;
	}
	
	
	public String getErrorMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(name()).append(": ").append(this.message);
		return sb.toString();		
	}

}
