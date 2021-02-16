package hr.unios.ferit.leafclassification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.unios.ferit.leafclassification.Model.Output;
import hr.unios.ferit.leafclassification.Model.Prediction;
import hr.unios.ferit.leafclassification.Model.TrainSample;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    List<TrainSample> trainSamples = new ArrayList<TrainSample>();
    TrainSample selectedSample;
    TextView scoredLabels;
    TextView scoredProbabilitiesPercentage;
    TextView scoredTitle;
    Button btnPredict;
    Spinner sampleSpinner;
    TextView selectedTestSample;
    ProgressBar predictProgress;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
        setUpSpinner();
    }

    private void setUpUI() {
        this.scoredLabels = (TextView) findViewById(R.id.tvScoredLabelsValue);
        this.scoredProbabilitiesPercentage = (TextView) findViewById(R.id.tvScoredProbabilitiesValue);
        this.scoredTitle = (TextView) findViewById(R.id.tvSampleName);
        this.btnPredict = (Button) findViewById(R.id.btnPredict);
        this.sampleSpinner = (Spinner) findViewById(R.id.sampleSpinner);
        this.selectedTestSample = (TextView) findViewById(R.id.tvSampleView);
        this.predictProgress = (ProgressBar) findViewById(R.id.predictProgress);

        btnPredict.setOnClickListener(this);
    }

    private void setUpSpinner() {
        // Spinner click listener
        sampleSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        trainSamples.add(new TrainSample("Sample 1", "{\r\n            \"id\":27,\r\n            \"species\": \"\",\r\n            \"margin1\":111,\r\n            \"margin2\":0,\r\n            \"margin3\":0,\r\n            \"margin4\":0.001953,\r\n            \"margin5\":0.029297,\r\n            \"margin6\":0.11133,\r\n            \"margin7\":0,\r\n            \"margin8\":0,\r\n            \"margin9\":0,\r\n            \"margin10\":0.029297,\r\n            \"margin11\":0,\r\n            \"margin12\":0,\r\n            \"margin13\":0,\r\n            \"margin14\":0,\r\n            \"margin15\":0.023438,\r\n            \"margin16\":0.001953,\r\n            \"margin17\":0,\r\n            \"margin18\":0.005859,\r\n            \"margin19\":0,\r\n            \"margin20\":0.11719,\r\n            \"margin21\":0.003906,\r\n            \"margin22\":0,\r\n            \"margin23\":0,\r\n            \"margin24\":0.019531,\r\n            \"margin25\":0.017578,\r\n            \"margin26\":0.011719,\r\n            \"margin27\":0.011719,\r\n            \"margin28\":0.015625,\r\n            \"margin29\":0.041016,\r\n            \"margin30\":0,\r\n            \"margin31\":0.009766,\r\n            \"margin32\":0.035156,\r\n            \"margin33\":0.050781,\r\n            \"margin34\":0,\r\n            \"margin35\":0.003906,\r\n            \"margin36\":0,\r\n            \"margin37\":0,\r\n            \"margin38\":0.003906,\r\n            \"margin39\":0.001953,\r\n            \"margin40\":0.003906,\r\n            \"margin41\":0.007812,\r\n            \"margin42\":0.17969,\r\n            \"margin43\":0.007812,\r\n            \"margin44\":0,\r\n            \"margin45\":0.021484,\r\n            \"margin46\":0,\r\n            \"margin47\":0.029297,\r\n            \"margin48\":0.001953,\r\n            \"margin49\":0,\r\n            \"margin50\":0.054688,\r\n            \"margin51\":0,\r\n            \"margin52\":0,\r\n            \"margin53\":0.009766,\r\n            \"margin54\":0.009766,\r\n            \"margin55\":0.035156,\r\n            \"margin56\":0,\r\n            \"margin57\":0,\r\n            \"margin58\":0,\r\n            \"margin59\":0.007812,\r\n            \"margin60\":0.005859,\r\n            \"margin61\":0.060547,\r\n            \"margin62\":0.003906,\r\n            \"margin63\":0.007812,\r\n            \"margin64\":0.001953,\r\n            \"shape1\":0.003906,\r\n            \"shape2\":0.0011448,\r\n            \"shape3\":0.0010666,\r\n            \"shape4\":0.00099556,\r\n            \"shape5\":0.00092949,\r\n            \"shape6\":0.00087691,\r\n            \"shape7\":0.00084537,\r\n            \"shape8\":0.00081038,\r\n            \"shape9\":0.00078007,\r\n            \"shape10\":0.00076342,\r\n            \"shape11\":0.00074886,\r\n            \"shape12\":0.00073552,\r\n            \"shape13\":0.00072681,\r\n            \"shape14\":0.00071813,\r\n            \"shape15\":0.0007155,\r\n            \"shape16\":0.00071266,\r\n            \"shape17\":0.00070683,\r\n            \"shape18\":0.00069732,\r\n            \"shape19\":0.00069564,\r\n            \"shape20\":0.0006992,\r\n            \"shape21\":0.00070635,\r\n            \"shape22\":0.0007099,\r\n            \"shape23\":0.000721,\r\n            \"shape24\":0.00072663,\r\n            \"shape25\":0.00073689,\r\n            \"shape26\":0.0007597,\r\n            \"shape27\":0.00078228,\r\n            \"shape28\":0.00079956,\r\n            \"shape29\":0.0008285,\r\n            \"shape30\":0.00085514,\r\n            \"shape31\":0.00088472,\r\n            \"shape32\":0.00091699,\r\n            \"shape33\":0.00094766,\r\n            \"shape34\":0.0009681,\r\n            \"shape35\":0.00096525,\r\n            \"shape36\":0.0009356,\r\n            \"shape37\":0.00089393,\r\n            \"shape38\":0.00085251,\r\n            \"shape39\":0.00081467,\r\n            \"shape40\":0.0007849,\r\n            \"shape41\":0.00076042,\r\n            \"shape42\":0.00074401,\r\n            \"shape43\":0.00072258,\r\n            \"shape44\":0.00070355,\r\n            \"shape45\":0.00068658,\r\n            \"shape46\":0.0006727,\r\n            \"shape47\":0.00066767,\r\n            \"shape48\":0.00066309,\r\n            \"shape49\":0.00066297,\r\n            \"shape50\":0.00067193,\r\n            \"shape51\":0.00067734,\r\n            \"shape52\":0.00069616,\r\n            \"shape53\":0.00071134,\r\n            \"shape54\":0.00073405,\r\n            \"shape55\":0.00075179,\r\n            \"shape56\":0.00077728,\r\n            \"shape57\":0.00079007,\r\n            \"shape58\":0.00080324,\r\n            \"shape59\":0.00083197,\r\n            \"shape60\":0.00085598,\r\n            \"shape61\":0.00087554,\r\n            \"shape62\":0.0009023,\r\n            \"shape63\":0.00094291,\r\n            \"shape64\":0.0010191,\r\n            \"texture1\":0.0010989,\r\n            \"texture2\":0.003906,\r\n            \"texture3\":0,\r\n            \"texture4\":0.007812,\r\n            \"texture5\":0,\r\n            \"texture6\":0.13281,\r\n            \"texture7\":0,\r\n            \"texture8\":0.000977,\r\n            \"texture9\":0.026367,\r\n            \"texture10\":0,\r\n            \"texture11\":0,\r\n            \"texture12\":0,\r\n            \"texture13\":0.37793,\r\n            \"texture14\":0,\r\n            \"texture15\":0,\r\n            \"texture16\":0,\r\n            \"texture17\":0,\r\n            \"texture18\":0,\r\n            \"texture19\":0,\r\n            \"texture20\":0.000977,\r\n            \"texture21\":0.001953,\r\n            \"texture22\":0,\r\n            \"texture23\":0,\r\n            \"texture24\":0.068359,\r\n            \"texture25\":0,\r\n            \"texture26\":0.001953,\r\n            \"texture27\":0,\r\n            \"texture28\":0.000977,\r\n            \"texture29\":0.017578,\r\n            \"texture30\":0.004883,\r\n            \"texture31\":0,\r\n            \"texture32\":0.037109,\r\n            \"texture33\":0,\r\n            \"texture34\":0,\r\n            \"texture35\":0.008789,\r\n            \"texture36\":0,\r\n            \"texture37\":0,\r\n            \"texture38\":0,\r\n            \"texture39\":0.055664,\r\n            \"texture40\":0.003906,\r\n            \"texture41\":0,\r\n            \"texture42\":0,\r\n            \"texture43\":0,\r\n            \"texture44\":0,\r\n            \"texture45\":0,\r\n            \"texture46\":0.039062,\r\n            \"texture47\":0.051758,\r\n            \"texture48\":0.007812,\r\n            \"texture49\":0.037109,\r\n            \"texture50\":0,\r\n            \"texture51\":0,\r\n            \"texture52\":0,\r\n            \"texture53\":0,\r\n            \"texture54\":0,\r\n            \"texture55\":0.000977,\r\n            \"texture56\":0.012695,\r\n            \"texture57\":0,\r\n            \"texture58\":0.079102,\r\n            \"texture59\":0,\r\n            \"texture60\":0,\r\n            \"texture61\":0,\r\n            \"texture62\":0,\r\n            \"texture63\":0,\r\n            \"texture64\":0\r\n         }\r\n"));
        trainSamples.add(new TrainSample("Sample 2", ""));
        trainSamples.add(new TrainSample("Sample 3", ""));
        trainSamples.add(new TrainSample("Sample 4", ""));

        List<String> titles = new ArrayList<String>();
        for (TrainSample sample: trainSamples) {
            titles.add(sample.getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sampleSpinner.setAdapter(dataAdapter);
    }

    private void predict(TrainSample sample) {
        final Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = "{\n" +
                "   \"Inputs\":{\n" +
                "      \"input1\":[\n" +
                "" + sample.getData() + "]\r\n   },\r\n   \"GlobalParameters\":{\r\n      \r\n   }\r\n}";
        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
                .url("https://ussouthcentral.services.azureml.net/workspaces/0c1678408c4c4bec9af2914f741c445e/services/f8ebcfcc52344250bf19cbc1fa8810a8/execute?api-version=2.0&format=swagger")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer XNl1MK3yxQV7Z/L+covR9ii07h/ZZpVxHoAtIGfWruMubjajrWFwFvO4CUouiG0TxYX7eVT3aLknck7fqzmhig==")
                .build();

        predictProgress.setVisibility(View.VISIBLE);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // ... check for failure using `isSuccessful` before proceeding

                // Read data on the worker thread
                Prediction prediction = gson.fromJson(Objects.requireNonNull(response.body()).charStream(), Prediction.class);

                // Run view-related code back on the main thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Output output = prediction.Results.output1.get(0);
                        Double scoredProbability = Double.parseDouble(getScoredProbabilities(output)) * 100;
                        int probabilitiesPercentage = (int)Math.round(scoredProbability);
                        scoredLabels.setText(output.getScoredLabels());
                        scoredTitle.setText(sample.getName());
                        scoredProbabilitiesPercentage.setText(probabilitiesPercentage + "%");
                        predictProgress.setVisibility(View.GONE);

                        if(scoredProbability >= 0 && probabilitiesPercentage < 50){
                            scoredProbabilitiesPercentage.setTextColor(getResources().getColor(R.color.red));
                        }
                        else {
                            scoredProbabilitiesPercentage.setTextColor(getResources().getColor(R.color.green));
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

        });
    }

    private String getScoredProbabilities(Output output) {
        String scoredLabels = output.getScoredLabels();
        if(scoredLabels.equals("Acer_Capillipes")) {
            return output.getScoredProbabilitiesForClassAcerCapillipes();
        }
        else if(scoredLabels.equals("Acer_Circinatum")) {
            return output.getScoredProbabilitiesForClassAcerCircinatum();
        }
        else if(scoredLabels.equals("Acer_Mono")) {
            return output.getScoredProbabilitiesForClassAcerMono();
        }
        else if(scoredLabels.equals("Acer_Opalus")) {
            return output.getScoredProbabilitiesForClassAcerOpalus();
        }
        else if(scoredLabels.equals("Acer_Palmatum")) {
            return output.getScoredProbabilitiesForClassAcerPalmatum();
        }
        else if(scoredLabels.equals("Acer_Pictum")) {
            return output.getScoredProbabilitiesForClassAcerPictum();
        }
        else if(scoredLabels.equals("Acer_Platanoids")) {
            return output.getScoredProbabilitiesForClassAcerPlatanoids();
        }
        else if(scoredLabels.equals("Acer_Rubrum")) {
            return output.getScoredProbabilitiesForClassAcerRubrum();
        }
        else if(scoredLabels.equals("Acer_Rufinerve")) {
            return output.getScoredProbabilitiesForClassAcerRufinerve();
        }
        else if(scoredLabels.equals("Acer_Saccharinu")) {
            return output.getScoredProbabilitiesForClassAcerSaccharinum();
        }
        else if(scoredLabels.equals("Alnus_Cordata")) {
            return output.getScoredProbabilitiesForClassAlnusCordata();
        }
        else if(scoredLabels.equals("Alnus_Maximowiczii")) {
            return output.getScoredProbabilitiesForClassAlnusMaximowiczii();
        }
        else if(scoredLabels.equals("Alnus_Rubra")) {
            return output.getScoredProbabilitiesForClassAlnusRubra();
        }
        else if(scoredLabels.equals("Alnus_Sieboldiana")) {
            return output.getScoredProbabilitiesForClassAlnusSieboldiana();
        }else if(scoredLabels.equals("Alnus_Viridis")) {
            return output.getScoredProbabilitiesForClassAlnusViridis();
        }
        else if(scoredLabels.equals("Arundinaria_Simonii")) {
            return output.getScoredProbabilitiesForClassArundinariaSimonii();
        }
        else if(scoredLabels.equals("Betula_Austrosinensis")) {
            return output.getScoredProbabilitiesForClassBetulaAustrosinensis();
        }
        else if(scoredLabels.equals("Betula_Pendula")) {
            return output.getScoredProbabilitiesForClassBetulaPendula();
        }
        else if(scoredLabels.equals("Callicarpa_Bodinieri")) {
            return output.getScoredProbabilitiesForClassCallicarpaBodinieri();
        }
        else if(scoredLabels.equals("Castanea_Sativa")) {
            return output.getScoredProbabilitiesForClassCastaneaSativa();
        }
        else if(scoredLabels.equals("Celtis_Koraiensis")) {
            return output.getScoredProbabilitiesForClassCeltisKoraiensis();
        }
        else if(scoredLabels.equals("Cercis_Siliquastrum")) {
            return output.getScoredProbabilitiesForClassCercisSiliquastrum();
        }
        else if(scoredLabels.equals("Cornus_Chinensis")) {
            return output.getScoredProbabilitiesForClassCornusChinensis();
        }
        else if(scoredLabels.equals("Cornus_Controversa")) {
            return output.getScoredProbabilitiesForClassCornusControversa();
        }else if(scoredLabels.equals("Cornus_Macrophylla")) {
            return output.getScoredProbabilitiesForClassCornusMacrophylla();
        }else if(scoredLabels.equals("Cotinus_Coggygria")) {
            return output.getScoredProbabilitiesForClassCotinusCoggygria();
        }
        else if(scoredLabels.equals("Crataegus_Monogyna")) {
            return output.getScoredProbabilitiesForClassCrataegusMonogyna();
        }else if(scoredLabels.equals("Cytisus_Battandieri")) {
            return output.getScoredProbabilitiesForClassCytisusBattandieri();
        }
        else if(scoredLabels.equals("Eucalyptus_Glaucescens")) {
            return output.getScoredProbabilitiesForClassEucalyptusGlaucescens();
        }
        else if(scoredLabels.equals("Eucalyptus_Neglecta")) {
            return output.getScoredProbabilitiesForClassEucalyptusNeglecta();
        }
        else if(scoredLabels.equals("Eucalyptus_Urnigera")) {
            return output.getScoredProbabilitiesForClassEucalyptusUrnigera();
        }
        else if(scoredLabels.equals("Fagus_Sylvatica")) {
            return output.getScoredProbabilitiesForClassFagusSylvatica();
        }else if(scoredLabels.equals("Ginkgo_Biloba")) {
            return output.getScoredProbabilitiesForClassGinkgoBiloba();
        }
        else if(scoredLabels.equals("Ilex_Aquifolium")) {
            return output.getScoredProbabilitiesForClassIlexAquifolium();
        }
        else if(scoredLabels.equals("Ilex_Cornuta")) {
            return output.getScoredProbabilitiesForClassIlexCornuta();
        }
        else if(scoredLabels.equals("Liquidambar_Styraciflua")) {
            return output.getScoredProbabilitiesForClassLiquidambarStyraciflua();
        }
        else if(scoredLabels.equals("Liriodendron_Tulipifera")) {
            return output.getScoredProbabilitiesForClassLiriodendronTulipifera();
        }else if(scoredLabels.equals("Lithocarpus_Cleistocarpus")) {
            return output.getScoredProbabilitiesForClassLithocarpusCleistocarpus();
        }else if(scoredLabels.equals("Lithocarpus_Edulis")) {
            return output.getScoredProbabilitiesForClassLithocarpusEdulis();
        }else if(scoredLabels.equals("Magnolia_Heptapeta")) {
            return output.getScoredProbabilitiesForClassMagnoliaHeptapeta();
        }else if(scoredLabels.equals("Magnolia_Salicifolia")) {
            return output.getScoredProbabilitiesForClassMagnoliaSalicifolia();
        }else if(scoredLabels.equals("Morus_Nigra")) {
            return output.getScoredProbabilitiesForClassMorusNigra();
        }else if(scoredLabels.equals("Olea_Europaea")) {
            return output.getScoredProbabilitiesForClassOleaEuropaea();
        }
        return "0.0";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        selectedSample = trainSamples.get(0);
        showSelectedSample(position);
    }

    private void showSelectedSample(int position) {
        TrainSample selectedSample = trainSamples.get(position);
        selectedTestSample.setText(selectedSample.getData());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        predict(selectedSample);
    }
}