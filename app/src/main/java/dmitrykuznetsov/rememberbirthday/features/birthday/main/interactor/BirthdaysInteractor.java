package dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.data.PersonData;

/**
 * Created by Alena on 26.12.2017.
 */

public interface BirthdaysInteractor {
    List<PersonData> getPersonDataList();
}