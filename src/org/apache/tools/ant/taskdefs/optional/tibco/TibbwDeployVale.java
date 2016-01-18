package org.apache.tools.ant.taskdefs.optional.tibco;

import java.io.File;


/**
 * TibbwDeploy is an ant task for deploying BusinessWorks applications to a tibco domain. 
 * 
 * @author Cdric Rochet
 *
 */
public class TibbwDeployVale extends AbstractTibbwTask {

    /**
     * Default constructor
     */
    public TibbwDeployVale() {
        super(".ear"); 
    }
    
    /** the domain where the tibco businessworks ear should be deployed * */
	protected String domain; 
    /** the credential file where there are user and password * */
	protected String cred;
    /** Base where project have integrations * */
	protected String appBase;

    /**
     * Create the string which reprensents the command line to execute.
     */
    protected String createCommand(File currentFile) {
   	 String fileNameNoExtension = this.subStringBeforeFirst(currentFile.getName(), ".");

        String command = this.bindir.getPath() + File.separator + "AppManage -deploy -ear " + currentFile 
		 	+ " -deployConfig " + this.srcdir.getPath()  + File.separator + fileNameNoExtension + ".xml"
			+ " -domain " + this.domain + " -cred " + this.cred +  " -app " + this.appBase +"/"+ fileNameNoExtension +  " -force -nostart ";

        return command;
   }

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the cred
	 */
	public String getCred() {
		return cred;
	}

	/**
	 * @param cred the cred to set
	 */
	public void setCred(String cred) {
		this.cred = cred;
	}

	/**
	 * @return the appBase
	 */
	public String getAppBase() {
		return appBase;
	}

	/**
	 * @param appBase the appBase to set
	 */
	public void setAppBase(String appBase) {
		this.appBase = appBase;
	}    


	
}
