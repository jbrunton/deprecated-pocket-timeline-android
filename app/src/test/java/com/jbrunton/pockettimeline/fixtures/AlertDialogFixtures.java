package com.jbrunton.pockettimeline.fixtures;

import android.app.AlertDialog;
import android.widget.Button;

import org.robolectric.shadows.ShadowAlertDialog;

import static org.robolectric.Shadows.shadowOf;

public class AlertDialogFixtures {
    public static boolean alertDialogShowing() {
        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        return dialog != null && dialog.isShowing();
    }

    public static String alertDialogMessage() {
        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        return shadowOf(dialog).getMessage().toString();
    }

    public static Button alertDialogButton(int whichButton) {
        return ShadowAlertDialog.getLatestAlertDialog().getButton(whichButton);
    }
}