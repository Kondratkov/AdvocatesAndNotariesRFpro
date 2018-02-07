package kondratkov.advocatesandnotariesrfpro.api_classes;

import kondratkov.advocatesandnotariesrfpro.account.Address;
import kondratkov.advocatesandnotariesrfpro.account.Region;

/**
 * Created by MODEST_PC on 02.12.2016.
 */


public class Notary {
    public int Id=0;
    public String Name = "";
    public String Surname = "";
    public String Patronymic = "";
    public String Fio ="";
    public Region Region = null;
    public Address Address = null;
    public String Address2 = "";
    public String LicenseNumber = "";
    public boolean IsPleadtingHereditaryCases = false;
    public boolean IsSitesCertification = false;
    public boolean DepositsReception = false;
    public boolean Equipage = false;
    public double Rating = 0;
    public boolean WorkInOffDays = false;
    public String ExtendedInfo = "";
    public ContactsData Contacts;

}
