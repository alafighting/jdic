# Microsoft Developer Studio Generated NMAKE File, Based on IeEmbed.dsp
!IF "$(CFG)" == ""
CFG=IeEmbed - Win32 Debug
!MESSAGE No configuration specified. Defaulting to IeEmbed - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "IeEmbed - Win32 Debug" && "$(CFG)" != "IeEmbed - Win32 Release"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "IeEmbed.mak" CFG="IeEmbed - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "IeEmbed - Win32 Debug" (based on "Win32 (x86) Application")
!MESSAGE "IeEmbed - Win32 Release" (based on "Win32 (x86) Application")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "IeEmbed - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "$(OUTDIR)\IeEmbed.exe"


CLEAN :
	-@erase "$(INTDIR)\BrowserWindow.obj"
	-@erase "$(INTDIR)\IeEmbed.obj"
	-@erase "$(INTDIR)\IeEmbed.pch"
	-@erase "$(INTDIR)\IeEmbed.res"
	-@erase "$(INTDIR)\MsgServer.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\Util.obj"
	-@erase "$(INTDIR)\VariantWrapper.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\IeEmbed.exe"
	-@erase "$(OUTDIR)\IeEmbed.ilk"
	-@erase "$(OUTDIR)\IeEmbed.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MLd /W3 /Gm /GX /ZI /Od /I "../../../share/native/utils" /I "$(MOZILLA_SRC_HOME)/dist/include/nspr" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /Fp"$(INTDIR)\IeEmbed.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 
RSC_PROJ=/l 0x409 /fo"$(INTDIR)\IeEmbed.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\IeEmbed.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=$(MOZILLA_SRC_HOME)/dist/lib/nspr4.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /incremental:yes /pdb:"$(OUTDIR)\IeEmbed.pdb" /debug /machine:I386 /out:"$(OUTDIR)\IeEmbed.exe" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\BrowserWindow.obj" \
	"$(INTDIR)\IeEmbed.obj" \
	"$(INTDIR)\MsgServer.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\Util.obj" \
	"$(INTDIR)\VariantWrapper.obj" \
	"$(INTDIR)\IeEmbed.res"

"$(OUTDIR)\IeEmbed.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "IeEmbed - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

ALL : "$(OUTDIR)\IeEmbed.exe" "$(OUTDIR)\IeEmbed.bsc"


CLEAN :
	-@erase "$(INTDIR)\BrowserWindow.obj"
	-@erase "$(INTDIR)\BrowserWindow.sbr"
	-@erase "$(INTDIR)\IeEmbed.obj"
	-@erase "$(INTDIR)\IeEmbed.pch"
	-@erase "$(INTDIR)\IeEmbed.res"
	-@erase "$(INTDIR)\IeEmbed.sbr"
	-@erase "$(INTDIR)\MsgServer.obj"
	-@erase "$(INTDIR)\MsgServer.sbr"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\StdAfx.sbr"
	-@erase "$(INTDIR)\Util.obj"
	-@erase "$(INTDIR)\Util.sbr"
	-@erase "$(INTDIR)\VariantWrapper.obj"
	-@erase "$(INTDIR)\VariantWrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\IeEmbed.bsc"
	-@erase "$(OUTDIR)\IeEmbed.exe"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MD /W3 /GX /O1 /I "../../../share/native/utils" /I "$(MOZILLA_SRC_HOME)/dist/include/nspr" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_AFXDLL" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\IeEmbed.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 
RSC_PROJ=/l 0x409 /fo"$(INTDIR)\IeEmbed.res" /d "NDEBUG" /d "_AFXDLL" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\IeEmbed.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\BrowserWindow.sbr" \
	"$(INTDIR)\IeEmbed.sbr" \
	"$(INTDIR)\MsgServer.sbr" \
	"$(INTDIR)\StdAfx.sbr" \
	"$(INTDIR)\Util.sbr" \
	"$(INTDIR)\VariantWrapper.sbr"

"$(OUTDIR)\IeEmbed.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=$(MOZILLA_SRC_HOME)/dist/lib/nspr4.lib /nologo /subsystem:windows /incremental:no /pdb:"$(OUTDIR)\IeEmbed.pdb" /machine:I386 /out:"$(OUTDIR)\IeEmbed.exe" 
LINK32_OBJS= \
	"$(INTDIR)\BrowserWindow.obj" \
	"$(INTDIR)\IeEmbed.obj" \
	"$(INTDIR)\MsgServer.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\Util.obj" \
	"$(INTDIR)\VariantWrapper.obj" \
	"$(INTDIR)\IeEmbed.res"

