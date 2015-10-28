package com.app.mates.secure;

import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;

public  class TriggerListener extends TriggerEventListener {
    public void onTrigger(TriggerEvent event) {
        // Do Work.
        // As it is a one shot sensor, it will be canceled automatically.
        // SensorManager.requestTriggerSensor(this, mSigMotion); needs to
        // be called again, if needed.
    }
}
