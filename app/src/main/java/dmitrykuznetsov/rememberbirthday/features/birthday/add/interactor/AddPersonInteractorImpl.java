package dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor;

import android.content.Context;
import android.net.Uri;

import dmitrykuznetsov.rememberbirthday.data.PersonData;
import dmitrykuznetsov.rememberbirthday.model.Person;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.AddPersonRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.AddPersonRepoImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.LastPersonRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.LastPersonRepoImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.PhoneRetrieverImpl;

/**
 * Created by dmitry on 25.05.17.
 */

public class AddPersonInteractorImpl implements AddPersonInteractor {

    private LastPersonRepo lastPersonRepo;
    private AddPersonRepo addPersonRepo;

    public PhoneRetriever phoneRetriever;

    public AddPersonInteractorImpl() {
        lastPersonRepo = new LastPersonRepoImpl();
        addPersonRepo = new AddPersonRepoImpl();

    }

    @Override
    public void addPersonData(Person p) {
        int id = lastPersonRepo.getNextId();
        PersonData personData = new PersonData(id, p.name.get(), p.note.get(), p.bindPhone.get(), p.pathImage.get(), p.dateInMillis.get(), null);
        addPersonRepo.addPerson(personData);
    }

    @Override
    public String getPhone(Uri uri) {
        return phoneRetriever.getPhone(uri);
    }
}