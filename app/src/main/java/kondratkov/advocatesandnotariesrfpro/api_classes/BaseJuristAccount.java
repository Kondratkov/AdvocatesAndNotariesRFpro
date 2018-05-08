package kondratkov.advocatesandnotariesrfpro.api_classes;


import java.io.File;

import kondratkov.advocatesandnotariesrfpro.account.Address;
import kondratkov.advocatesandnotariesrfpro.account.Bup;
import kondratkov.advocatesandnotariesrfpro.account.JudicialAreas;
import kondratkov.advocatesandnotariesrfpro.account.JuristOrganisation;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecialization;

/**
 * Created by xaratyan on 05.12.2016.
 */

public class BaseJuristAccount {
    /// <summary>
    /// Номер в реестре АП
    /// </summary>
    public String NumberInApReestr ;//{ get; set; }
    /// <summary>
    /// Статус адвоката присвоен кем
    /// </summary>
    public String JuristStatusAssignedBy;// { get; set; }
    /// <summary>
    /// Стаж адвоката
    /// </summary>
    public String ExperienceLevel;//{ get; set; }
    /// <summary>
    /// Организация адвоката
    /// </summary>
    public JuristOrganisation JuristOrganisation ;//{ get; set; }
    /// <summary>
    /// Квалификация
    /// </summary>
    public String Qualification ;//{ get; set; }
    /// <summary>
    /// Ученая степень
    /// </summary>
    public String PhdInfo ;//{ get; set; }
    /// <summary>
    /// Владение иностранными языками
    /// </summary>
    public String LanguagesInfo;// { get; set; }
    /// <summary>
    /// Срочный выезд
    /// </summary>
    public boolean CanFastComing;// { get; set; }
    /// <summary>
    /// Членство во ФСАР
    /// </summary>
    public boolean IsFsarMember;// { get; set; }

    public String FsarMamberDate;// { get; set; }
    /// <summary>
    /// региональное отделение ФСАР
    /// </summary>
    public String FsarRegionalOffice;// { get; set; }

    /// <summary>
    /// Доп.офиц.данные и характеристики: Награды и т.д.
    /// </summary>
    public String ExtendedInfo;//{ get; set; }
    /// <summary>
    ///  Рейтинг
    /// </summary>
    public double Rating ;//{ get; set; }
    /// <summary>
    /// Работа в выходные дни
    /// </summary>
    public boolean WorkInOffDays;// { get; set; }
    /// <summary>
    /// БЮПы
    /// </summary>
    public Bup Bups;// { get; set; }
    /// <summary>
    /// Наличие взысканий
    /// </summary>
    public boolean HasRecoveries ;//{ get; set; }
    /// <summary>
    /// Судебная территория
    /// </summary>
    public JudicialAreas JudicialArea;// { get; set; }
    /// <summary>
    /// Специализации адвоката
    /// </summary>
    public JuristSpecialization[] Specializations ;//{ get; set; }
    /// <summary>
    /// Наставничество (имеет ли помощников/стажеров) да/нет
    /// </summary>
    public boolean Tutorship;// { get; set; }

    public int Id;
    public String Login;
    public String Fio;
    public String Name;
    public String Surename;
    public String Patronymic;
    public String Email;
    public String Phone;
    public String Site;
    public boolean IsOnline;
    public byte[] Image;
    public String LastActivityTime ;
    public AccountTypes AccountType;
    public kondratkov.advocatesandnotariesrfpro.account.Address Address;
    public double Longitude;
    public double Latitude;
    public double CurrentLongitude;
    public double CurrentLatitude;
    public String ImageUrl;
    //public File Image;

    public enum AccountTypes
    {
        Client,
        Jurist,
        DutyJurist
    }

}
