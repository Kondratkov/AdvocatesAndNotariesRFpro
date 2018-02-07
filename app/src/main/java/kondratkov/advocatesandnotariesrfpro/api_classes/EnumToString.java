package kondratkov.advocatesandnotariesrfpro.api_classes;

/**
 * Created by xaratyan on 06.12.2016.
 */

public class EnumToString {
    public String DocumentTypString(DocumentOrder documentOrder){
        String s = "";
        if (documentOrder.DocumentType.Pretension==DocumentOrder.DocumentTyp.Pretension){
            s = "Претензия";
        }else if(documentOrder.DocumentType.ComplaintActionsOfficials==DocumentOrder.DocumentTyp.ComplaintActionsOfficials){
            s = "Жалоба на действия должностных лиц";
        }else if(documentOrder.DocumentType.AppealToStateBodies==DocumentOrder.DocumentTyp.AppealToStateBodies){
            s = "Обращение в госорганы РФ";
        }else if(documentOrder.DocumentType.Statement==DocumentOrder.DocumentTyp.Statement){
            s = "Заявление";
        }else if(documentOrder.DocumentType.Plaint==DocumentOrder.DocumentTyp.Plaint){
            s = "Исковое заявление";
        }else if(documentOrder.DocumentType.ObjectionToClaim==DocumentOrder.DocumentTyp.ObjectionToClaim){
            s = "Возражение на иск";
        }else if(documentOrder.DocumentType.AppealCourtDecision==DocumentOrder.DocumentTyp.AppealCourtDecision){
            s = "Обжалование решения суда";
        }else if(documentOrder.DocumentType.AnotherDocument==DocumentOrder.DocumentTyp.AnotherDocument){
            s = "Иной документ";
        }else if(documentOrder.DocumentType.DifficultToChoose==DocumentOrder.DocumentTyp.DifficultToChoose){
            s = "Затрудняюсь выбрать";
        }
        return s;
    }
}
