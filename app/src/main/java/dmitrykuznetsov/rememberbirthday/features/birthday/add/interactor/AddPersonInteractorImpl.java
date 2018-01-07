package dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor;

import android.content.Intent;
import android.net.Uri;

import com.theartofdev.edmodo.cropper.CropImage;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.model.Person;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;

/**
 * Created by dmitry on 25.05.17.
 */

public class AddPersonInteractorImpl implements AddPersonInteractor {

    private PersonRepo personRepo;
    private PhoneRetriever phoneRetriever;

    public AddPersonInteractorImpl(PersonRepo personRepo, PhoneRetriever phoneRetriever) {
        this.personRepo = personRepo;
        this.phoneRetriever = phoneRetriever;
    }

    @Override
    public void addPersonData(Person p) {
        int id = getNextIdPerson();
        PersonData personData = new PersonData(id, p.getName(), p.getNote(), p.getBindPhone(),
                p.getPathImage(), p.getDateInMillis()/*, null*/);
        personRepo.addPerson(personData);
    }

    @Override
    public String getPhone(Intent data) {
        Uri uri = data.getData();
        return phoneRetriever.getPhone(uri);
    }

    @Override
    public String getPathImage(Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        Uri resultUri = result.getUri();
        return resultUri.getPath();
    }

    private int getNextIdPerson() {
        int id = personRepo.getPersonLastId();
        id++;
        return id;
    }
}
