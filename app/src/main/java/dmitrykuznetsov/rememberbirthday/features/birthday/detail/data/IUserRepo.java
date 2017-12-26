package dmitrykuznetsov.rememberbirthday.features.birthday.detail.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.old.RememberContentProvider;
import dmitrykuznetsov.rememberbirthday.data.PersonData;
import dmitrykuznetsov.rememberbirthday.interfaces.ServiceCallback;

/**
 * Created by dmitry on 11.03.17.
 */

public class IUserRepo implements UserRepo {
    @Override
    public void getUser(final Context context, final int userId, final ServiceCallback<PersonData> callBack) {
        final ContentResolver cr = context.getContentResolver();
        final String where = RememberContentProvider.UID + "=" + userId;

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                return cr.query(RememberContentProvider.CONTENT_URI, null, where, null, null);
            }

            @Override
            protected void onPostExecute(Cursor c) {
                if (c != null) {
                    c.moveToFirst();
                    String name = c.getString(c.getColumnIndex(RememberContentProvider.NAME));
                    String note = c.getString(c.getColumnIndex(RememberContentProvider.NOTE));
                    String pathImage = c.getString(c.getColumnIndex(RememberContentProvider.PATHIMAGE));
                    long dateInMillis = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
                    String bindPhone = c.getString(c.getColumnIndex(RememberContentProvider.PHONE_NUMBER));

                    PersonData person = new PersonData(userId, name, note, bindPhone, pathImage, dateInMillis, null);
                    callBack.onSuccess(person);
                    c.close();
                } else {
                    callBack.onFailed(context.getString(R.string.error_user_not_found));
                }
            }
        }.execute();
    }

    @Override
    public void deleteUser(Context context, int userId, ServiceCallback<Integer> callBack) {
        ContentResolver cr = context.getContentResolver();
        String where = RememberContentProvider.UID + "=" + userId;

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return cr.delete(RememberContentProvider.CONTENT_URI, where, null);
            }

            @Override
            protected void onPostExecute(Integer numberRows) {
                super.onPostExecute(numberRows);
                if (numberRows > 0) {
                    callBack.onSuccess(numberRows);
                } else {
                    callBack.onFailed(context.getString(R.string.error_user_not_found));
                }
            }
        }.execute();
    }
}