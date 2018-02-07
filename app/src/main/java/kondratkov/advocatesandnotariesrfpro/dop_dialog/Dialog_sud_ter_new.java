package kondratkov.advocatesandnotariesrfpro.dop_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import kondratkov.advocatesandnotariesrfpro.IN;
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.account.City;
import kondratkov.advocatesandnotariesrfpro.account.JudicialAreas;
import kondratkov.advocatesandnotariesrfpro.account.Region;

public class Dialog_sud_ter_new implements
        SearchView.OnQueryTextListener{

    public int SudTer_num = 0;
    public String Region_string = "";
    public String SudTer_string = "";

    public String[] str_region = null;
    public int[] int_region = null;
    public String[] str_SudTer = null;
    public String[] int_str_SudTer = null;
    public Spinner spinner = null;

    public Activity activity = null;
    public Context context = null;
    public String SudTer_url = "";
    public String SudTer_name = "";
    public String SudTer_id = "";
    public i_dialog_sudTer di_r;
    IN in;

    JudicialAreas post_SudTer;
    Region region;
    Region[] mcArrayRegions;
    JudicialAreas[] mcArraySudTer;
    public int id_SudTer = 0;

    public ProgressBar progressBar;
    public FrameLayout frame;

    public Spinner spinner_region;
    public ListView listView;
    public SearchView mSearchView;
    public TextView mTextViewSudter;


    public Dialog_sud_ter_new(Context context, String url) {
        this.context = context;
        this.SudTer_url = url;
    }

    public void openDialogSudTer() {


        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Выбор города");
        dialog.setContentView(R.layout.dop_dialog_sudter);

        di_r = (i_dialog_sudTer) context;

        frame = (FrameLayout) dialog.findViewById(R.id.frame_dialog);
        progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar_dialog);

        frame.setBackgroundResource(R.color.frameOn);
        frame.setClickable(true);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        mTextViewSudter = (TextView)dialog.findViewById(R.id.mTextViewSudter);

        spinner_region = (Spinner) dialog.findViewById(R.id.dialog_sort_spinner_region);
        listView = (ListView) dialog.findViewById(R.id.listView_dop_sudter);

        mSearchView = (SearchView) dialog.findViewById(R.id.searchView_dop_sudter);
        setupSearchView();

        Button btnDismiss = (Button) dialog.getWindow().findViewById(
                R.id.dialog_sort_button_close);
        Button btnmiss = (Button) dialog.getWindow().findViewById(
                R.id.dialog_sort_button_yes);


        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region.Cities[id_SudTer].Region = region;
                String START;
                START = region.JudicialAreas[id_SudTer].Name;
                START = region.Name;
                START = new Gson().toJson(region);
                START = region.Name;
                di_r.iv_onSudTer(region.JudicialAreas[id_SudTer], region);
                dialog.dismiss();
            }
        });

        dialog.show();
        new UrlConnectionTask().execute();
        // /new AsyncTaskDialog().execute();
    }
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("поиск судебной территории");
    }

    public void stopProgressBar() {
        frame.setBackgroundResource(R.color.frameOff);
        frame.setClickable(false);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void onSpinner() {

        /*try {
            JSONObject jsonObject = new JSONObject(Region_string);
            JSONArray jsonArray = jsonObject.getJSONArray("array");

            for(int i = 0; i< jsonArray.length(); i++ ){
                str_region[i] = jsonArray.getJSONObject(i).getString("name");
                int_region[i] = jsonArray.getJSONObject(i).getInt("id");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        try {
            str_region = new String[mcArrayRegions.length];
            int_region = new int[mcArrayRegions.length];
        }catch (Exception e){

        }
        for (int i = 0; i < mcArrayRegions.length; i++) {
            str_region[i] = mcArrayRegions[i].Name;
            int_region[i] = mcArrayRegions[i].Id;
        }

        final ArrayAdapter<String> adapter_region = new ArrayAdapter<String>(context, R.layout.my_quest_item_spinner, str_region);
        //ArrayAdapter.createFromResource(this, R.layout.my_quest_item_spinner, new String(){""});
        spinner_region.setAdapter(adapter_region);
        spinner_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                SudTer_num = int_region[Integer.parseInt(String.valueOf(id))];
                //new AsyncTaskDialog().execute();
                new UrlConnectionTask().execute();
            }
        });
        /*spinner_SudTer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                SudTer_name = str_SudTer[pos];
                post_SudTer = region.JudicialAreas[pos];
                id_SudTer = (int) id;
                stopProgressBar();
            }
        });*/
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText.toString());
        }
        return true;
    }

    class UrlConnectionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";

            /*try {
                URL url = new URL("http://"+in.get_url()+"/TopQuestions/GetTopQuestions");
                URLConnection urlConnection = url.openConnection();


                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }*/

            OkHttpClient client = new OkHttpClient();
            Request request = null;

            if (SudTer_num == 0) {
                request = new Request.Builder()
                        .url("http://" + SudTer_url + "/Regions/GetRegions")
                        .build();
            } else {
                request = new Request.Builder()
                        .url("http://" + SudTer_url + "/Regions/GetRegion/" + SudTer_num)
                        .build();
            }

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                result = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            Gson gg = new Gson();
            if(result==null){}else {

                if (SudTer_num == 0) {
                    mcArrayRegions = gg.fromJson(result, Region[].class);
                    Region_string = result;
                    SudTer_num = 1;
                    onSpinner();
                } else {
                    region = gg.fromJson(result, Region.class);
                    str_SudTer = new String[region.JudicialAreas.length];
                    for (int i = 0; i < region.JudicialAreas.length; i++) {
                        Log.d("qwerty", String.valueOf(i));
                        str_SudTer[i] = region.JudicialAreas[i].Name;
                    }
                    ArrayAdapter<String> adapter_SudTer = new ArrayAdapter<String>(context,
                            R.layout.my_quest_item_spinner, str_SudTer);
                    //ArrayAdapter.createFromResource(this, R.layout.my_quest_item_spinner, new String(){""});
                    listView.setAdapter(adapter_SudTer);
                    listView.setTextFilterEnabled(true);
                    mTextViewSudter.setText("");
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position,
                                                long id) {

                            mTextViewSudter.setText(((TextView) v).getText());
                            //mTextViewSudter.setText(region.JudicialAreas[position].Name);
                            mSearchView.setQuery("", false);
                            mSearchView.clearFocus();
                            mSearchView.setIconified(true);
                            for (int i = 0; i < region.JudicialAreas.length; i++) {
                                if (mTextViewSudter.getText().equals(region.JudicialAreas[i].Name)) {
                                    SudTer_name = str_SudTer[i];
                                    post_SudTer = region.JudicialAreas[i];
                                    id_SudTer = i;
                                    stopProgressBar();
                                }
                            }

                        /*in.set_idt(msArrayclientQuestion[position].Id);
                        in.set_clientQuestion(msArrayclientQuestion[position]);
                        in.set_quest(0);
                        mSearchView.ad*/
                        }
                    });

                    setupSearchView();
                    //spinner_SudTer.setAdapter(adapter_SudTer);
                    stopProgressBar();
                }
            }
            super.onPostExecute(result);
        }
    }

    public static interface i_dialog_sudTer {
        public void iv_onSudTer(JudicialAreas judicialAreas, Region region);

    }
}

