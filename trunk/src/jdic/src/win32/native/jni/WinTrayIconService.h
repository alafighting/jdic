/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_jdesktop_jdic_tray_internal_impl_WinTrayIconService */

#ifndef _Included_org_jdesktop_jdic_tray_internal_impl_WinTrayIconService
#define _Included_org_jdesktop_jdic_tray_internal_impl_WinTrayIconService
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_jdesktop_jdic_tray_internal_impl_WinTrayIconService
 * Method:    createIconIndirect
 * Signature: ([I[BIIIII)J
 */
JNIEXPORT jlong JNICALL Java_org_jdesktop_jdic_tray_internal_impl_WinTrayIconService_createIconIndirect
  (JNIEnv *, jobject, jintArray, jbyteArray, jint, jint, jint, jint, jint);

/*
 * Class:     org_jdesktop_jdic_tray_internal_impl_WinTrayIconService
 * Method:    deleteHIcon
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_jdesktop_jdic_tray_internal_impl_WinTrayIconService_deleteHIcon
  (JNIEnv *, jobject, jlong);

/*
 * Class:     org_jdesktop_jdic_tray_internal_impl_WinTrayIconService
 * Method:    createIcon
 * Signature: (JILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_jdesktop_jdic_tray_internal_impl_WinTrayIconService_createIcon
  (JNIEnv *, jobject, jlong, jint, jbyteArray);

/*
 * Class:     org_jdesktop_jdic_tray_internal_impl_WinTrayIconService
 * Method:    updateNativeIcon
 * Signature: (JILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_jdesktop_jdic_tray_internal_impl_WinTrayIconService_updateNativeIcon
  (JNIEnv *, jobject, jlong, jint, jbyteArray);

/*
 * Class:     org_jdesktop_jdic_tray_internal_impl_WinTrayIconService
 * Method:    removeIcon
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_jdesktop_jdic_tray_internal_impl_WinTrayIconService_removeIcon
  (JNIEnv *, jclass, jint);

#ifdef __cplusplus
}
#endif
#endif
