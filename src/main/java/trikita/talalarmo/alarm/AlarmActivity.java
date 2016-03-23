package trikita.talalarmo.alarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.WindowManager;

import trikita.anvil.RenderableView;
import static trikita.anvil.DSL.*;

import trikita.jedux.Action;
import trikita.talalarmo.Actions;
import trikita.talalarmo.App;
import trikita.talalarmo.ui.Theme;

public class AlarmActivity extends Activity {
    private PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "AlarmActivity");
        mWakeLock.acquire();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        setContentView(new RenderableView(this) {
            public void view() {
                Theme.materialIcon(() -> {
                    size(FILL, FILL);
                    text("\ue857"); // "alarm off"
                    textColor(Theme.get(App.getState().settings().theme()).secondaryTextColor);
                    textSize(dip(128));
                    backgroundColor(Theme.get(App.getState().settings().theme()).backgroundColor);
                    onClick(v -> {
                        App.dispatch(new Action<>(Actions.Alarm.DISMISS));
                        finish();
                    });
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }
}
