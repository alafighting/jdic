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

#ifndef _MsgServer_H_
#define _MsgServer_H_

#include "prio.h"
#include "prnetdb.h"
#include "prthread.h"
#include "prlock.h"

// the maximum connection from client we can accept
#define MAX_CONN    1

#define MAX_FD      2

#define BUFFER_SIZE      2048
#define BUFFER_SIZE_HALF BUFFER_SIZE / 2
#define MAX_TRIGGER      20
#define EMPTY_TRIGGER    -1111
#define MAX_WAIT         100

typedef void (*MsgHandler)(const char *);

class MsgServer
{
private:
    // the port we are listening to
    static int mPort;

    PRPollDesc mPollList[MAX_FD];
    
    int mFailed;
    unsigned int mCounter;

    char *mSendBuffer;
    // message buffer receiving the short (<= BUFFER_SIZE) messages.
    char *mRecvBuffer;    
    // message buffer storing the received message pieces for a 
    // long (> BUFFER_SIZE) message.
    char *mLongRecvBuffer; 
    int mLongRecvBufferSize; 

    struct Trigger {
        int mInstance;
        int mMsg;
        int *mTrigger;
    };

    Trigger *mTriggers;

    MsgHandler mHandler;

    int RecvData();
    int SendData();

public:
    MsgServer();
    ~MsgServer();

    int CreateServerSocket();

    int Listen();
    
    int Send(const char *pData);
    int AddTrigger(int instance, int msg, int *trigger);

    int IsFailed() { return mFailed; }
    void SetHandler(MsgHandler handler) { mHandler = handler; }

    static void SetPort(int port) { mPort = port; }
};

void SendSocketMessage(int instance, int event, const char *pData = NULL);
void AddTrigger(int instance, int msg, int *trigger);
void PortListening(void* pParam);

extern MsgServer gMessenger;
extern PRLock *gServerLock;

#endif
