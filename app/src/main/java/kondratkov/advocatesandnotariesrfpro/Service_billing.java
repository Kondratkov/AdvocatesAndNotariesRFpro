package kondratkov.advocatesandnotariesrfpro;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.android.vending.billing.IInAppBillingService;

public class Service_billing extends Service {
    final String LOG_TAG = "myLogs";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
    }

    IInAppBillingService mService ;
    ServiceConnection mServiceConn =  new  ServiceConnection()  {
        @Override
        public  void onServiceDisconnected ( ComponentName name )  {
            mService =  null ;
        }

        @Override
        public  void onServiceConnected ( ComponentName name ,
                                          IBinder service )  {
            mService =  IInAppBillingService . Stub . asInterface ( service );
        }
    };

    public IBinder onBind(Intent intent) {

        Log.d(LOG_TAG, "MyService onBind");
        return new Binder();
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(LOG_TAG, "MyService onRebind");
    }

    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "MyService onUnbind");
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
        Log.d(LOG_TAG, "MyService onDestroy");
    }
}
