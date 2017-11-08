package org.apache.tools.ant.taskdefs.optional.tibco;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;


/**
 * TibbwBuildEar is an ant task for building Tibco BusinessWorks ear.
 *
 * @author Cedric Rochet
 *
 */
public class TibbwBuildEarVale extends AbstractTibbwTask {
    //~ Instance fields ============================================================================================================

    // ============================================================================================================
    // ============================================================================================================
   // protected File currentArchiveFile;

    /** the directory where the tibco businessworks ear are built * */
    protected File destdir;
    protected File projectDir;
    protected String archiveProjectPath;

    //~ Constructors ===============================================================================================================

    // ===============================================================================================================
    // ===============================================================================================================

    /**
     * Default constructor
     */
    public TibbwBuildEarVale() {
        super(".archive");
        this.destdir = null;

        this.projectDir = null;
        this.archiveProjectPath = null;

    }

    //~ Methods ====================================================================================================================

    /**
     * @param archiveProjectPath The archiveProjectPath to set.
     */
    public void setArchiveProjectPath(String archiveProjectPath) {
        this.archiveProjectPath = archiveProjectPath;
    }

    /**
     * @param bindir The bindir to set.
     */
    public void setBindir(File bindir) {
        this.bindir = bindir;
    }

    /**
     * @return Returns the bindir.
     */
    public File getBindir() {
        return bindir;
    }

    /**
     * @param destdir The destdir to set.
     */
    public void setDestdir(File destdir) {
        this.destdir = destdir;
    }

    /**
     * @return Returns the destdir.
     */
    public File getDestdir() {
        return destdir;
    }

    /**
     * @param projectDir The projectDir to set.
     */
    public void setProjectDir(File projectDir) {
        this.projectDir = projectDir;
    }

    /**
     * @return Returns the projectDir.
     */
    public File getProjectDir() {
        return projectDir;
    }

    /**
     * @param srcdir
     *            The srcdir to set.
     */
    public void setSrcdir(File srcdir) {
        this.srcdir = srcdir;
    }

    /**
     * @return Returns the srcdir.
     */
    public File getSrcdir() {
        return srcdir;
    }

    // ====================================================================================================================
    // ====================================================================================================================
    protected String createCommand(File currentFile) {

        this.findArchiveProjectPath(currentFile);

        @SuppressWarnings("unused")
		String projectName = projectDir.getName();
        
        String integratrionName = subStringBeforeFirst(currentFile.getName(),".");

        String earPath = this.destdir + File.separator
            + integratrionName + ".ear";

        String command = this.bindir.getPath() + File.separator + "buildear -s -ear \"" + this.archiveProjectPath + "\" -o \"" + earPath
            + "\" -p \"" + projectDir.getPath() + "\" --propFile \""+this.bindir.getPath() + File.separator + "buildear.tra\" -x -s logFile=\"" + projectDir.getPath() + "\\myLogFile.txt\"";

        
        try{
        	File xml = new File(this.archiveProjectPath.replace("archive", "xml"));
        	if(xml.exists())
        	{
        		Files.copy(xml.toPath(), new FileOutputStream(new File(this.destdir + File.separator
        	            + integratrionName+".xml")));
        	}
        }catch(Throwable e){
        	e.printStackTrace();
        }
        
        return command;
    }

    /**
     * Determine the businessworks project relative path of the archive
     * @return the businessworks project relative path of the archive
     */
    protected void findArchiveProjectPath(File currentArchiveFile) {
        boolean datIsFound = false;
        String archivePathInProject = new String();
        String archiveName = currentArchiveFile.getName();
        File currentDirFile = currentArchiveFile.getParentFile();

        for (; (currentDirFile != null) && !datIsFound; currentDirFile = currentDirFile.getParentFile()) {
            File[] listOfDatFile = currentDirFile.listFiles(new EndOfFileFilter(".dat"));

            if ((listOfDatFile != null) && (listOfDatFile.length > 0)) {
                datIsFound = true;
                archivePathInProject = '/' + archivePathInProject;
                this.projectDir = currentDirFile;
            } else {
                archivePathInProject = currentDirFile.getName() + '/' + archivePathInProject;
            }
        }

        this.archiveProjectPath = datIsFound ? (archivePathInProject + archiveName) : null;
    }
}
