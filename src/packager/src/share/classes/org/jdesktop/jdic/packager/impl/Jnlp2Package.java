/*
 * Copyright (C) 2004 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */ 

package org.jdesktop.jdic.packager.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.net.URL;
import java.util.Iterator;

/**
 * This class generates an platform dependent installable package with the given
 * arguments.
 * <p>
 * On Windows, the generated package is in msi format.
 * <p>
 * On Linux, the generated package is in rpm format.
 * <p>
 * On Solaris, the generated package is in pkg format.
 *
 */
public final class Jnlp2Package {
    /**
     * Jnlp2Packager creator.
     *
     */
    private Jnlp2Package() {
    }

    /**
     * Checks boolean properties and reports errors for illegal
     * values(not true or false).
     * @param propertyName The given property name.
     * @return True if the property contains valid value.
     */
    private static boolean getBoolProperty(String propertyName) {
        String propertyValue = System.getProperty(propertyName);
        boolean retValue = false;

        if (propertyValue == null) {
            retValue = false;
        } else {
            if (propertyValue.equalsIgnoreCase("true")) {
                retValue = true;
            } else if (propertyValue.equalsIgnoreCase("false")) {
                retValue = false;
            } else {
                throw new IllegalArgumentException(
                    "The value of property " + propertyName
                    + " can only be either true or false.");
            }
        }

        return retValue;
    }

    /**
     * Checks all the input arguments in the command line, and returns
     * a JnlpPackageInfo object containning the package related information.
     * <p>
     * The JnlpPackageInfo object will be used to generate a installable
     * package.
     *
     * @param args Arguments to be parsed.
     * @return The parsed JnlpPackageInfo.
     * @throws IOException If failed to parse the arguments.
     */
    private static JnlpPackageInfo parseArguments(String[] args)
            throws IOException {
        String version = null;
        String release = null;
        boolean showLicense = false;
        String licenseDirName = null;
        String bannerJpgFileName = null;
        String panelJpgFileName = null;
        boolean shortcutEnabled = false;
        boolean associationEnabled = false;
        boolean systemCacheEnabled = false;
        /////////////////////////////////////////////////////////////
        // Check the jnlp file path argument.
        if (args.length < 1) {
            throw new IllegalArgumentException(
                        "Please specify the jnlp file path.");
        }
        JnlpPackageInfo pkgInfo = new JnlpPackageInfo();
        pkgInfo.setJnlpFilePath(
            getValidFileArgument(args[0], "jnlp file path", false));
        checkJnlpFilePathArgument(pkgInfo);
        /////////////////////////////////////////////////////////////
        // Check the ResourcePath property.
        // If the ResourcePath property is set, and check if it points to an
        // existing directory, and if it's readable.
        checkResourcePathArgument(pkgInfo);
        /////////////////////////////////////////////////////////////
        // Check PackageName property.
        checkPackageNameArgument(pkgInfo);
        /////////////////////////////////////////////////////////////
        // Check OutputDir property.
        // If the OutputDir is not set, set default outputdir as current dir.
        checkOutputDirNameArgument(pkgInfo);
        /////////////////////////////////////////////////////////////
        // Check Version property.
        version = System.getProperty(JnlpConstants.PROPERTY_NAME_VERSIONNO);
        if (version == null) {
            version = JnlpConstants.DEFAULT_VERSION_NUM;
        }
        pkgInfo.setVersion(version);
        /////////////////////////////////////////////////////////////
        // Check Release property.
        release = System.getProperty(JnlpConstants.PROPERTY_NAME_RELEASENO);
        if (release == null) {
            release = JnlpConstants.DEFAULT_RELEASE_NUM;
        }
        pkgInfo.setRelease(release);
        /////////////////////////////////////////////////////////////
        // Check License property. If it's set, check if it points to a valid
        // file.
        licenseDirName = System.getProperty(
                                JnlpConstants.PROPERTY_NAME_LICENSEDIR);
        licenseDirName = getValidFileArgument(
                            licenseDirName, "license directory", true);
        if (licenseDirName != null) {
            showLicense = true;
        }
        pkgInfo.setShowLicense(showLicense);
        pkgInfo.setLicenseDirPath(licenseDirName);
        /////////////////////////////////////////////////////////////
        // Check BannerJpgFile property.
        bannerJpgFileName = System.getProperty(
                            JnlpConstants.PROPERTY_NAME_BANNERJPGFILE);
        bannerJpgFileName = getValidFileArgument(
                              bannerJpgFileName, "Banner Jpeg File", true);
        pkgInfo.setBannerJpgFilePath(bannerJpgFileName);
        /////////////////////////////////////////////////////////////
        // Check PanelJpgFile property.
        panelJpgFileName = System.getProperty(
                        JnlpConstants.PROPERTY_NAME_PANELJPGFILE);
        panelJpgFileName = getValidFileArgument(
                            panelJpgFileName, "Panel Jpeg File", true);
        pkgInfo.setPanelJpgFilePath(panelJpgFileName);
        /////////////////////////////////////////////////////////////
        // Check MSSDKPath & RawMSIFile property.
        checkMSSDKPathArgument(pkgInfo);
        /////////////////////////////////////////////////////////////
        // Check EnableShortcut property.
        shortcutEnabled = getBoolProperty(
                        JnlpConstants.PROPERTY_NAME_ENABLESHORTCUT);
        pkgInfo.setShortcutEnabled(shortcutEnabled);
        /////////////////////////////////////////////////////////////
        // Check EnableAssociation property.
        associationEnabled = getBoolProperty(
                        JnlpConstants.PROPERTY_NAME_ENABLEASSOCIATION);
        pkgInfo.setAssociationEnabled(associationEnabled);
        /////////////////////////////////////////////////////////////
        // Check EnableSystemCache property.
        systemCacheEnabled = getBoolProperty(
                        JnlpConstants.PROPERTY_NAME_ENABLESYSTEMCACHE);
        pkgInfo.setSystemCacheEnabled(systemCacheEnabled);

        try {
            pkgInfo.parseJnlpInfo();
        } catch (Exception e) {
            return null;
        }
        return pkgInfo;
    }

