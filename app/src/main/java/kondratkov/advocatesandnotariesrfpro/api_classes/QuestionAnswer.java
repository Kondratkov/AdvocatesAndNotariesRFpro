package kondratkov.advocatesandnotariesrfpro.api_classes;


import kondratkov.advocatesandnotariesrfpro.account.DutyJuristAccount;

/**
 * Created by xaratyan on 03.12.2016.
 */

public class QuestionAnswer {
    public int Id;// { get; set; }
    /// <summary>
    /// Id вопроса
    /// </summary>
    public ClientQuestion Question;// { get; set; }
    /// <summary>
    /// Тело ответа
    /// </summary>
    public String Body;// { get; set; }
    /// <summary>
    /// юрист ответивший на вопрос
    /// </summary>
    public BaseJuristAccount Account;// { get; set; }
    /// <summary>
    /// Дата ответа
    /// </summary>
    public String Date;// { get; set; }
    /// <summary>
    /// Признак того что прочтено клиентом
    /// </summary>
    public accountType AccountType;
    public boolean IsReaded;// { get; set; }

    public enum accountType{
        Client,
        Jurist,
        DutyJurist,
        Moderator
    }
}
