package dmitrykuznetsov.rememberbirthday.features.birthday.detail.di;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.input.InputDialog;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.input.InputDialogVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor.DetailBirthdayInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor.DetailBirthdayInteractorImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.scope.ActivityScope;

/**
 * Created by Alena on 26.12.2017.
 */

@Module
public class DetailBirthdayModule {

//    @Provides
//    @ActivityScope
//    InputDialogBinding provideInputDialogBinding(Context context, InputDialogVM inputDialogVM) {
//        InputDialogBinding inputDialogBinding = DataBindingUtil
//                .inflate(LayoutInflater.from(context), R.layout.input_dialog, null, false);
//        inputDialogBinding.setViewModel(inputDialogVM);
//        return inputDialogBinding;
//    }

    @Provides
    @ActivityScope
    AlertDialog.Builder provideAlertDialog(DetailBirthdayActivity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle(R.string.dialog_title_confirm_delete_user);
        builder.setMessage(R.string.dialog_message_confirm_delete_user);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> activity.confirmDeletePerson());
        builder.setNegativeButton(R.string.cancel, null);
        builder.setOnCancelListener(null);
        return builder;
    }

    @Provides
    @ActivityScope
    InputDialogVM provideInputDialogVM() {
        return new InputDialogVM();
    }

    @Provides
    @ActivityScope
    InputDialog provideInputDialog(AlertDialog.Builder builder) {
        return new InputDialog(builder);
    }

    @Provides
    @ActivityScope
    DetailBirthdayInteractor provideDetailBirthdayInteractor(PersonRepo personRepo) {
        return new DetailBirthdayInteractorImpl(personRepo);
    }

    @Provides
    @ActivityScope
    DetailBirthdayActivityVM provideDetailBirthdayActivityVM(DetailBirthdayActivity activity,
                                                             DetailBirthdayInteractor interactor, InputDialog inputDialog) {
        Intent intent = activity.getIntent();
        if (intent != null) {
            int id = intent.getIntExtra(Constants.USER_ID, 0);
            return new DetailBirthdayActivityVM(activity, id, interactor, inputDialog);
        } else {
            Toast.makeText(activity, R.string.error_person_not_found, Toast.LENGTH_LONG).show();
            activity.finish();
            return null;
        }
    }
}