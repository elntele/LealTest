package com.knowtest.lealtest.interfaces;

import com.google.firebase.events.Event;

import java.util.List;
import java.util.Map;

public interface LealCalBack {
    void onCallback(List<Map<String, Object>> eventList);
}
