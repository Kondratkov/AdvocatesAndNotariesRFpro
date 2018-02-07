package kondratkov.advocatesandnotariesrfpro.api_classes;


import kondratkov.advocatesandnotariesrfpro.account.ClientAccount;
import kondratkov.advocatesandnotariesrfpro.account.DutyJuristAccount;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecializationSector;

/**
 * Created by xaratyan on 04.12.2016.
 */

public class DocumentOrder {
    public int Id;// { get; set; }
    /// <summary>
    /// Id заказа
    /// </summary>
    public String Uin;// => $"DHK-{Id:00000000}";
    /// <summary>
    /// Специализация юриста
    /// </summary>
    public JuristSpecializationSector JuristSpecialization;// { get; set; }
    /// <summary>
    /// Предпочтительный способ связи
    /// </summary>
    public PreferredContactMethod ContactMethod;// { get; set; }
    /// <summary>
    /// Тип документа
    /// </summary>
    public DocumentTyp DocumentType;// { get; set; }
    /// <summary>
    /// Кратко суть вопроса по заказу доумента
    /// </summary>
    public String Description;// { get; set; }
    /// <summary>
    /// Причение к заказу документа
    /// </summary>
    public String DocumentNote;// { get; set; }
    /// <summary>
    /// Id клиента
    /// </summary>
    public int ClientId;// { get; set; }
    /// <summary>
    /// Аккаунт клиента (можно не заполнять)
    /// </summary>
    public ClientAccount ClientAccount;// { get; set; }
    /// <summary>
    /// Документы прикрепленные к заказу клиентом
    /// </summary>
    public DocumentOrderFile[] ClientDocuments;// { get; set; }
    /// <summary>
    /// Документы прикрепленные к заказу юристом после оплаты
    /// </summary>
    public DocumentOrderFile[] JuristDocuments;// { get; set; }
    /// <summary>
    /// Id юриста
    /// </summary>
    public int JuristId;// { get; set; }
    /// <summary>
    /// Назначенный адвокат/Взял в паркинг
    /// </summary>
    public DutyJuristAccount JuristAccount;// { get; set; }

    /// <summary>
    /// Текущий статус заказа
    /// </summary>
    public ServiceState State;// { get; set; }

    /// <summary>
    /// Счет на оплату
    /// </summary>
    public Order Order;// { get; set; }
    public Comment[] Comments;

    /// <summary>
    /// Виды документов
    /// </summary>
    public enum DocumentTyp {
        // Правовой кейс - тематический шаблон в виде архивного файла.Необходимо организовать систему направления клиенту самого архива или ссылки для скачивания!
        LegalCase,
        // Проверка добросовестности контрагента (дью дилидженс- лайт)
        CheckingIntegrityCounterparty,
        // Претензия
        Pretension,
        // Жалоба на действия должностных лиц
        ComplaintActionsOfficials,
        // Обращение в госорганы РФ
        AppealToStateBodies,
        // Заявление
        Statement,
        // Исковое заявление
        Plaint,
        // Возражение на иск
        ObjectionToClaim,
        // Обжалование решения суда
        AppealCourtDecision,
        // Иной документ
        AnotherDocument,
        // Затрудняюсь выбрать
        DifficultToChoose
    }
    public enum PreferredContactMethod
    {
        Email,
        Phone
    }
    public enum ServiceState
    {
        /// <summary>
        /// Стадия согласования объема и стоимости правовой помощи со специалистом
        /// </summary>
        MatchingStage,
        /// <summary>
        /// Согласовано, ожидается оплата. Означает, что система ожидает осуществления транзакции оплаты
        /// </summary>
        WaitingForPayment,
        /// <summary>
        /// Исполнение заказа. При этом статусе ведется подготовка заказанных услуг.
        /// </summary>
        InProgress,
        /// <summary>
        /// Ваш заказ готов. Означает, что услуга подготовлена(адвокат свяжется или документы будут отправлены на почту).
        /// </summary>
        Completed
    }
}
