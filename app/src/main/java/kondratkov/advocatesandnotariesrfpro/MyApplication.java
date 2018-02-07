package kondratkov.advocatesandnotariesrfpro;

import android.app.Application;

import kondratkov.advocatesandnotariesrfpro.api_classes.BaseJuristAccount;
import kondratkov.advocatesandnotariesrfpro.api_classes.ClientQuestion;

/**
 * Created by kondratkov on 16.12.2017.
 */

public class MyApplication extends Application {

    private BaseJuristAccount mBaseJuristAccount;

    private ClientQuestion mClientQuestion;

    private static MyApplication singleton;
    // Возвращает экземпляр данного класса
    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        singleton = this;

    }

    public BaseJuristAccount getBaseJuristAccount() {
        return mBaseJuristAccount;
    }

    public void setBaseJuristAccount(BaseJuristAccount baseJuristAccount) {
        mBaseJuristAccount = baseJuristAccount;
    }

    public ClientQuestion getClientQuestion() {
        return mClientQuestion;
    }

    public void setClientQuestion(ClientQuestion clientQuestion) {
        mClientQuestion = clientQuestion;
    }
}
