/* -*- Mode: C++; tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 4 -*- */
/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is mozilla.org code.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 2002
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   Conrad Carlen <ccarlen@netscape.com>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

// from the Gecko SDK
#include "nsIDirectoryService.h"
#include "nsIFile.h"
#include "nsCOMPtr.h"

// --------------------------------------------------------------------------------------
// ProfileDirServiceProvider - The nsIDirectoryServiceProvider implementation used for
// profile-relative file locations.
// --------------------------------------------------------------------------------------

class ProfileDirServiceProvider: public nsIDirectoryServiceProvider
{  
  NS_DECL_ISUPPORTS
  NS_DECL_NSIDIRECTORYSERVICEPROVIDER

  friend nsresult NS_NewMyProfileDirServiceProvider(ProfileDirServiceProvider**);

public:

   /**
    * SetProfileDir
    *
    * @param aProfileDir   The directory containing the profile files.
    *                      It does not need to exist before calling this
    *                      method. If it does not, it will be created and
    *                      defaults will be copied to it. 
    */

   virtual nsresult        SetProfileDir(nsIFile* aProfileDir);

  /**
   * Register
   *
   * Convenience method to register the provider with directory service.
   * The service holds strong references to registered providers so consumers
   * don't need to hold a reference to this object after calling Register().
   */

  virtual nsresult         Register();

  /**
   * Shutdown
   *
   * This method must be called before shutting down XPCOM if this object
   * was created with aNotifyObservers == PR_TRUE. If this object was
   * created with aNotifyObservers == PR_FALSE, this method is a no-op.
   */

  virtual nsresult         Shutdown();

protected:
                           ProfileDirServiceProvider();
   virtual                 ~ProfileDirServiceProvider();

  nsresult                 InitProfileDir(nsIFile* profileDir);
  nsresult                 EnsureProfileFileExists(nsIFile *aFile, nsIFile *destDir);

protected:

  nsCOMPtr<nsIFile>        mProfileDir;
};


// --------------------------------------------------------------------------------------

/**
 * Global method to create an instance of ProfileDirServiceProvider
 *
 * @param aNotifyObservers    If true, will send out profile startup
 *                            notifications when the profile directory is set.
 *                            See nsIProfileChangeStatus.
 */
 
nsresult NS_NewMyProfileDirServiceProvider(ProfileDirServiceProvider** aProvider);
