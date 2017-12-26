package dmitrykuznetsov.rememberbirthday.common.base;

import android.support.v7.app.AppCompatActivity;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.error.RetrofitException;

/**
 * Created by vernau on 5/17/17.
 */

public abstract class NetworkActivityVM<A extends AppCompatActivity> extends BaseActivityVM<A> {

//    public final ObservableField<String> errorInputText = new ObservableField<>();

    public NetworkActivityVM(A activity) {
        super(activity);
    }

    protected void onError(Throwable throwable) {
        isLoading.set(false);

        String message = null;
        RetrofitException retrofitException = (RetrofitException) throwable;
        switch (retrofitException.getKind()) {
            case NETWORK:
                message = getActivity().getString(R.string.error_network_not_available);
                break;
            case HTTP:
                handleHTTPError(retrofitException);
                break;
            case UNEXPECTED:
            default:
                message = getActivity().getString(R.string.error_server_return_incorrect_data);
                break;
        }
        errorMessage.set(message);
    }

    private void handleHTTPError(RetrofitException retrofitException) {
        String message = null;
        int code = retrofitException.getResponse().code();
        switch (code) {
            case 401:
                message = getActivity().getString(R.string.error_401);
                break;
            case 403:
                message = getActivity().getString(R.string.error_403);
                break;
            case 404:
                message = getActivity().getString(R.string.error_404);
                break;
            default:
                break;
        }
        errorMessage.set(message);
    }


}