package com.example.smartcare.secure;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class secureDevice extends Service {
    public secureDevice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
