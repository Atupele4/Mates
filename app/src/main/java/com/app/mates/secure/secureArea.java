package com.app.mates.secure;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by SmartCare on 25/10/2015.
 */
public class secureArea extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public secureArea(String name) {
        super("HelloIntentService");
    }

    public secureArea(){
        super("");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        long endTime = System.currentTimeMillis() + 5*1000;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                }
            }
        }
    }
}