    /**
     * Checks if the file argument is valid.
     * @param filePath The given file path argument.
     * @param argName   The name of the argument.
     * @param isNullable    true if the argument could be set as null.
     * @return The valid file argument.
     * @throws IOException If fails to get the valid file.
     */
    private static String getValidFileArgument(
                    String filePath, String argName, boolean isNullable)
                    throws IOException {
        if (filePath == null) {
            //filePaht argument is null
            if (isNullable) {
                //null argument acceptable
                return null;
            } else {
                //null argument not acceptable
                throw new IllegalArgumentException(argName
                                                    + " could not be null.");
            }
        } else {
            //filePath argument is not null
            File theFile = new File(filePath);
            theFile = theFile.getCanonicalFile();
            if (theFile.canRead()) {
                //The file is readable
                return theFile.toString();
            } else {
                throw new IllegalArgumentException(
                             "The given "
                             + argName
                             + " is not valid: "
                             + filePath);
            }
        }
    }

    /**
     * Checks if the jnlp file path argument points to a valid jnlp file.
     * @param pkgInfo The JnlpPackageInfo instance.
     * @throws IOException  If the jnlp file is not valid.
     */
    private static void checkJnlpFilePathArgument(JnlpPackageInfo pkgInfo)
                    throws IOException {
        //Checks if the jnlp fileName ends with .jnlp
        String jnlpFileName = pkgInfo.getJnlpFilePath();
        if (!jnlpFileName.endsWith(JnlpConstants.JNLPFILE_EXTENSION)) {
            throw new IllegalArgumentException(
                "The jnlp file path doesn't points to a valid .jnlp file.");
        }
        //Construct the jnlpFile variable
        File jnlpFile = new File(jnlpFileName);
        // Check if the jnlp file path points to a valid JNLP file.
        // Parse the 'codebase' information and relative resource file paths.
        String jnlpCodebase = null;
        String jnlpFileHref = null;

        // Parse codebase information.
        jnlpCodebase = JnlpParser.parseJnlpInfo(jnlpFile,
                            JnlpConstants.JNLP_FIELD_CODEBASE);
        if (jnlpCodebase == null) {
            throw new IOException(
                "Cann't retrieve the codebase information"
                + " from the given jnlp file.");
        }

        // Parse jnlp href information.
        String jnlpHref = JnlpParser.parseJnlpHref(jnlpFile);
        if (jnlpHref == null) {
            throw new IOException(
                "Cann't retrieve the href information"
                + " from the given jnlp file.");
        } else {
            // Check if the jnlp href is the same with the given jnlp file name.
            String fileName = jnlpFile.getName();
            if (!fileName.equals(jnlpHref)) {
                throw new IOException(
                "The given jnlp file name is different from its href value: "
                + jnlpHref);
            }
        }
        // codebase + href is the absolute jnlp file href.
        if (!jnlpCodebase.endsWith("/")) {
            jnlpCodebase = jnlpCodebase.concat(File.separator);
        }
        jnlpFileHref = jnlpCodebase + jnlpHref;
        // Parse resource files information.
        List jnlpRefFilePaths = JnlpParser.parseJnlpRefFiles(jnlpFile);
        if (jnlpRefFilePaths == null) {
            throw new IOException(
                "Cann't retrieve the jnlp resource files information"
                + " from the given jnlp file.");
        }
        pkgInfo.setJnlpFileHref(jnlpFileHref);
        pkgInfo.setJnlpRefFilePaths(jnlpRefFilePaths);
    }

