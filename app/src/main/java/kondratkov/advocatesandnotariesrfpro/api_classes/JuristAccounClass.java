package kondratkov.advocatesandnotariesrfpro.api_classes;

import java.util.Date;

import kondratkov.advocatesandnotariesrfpro.account.Address;
import kondratkov.advocatesandnotariesrfpro.account.Bup;
import kondratkov.advocatesandnotariesrfpro.account.JudicialAreas;
import kondratkov.advocatesandnotariesrfpro.account.JuristOrganisation;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecialization;

/**
 * Created by xaratyan on 02.12.2016.
 */

public class JuristAccounClass {
    public int Id;
    /// Номер в реестре АП
    public String NumberInApReestr = "";
    /// Статус адвоката присвоен кем
    public String JuristStatusAssignedBy= "";
    /// Стаж адвоката
    public Date ExperienceLevel;
    /// Организация адвоката
    public JuristOrganisation JuristOrganisation = null;
    /// Квалификация
    public String Qualification= "";
    /// Ученая степень
    public String PhdInfo= "";
    /// Владение иностранными языками
    public String LanguagesInfo= "";
    /// Срочный выезд
    public boolean CanFastComing;
    /// Членство во ФСАР
    public boolean IsFsarMember;

    public Date FsarMamberDate;
    /// региональное отделение ФСАР
    public String FsarRegionalOffice= "";
    /// Доп.офиц.данные и характеристики: Награды и т.д.
    public String ExtendedInfo= "";
    ///  Рейтинг
    public double Rating;
    /// Работа в выходные дни
    public boolean WorkInOffDays ;
    /// БЮПы
    public Bup[] BupsTypes;
    /// Наличие взысканий
    public boolean HasRecoveries;
    /// Судебная территория
    public JudicialAreas JudicialArea;
    /// Специализации адвоката
    public JuristSpecialization[] Specializations ;
    /// Наставничество (имеет ли помощников/стажеров) да/нет
    public boolean Tutorship;// { get; set; }

    public accountType AccountType;
    public String Name = "";
    public String Surename = "";
    public String Patronymic = "";
    public String Email = "";
    public String Phone = "";
    public boolean IsOnline= false;
    public String Fio = "";
    public byte[] Image;
    public Date LastActivityTime ;
    public Address Address;
    public double Longitude;
    public double Latitude;

    public enum accountType
    {
        Client,
        Jurist,
        DutyJurist
    }
}