"$(OUTDIR)\IeEmbed.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ENDIF 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL_PROJ=

!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("IeEmbed.dep")
!INCLUDE "IeEmbed.dep"
!ELSE 
!MESSAGE Warning: cannot find "IeEmbed.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "IeEmbed - Win32 Debug" || "$(CFG)" == "IeEmbed - Win32 Release"
SOURCE=.\BrowserWindow.cpp

!IF  "$(CFG)" == "IeEmbed - Win32 Debug"


"$(INTDIR)\BrowserWindow.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\IeEmbed.pch"


!ELSEIF  "$(CFG)" == "IeEmbed - Win32 Release"


"$(INTDIR)\BrowserWindow.obj"	"$(INTDIR)\BrowserWindow.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\IeEmbed.pch"


!ENDIF 

SOURCE=.\IeEmbed.cpp

!IF  "$(CFG)" == "IeEmbed - Win32 Debug"


"$(INTDIR)\IeEmbed.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\IeEmbed.pch"


!ELSEIF  "$(CFG)" == "IeEmbed - Win32 Release"


"$(INTDIR)\IeEmbed.obj"	"$(INTDIR)\IeEmbed.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\IeEmbed.pch"


!ENDIF 

SOURCE=.\IeEmbed.rc

"$(INTDIR)\IeEmbed.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) $(RSC_PROJ) $(SOURCE)


SOURCE=..\..\..\share\native\utils\MsgServer.cpp

!IF  "$(CFG)" == "IeEmbed - Win32 Debug"

CPP_SWITCHES=/nologo /MLd /W3 /Gm /GX /ZI /Od /I "../../../share/native/utils" /I "$(MOZILLA_SRC_HOME)/dist/include/nspr" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\MsgServer.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "IeEmbed - Win32 Release"

CPP_SWITCHES=/nologo /MD /W3 /GX /O1 /I "../../../share/native/utils" /I "$(MOZILLA_SRC_HOME)/dist/include/nspr" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_AFXDLL" /FR"$(INTDIR)\\" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\MsgServer.obj"	"$(INTDIR)\MsgServer.sbr" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=.\StdAfx.cpp

!IF  "$(CFG)" == "IeEmbed - Win32 Debug"

CPP_SWITCHES=/nologo /MLd /W3 /Gm /GX /ZI /Od /I "../../../share/native/utils" /I "$(MOZILLA_SRC_HOME)/dist/include/nspr" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /Fp"$(INTDIR)\IeEmbed.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\IeEmbed.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "IeEmbed - Win32 Release"

CPP_SWITCHES=/nologo /MD /W3 /GX /O1 /I "../../../share/native/utils" /I "$(MOZILLA_SRC_HOME)/dist/include/nspr" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_AFXDLL" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\IeEmbed.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\StdAfx.sbr"	"$(INTDIR)\IeEmbed.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\..\..\share\native\utils\Util.cpp

!IF  "$(CFG)" == "IeEmbed - Win32 Debug"

CPP_SWITCHES=/nologo /MLd /W3 /Gm /GX /ZI /Od /I "../../../share/native/utils" /I "$(MOZILLA_SRC_HOME)/dist/include/nspr" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\Util.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "IeEmbed - Win32 Release"

CPP_SWITCHES=/nologo /MD /W3 /GX /O1 /I "../../../share/native/utils" /I "$(MOZILLA_SRC_HOME)/dist/include/nspr" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_AFXDLL" /FR"$(INTDIR)\\" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\Util.obj"	"$(INTDIR)\Util.sbr" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=.\VariantWrapper.cpp

!IF  "$(CFG)" == "IeEmbed - Win32 Debug"


"$(INTDIR)\VariantWrapper.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\IeEmbed.pch"


!ELSEIF  "$(CFG)" == "IeEmbed - Win32 Release"


"$(INTDIR)\VariantWrapper.obj"	"$(INTDIR)\VariantWrapper.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\IeEmbed.pch"


!ENDIF 


!ENDIF 