    /**
     * Checks if the resource files referenced in the jnlp file in valid.
     * @param pkgInfo The jnlpPackageInfo instance.
     * @throws IOException If failed during the checking process.
     */
    private static void checkResourcePathArgument(JnlpPackageInfo pkgInfo)
                throws IOException {
        String resourceDirName = System.getProperty(
                                JnlpConstants.PROPERTY_NAME_RESOURCEDIR);
        String jnlpFileName = pkgInfo.getJnlpFilePath();
        File jnlpFile = new File(jnlpFileName);
        if (resourceDirName == null) {
            String jnlpFileParentDir = jnlpFile.getParent();
            resourceDirName = getValidFileArgument(
                                jnlpFileParentDir, "resource dir", false);
        } else {
            resourceDirName = getValidFileArgument(
                                resourceDirName, "resource dir", false);
        }
        File resourceDirFile = new File(resourceDirName);
        // Check if all of the absolute referenced file paths exist.
        Iterator refIter = pkgInfo.getJnlpRefFilePaths();
        while (refIter.hasNext()) {
            String oneRefFilePath = (String) refIter.next();
            if (oneRefFilePath != null) {
                File oneAbsFilePath = new File(resourceDirFile, oneRefFilePath);

                // Check if the file is a valid file and is readable.
                if (!FileOperUtility.isFileReadable(oneAbsFilePath)) {
                    throw new IllegalArgumentException(
                                "Cann't read resource file: "
                                + oneRefFilePath
                                + " from resource path: "
                                + resourceDirFile.toString());
                }
            }
        }
        pkgInfo.setResourcePath(resourceDirName);
    }

    /**
     * Get the package name property from command line option.
     * @param pkgInfo The JnlpPackageInfo instance.
     * @throws IOException If failed to get the packagename property.
     */
    private static void checkPackageNameArgument(JnlpPackageInfo pkgInfo)
        throws IOException {
        String packageName = System.getProperty(
                            JnlpConstants.PROPERTY_NAME_PACKAGENAME);
        if (packageName == null) {
            // No package name is set, then use the jnlp file name by default.
            File jnlpFile = new File(pkgInfo.getJnlpFilePath());
            packageName = FileOperUtility.getFileNameWithoutExt(jnlpFile);
            if (packageName == null) {
                throw new IllegalArgumentException(
                    "The given jnlp file name is not a valid package name.");
            }
        }
        pkgInfo.setPackageName(packageName);
    }

