package dmitrykuznetsov.rememberbirthday.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dmitrykuznetsov.rememberbirthday.common.receiver.interactor.AlarmInteractor;

/**
 * Created by Dmitry Kuznetsov on 23.07.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String ACTION_REFRESH_DATA = "dmitrykuznetsov.rememberbirthday.ACTION_REFRESH_DATA";
    public static final String ACTION_PACKAGE_REPLACED = "android.intent.action.MY_PACKAGE_REPLACED";
    public static final String ACTION_INSTALL = "com.android.vending.INSTALL_REFERRER";

    @Inject
    AlarmInteractor alarmInteractor;

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);

        if (ACTION_REFRESH_DATA.equals(intent.getAction())
                || ACTION_PACKAGE_REPLACED.equals(intent.getAction())
                || ACTION_INSTALL.equals(intent.getAction())) {
            Log.d("AlarmReceiver", "run");
            alarmInteractor.updateAlarm()
                    .subscribe();
            alarmInteractor.runNotificationPersons()
                    .subscribe();
        }
    }

}
