package com.rana.abjadity.common.helpers;

import android.app.Activity;
import android.view.WindowManager;
import com.google.ar.core.Camera;
import com.google.ar.core.TrackingFailureReason;
import com.google.ar.core.TrackingState;

/** Gets human readibly tracking failure reasons and suggested actions. */
public final class TrackingStateHelper {
  private static final String INSUFFICIENT_FEATURES_MESSAGE =
      "لم يتم التعرف على سطح ..";
  private static final String EXCESSIVE_MOTION_MESSAGE = "حرك الجهاز بسرعة أبطئ ..";
  private static final String INSUFFICIENT_LIGHT_MESSAGE =
      "المكان مظلم، حاول مرة أخرى في مكان مضيئ ..";
  private static final String BAD_STATE_MESSAGE =
      "يرجى المحاولة مرة أخرى ..";
  private static final String CAMERA_UNAVAILABLE_MESSAGE =
      "يتم استخدام الكاميرا من خلال تطبيق أخر ..";

  private final Activity activity;

  private TrackingState previousTrackingState;

  public TrackingStateHelper(Activity activity) {
    this.activity = activity;
  }

  /** Keep the screen unlocked while tracking, but allow it to lock when tracking stops. */
  public void updateKeepScreenOnFlag(TrackingState trackingState) {
    if (trackingState == previousTrackingState) {
      return;
    }

    previousTrackingState = trackingState;
    switch (trackingState) {
      case PAUSED:
      case STOPPED:
        activity.runOnUiThread(
            () -> activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
        break;
      case TRACKING:
        activity.runOnUiThread(
            () -> activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
        break;
    }
  }

  public static String getTrackingFailureReasonString(Camera camera) {
    TrackingFailureReason reason = camera.getTrackingFailureReason();
    switch (reason) {
      case NONE:
        return "";
      case BAD_STATE:
        return BAD_STATE_MESSAGE;
      case INSUFFICIENT_LIGHT:
        return INSUFFICIENT_LIGHT_MESSAGE;
      case EXCESSIVE_MOTION:
        return EXCESSIVE_MOTION_MESSAGE;
      case INSUFFICIENT_FEATURES:
        return INSUFFICIENT_FEATURES_MESSAGE;
      case CAMERA_UNAVAILABLE:
        return CAMERA_UNAVAILABLE_MESSAGE;
    }
    return "Unknown tracking failure reason: " + reason;
  }
}