    /**
     * Gets the raw MSI template file path from command line option.
     * @param pkgInfo   The JnlpPakcageInfo instance.
     * @throws IOException If failed to get the raw MSI template path.
     */
    private static void checkMSSDKPathArgument(JnlpPackageInfo pkgInfo)
            throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        String msSDKPath = null;
        String rawMsiFilePath = null;
        if (osName.startsWith(JnlpConstants.OS_WINDOWS)) {
            msSDKPath = System.getProperty(
                            JnlpConstants.PROPERTY_NAME_MS_SDK_PATH);
            msSDKPath = getValidFileArgument(
                            msSDKPath, "MS SDK Path", false);
            msSDKPath = msSDKPath
                        + (msSDKPath.endsWith(File.separator)
                           ? "" : File.separator);
            rawMsiFilePath =
                msSDKPath
                + JnlpConstants.HIERACHY_TO_RAW_MSI;
            rawMsiFilePath = getValidFileArgument(
                            rawMsiFilePath, "raw MSI file path", false);
        }
        pkgInfo.setMSSDKDirPath(msSDKPath);
        pkgInfo.setRawMsiFilePath(rawMsiFilePath);
    }

    /**
     * Checks if the output directory is valid.
     * @param pkgInfo The JnlpPackageInfo instance.
     * @throws IOException If failed during the checking process.
     */
    private static void checkOutputDirNameArgument(JnlpPackageInfo pkgInfo)
                    throws IOException {
        String outputDirName = System.getProperty(
                                JnlpConstants.PROPERTY_NAME_OUTPUTDIR);
        if (outputDirName == null) {
            outputDirName = "." + File.separator;
        }
        outputDirName =
            getValidFileArgument(outputDirName, "package dir", false);
        //Make sure output dir ends with file separator.
        if (!outputDirName.endsWith(File.separator)) {
            outputDirName += File.separator;
        }
        File outputDirFile = new File(outputDirName);
        // Check if the directory already exists.
        if (outputDirFile.isDirectory()) {
            if (!outputDirFile.canWrite()) {
                throw new IllegalArgumentException(
                    "The given package path is not writable: "
                    + outputDirFile);
            } else {
                pkgInfo.setOutputDirPath(outputDirName);
            }
        } else {
            // Try to create the new destination directory.
            boolean mksucceed = outputDirFile.mkdirs();
            if (!mksucceed) {
                throw new IllegalArgumentException(
                    "Failed to create the output dir: " + outputDirFile);
            } else {
                pkgInfo.setOutputDirPath(outputDirName);
            }
        }
    }
    /**
     * Invoke the concrete platform relevant implementation to generate the
     * package.
     * @param args The given arguments.
     * @throws IOException If failed to generate the dest package.
     */
    public static void generatePackage(String[] args)
        throws IOException {
        // Check if the given jnlp file path is an URL. If it it, retrieve the
        // file locally.
        boolean isHttpJnlp = false;
        String tempDirName = null;
        if (args[0].startsWith("http://")) {
            isHttpJnlp = true;

            tempDirName = FileOperUtility.createUniqueTmpDir();

            File localJnlpFile =
                 FileOperUtility.httpJnlp2localJnlp(
                                    new URL(args[0]), tempDirName);
            args[0] = localJnlpFile.toString();
        }

        // Parse and check the command line arguments.
        JnlpPackageInfo jnlpPkgInfo = null;
        jnlpPkgInfo = parseArguments(args);

        // Retrieve the jnlp resource files locally if the given jnlp path is an
        // URL.
        if (isHttpJnlp) {
            FileOperUtility.httpJnlpRes2localRes(jnlpPkgInfo, tempDirName);
        }

        // Generate the installable package with the given package info.
        PackageGenerator pkgGenerator = PackageGeneratorFactory.newInstance();
        pkgGenerator.generatePackage(jnlpPkgInfo);

        // Delete the locally jnlp resource files retrieved from the given URL.
        if (isHttpJnlp) {
            FileOperUtility.deleteDirTree(new File(tempDirName));
        }
    }
}
