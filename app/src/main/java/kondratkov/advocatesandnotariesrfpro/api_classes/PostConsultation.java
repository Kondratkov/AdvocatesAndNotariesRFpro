package kondratkov.advocatesandnotariesrfpro.api_classes;

import java.util.Date;

import kondratkov.advocatesandnotariesrfpro.account.ClientAccount;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecializationSector;

/**
 * Created by xaratyan on 03.12.2016.
 */

public class PostConsultation {
    public int Id =0;
    public String Uin = "";
    public JuristSpecializationSector JuristSpecializationSector= null;
    public preferredContactMethod ContactMethod;
    public String ConsultationQuestion ="";
    public String ClientPreferredConsultationDate;
    public Date ConsultationDate;
    /// Примечания к заказу
    public String ClientNote="";
    public int ClientId;
    public ClientAccount ClientAccount;
    public int JuristId;
    public JuristAccounClass JuristAccountl;
    public ServiceState State;
    public Order Order;
    public Comment[] Comments;

    public enum preferredContactMethod
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
