package com.example.hansong.zimutiemodel;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hansong on 17-7-10.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static void addactivity(Activity activity){
        activities.add(activity);
    }
    public static void removeactivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishall() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

    }
}
