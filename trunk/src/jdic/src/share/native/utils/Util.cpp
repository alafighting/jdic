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

#include <stdio.h>
#include <stdarg.h>
#include <string.h>
#include "prlog.h"
#include "prprf.h"
#include "Util.h"

#if defined(DEBUG) || defined(_DEBUG)

PRLogModuleInfo *prLogModuleInfo = PR_NewLogModule("webbrowser");

void WBTrace(const char *format, ...)
{
    va_list args;
    va_start(args, format);

    char buffer[512];
    PR_vsnprintf(buffer, sizeof(buffer), format, args);

    //fprintf(stderr, "### Ctrace: %s\n", buffer);
    if (prLogModuleInfo) {
        PR_LOG(prLogModuleInfo, 5, (buffer));
    }

    va_end(args);
}

#endif //DEBUG

///////////////////////////////////////////////////////////
// Implemetation of Array type
///////////////////////////////////////////////////////////
WBArray::WBArray()
{
	m_pData = NULL;
	m_nSize = m_nMaxSize = 0;
}

WBArray::~WBArray()
{
	delete[] (char*)m_pData;
}

void WBArray::SetSize(int nNewSize)
{
	if (nNewSize == 0)
	{
		// shrink to nothing
		delete[] (char*)m_pData;
		m_pData = NULL;
		m_nSize = m_nMaxSize = 0;
	}
	else if (m_pData == NULL)
	{
		// create one with exact size
		m_pData = (void**) new char[nNewSize * sizeof(void*)];

		memset(m_pData, 0, nNewSize * sizeof(void*));  // zero fill

		m_nSize = m_nMaxSize = nNewSize;
	}
	else if (nNewSize <= m_nMaxSize)
	{
		// it fits
		if (nNewSize > m_nSize)
		{
			// initialize the new elements
			memset(&m_pData[m_nSize], 0, (nNewSize-m_nSize) * sizeof(void*));
		}

		m_nSize = nNewSize;
	}
	else
	{
		// otherwise, grow array
		int nGrowBy = min(1024, max(4, m_nSize / 8));
		
        int nNewMax;
		if (nNewSize < m_nMaxSize + nGrowBy)
			nNewMax = m_nMaxSize + nGrowBy;  // granularity
		else
			nNewMax = nNewSize;  // no slush

		void** pNewData = (void**) new char[nNewMax * sizeof(void*)];

		// copy new data from old
		memcpy(pNewData, m_pData, m_nSize * sizeof(void*));
		memset(&pNewData[m_nSize], 0, (nNewSize-m_nSize) * sizeof(void*));

		// get rid of old stuff (note: no destructors called)
		delete[] (char*)m_pData;
		m_pData = pNewData;
		m_nSize = nNewSize;
		m_nMaxSize = nNewMax;
	}
}

/////////////////////////////////////////////////////////////////////////////

void WBArray::SetAtGrow(int nIndex, void* newElement)
{
	if (nIndex >= m_nSize)
		SetSize(nIndex+1);
	m_pData[nIndex] = newElement;
}

int WBArray::Add(void* newElement)
{ 
    int nIndex = m_nSize;
    SetAtGrow(nIndex, newElement);
    return nIndex; 
}

void WBArray::InsertAt(int nIndex, void* newElement, int nCount)
{
	if (nIndex >= m_nSize)
	{
		// adding after the end of the array
		SetSize(nIndex + nCount);  // grow so nIndex is valid
	}
	else
	{
		// inserting in the middle of the array
		int nOldSize = m_nSize;
		SetSize(m_nSize + nCount);  // grow it to new size
		// shift old data up to fill gap
		memmove(&m_pData[nIndex+nCount], &m_pData[nIndex],
			(nOldSize-nIndex) * sizeof(void*));

		// re-init slots we copied from
		memset(&m_pData[nIndex], 0, nCount * sizeof(void*));
	}

	// copy elements into the empty space
	while (nCount--)
		m_pData[nIndex++] = newElement;
}

void WBArray::RemoveAt(int nIndex, int nCount)
{
	// just remove a range
	int nMoveCount = m_nSize - (nIndex + nCount);

	if (nMoveCount)
		memmove(&m_pData[nIndex], &m_pData[nIndex + nCount],
			nMoveCount * sizeof(void*));
	m_nSize -= nCount;
}
