package kondratkov.advocatesandnotariesrfpro.dop_dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.account.Address;
import kondratkov.advocatesandnotariesrfpro.account.City;
import kondratkov.advocatesandnotariesrfpro.account.Region;

/**
 * Created by MODEST_PC on 28.11.2016.
 */

public class Dop_dialog_address {

    public String url;
    public Context context= null;
    public Address address;
    public String region;
    public String city;

    private EditText et_region, et_city, et_street, et_street_number, et_buildingNumber;

    public i_dialog_address i_di_ad;

    public Dop_dialog_address(Context context, String url, Address address){
        this.context = context;
        this.url = url;
        this.address = address;
        region = address.Region;
        city = address.City;
    }

    public void openDialogAddress(){
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Адрес");
        dialog.setContentView(R.layout.dop_dialog_address);

        i_di_ad = (i_dialog_address) context;

        dialog.findViewById(R.id.button_dilog_address_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.button_dilog_address_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                address.Region = region;
                address.City = city;
                address.Street = String.valueOf(et_street.getText());
                address.StreetNumber = String.valueOf(et_street_number.getText());
                address.BuildingNumber = String.valueOf(et_buildingNumber.getText());
                String START;
                START = address.City;
                START = address.Region;
                START = new Gson().toJson(address.Region);
                START = address.Region;
                START = address.Street;
                String s = new Gson().toJson(address);
                s="sdfsd";
                i_di_ad.iv_dialog_address(address);
                dialog.dismiss();
            }
        });

        et_region = (EditText)dialog.findViewById(R.id.et_dilog_address_region);
        et_city = (EditText)dialog.findViewById(R.id.et_dilog_address_city);
        et_street= (EditText)dialog.findViewById(R.id.et_dilog_address_street);
        et_street_number= (EditText)dialog.findViewById(R.id.et_dilog_address_street_number);
        et_buildingNumber= (EditText)dialog.findViewById(R.id.et_dilog_address_buildingNumber);

        if(address != null) {
            et_region.setText(address.Region);
            et_city.setText(address.City);
            et_street.setText(address.Street);
            et_street_number.setText(address.StreetNumber);
            et_buildingNumber.setText(address.BuildingNumber);
        }
        dialog.show();
    }

    public static interface i_dialog_address{
        public void iv_dialog_address(Address address);
    }


}
