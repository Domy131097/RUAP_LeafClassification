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
        trainSamples.add(new TrainSample("Sample 2", "{\r\n    \"id\": 137,\r\n    \"species\": \"\",\r\n    \"margin1\": 0.019531,\r\n    \"margin2\": 0.009766,\r\n    \"margin3\": 0.085938,\r\n    \"margin4\": 0.019531,\r\n    \"margin5\": 0.005859,\r\n    \"margin6\": 0.041016,\r\n    \"margin7\": 0.013672,\r\n    \"margin8\": 0.005859,\r\n    \"margin9\": 0,\r\n    \"margin10\": 0.003906,\r\n    \"margin11\": 0.011719,\r\n    \"margin12\": 0.013672,\r\n    \"margin13\": 0.070312,\r\n    \"margin14\": 0,\r\n    \"margin15\": 0.017578,\r\n    \"margin16\": 0,\r\n    \"margin17\": 0.011719,\r\n    \"margin18\": 0.015625,\r\n    \"margin19\": 0,\r\n    \"margin20\": 0.013672,\r\n    \"margin21\": 0.021484,\r\n    \"margin22\": 0,\r\n    \"margin23\": 0.005859,\r\n    \"margin24\": 0.003906,\r\n    \"margin25\": 0.001953,\r\n    \"margin26\": 0.023438,\r\n    \"margin27\": 0.001953,\r\n    \"margin28\": 0.025391,\r\n    \"margin29\": 0.033203,\r\n    \"margin30\": 0.017578,\r\n    \"margin31\": 0,\r\n    \"margin32\": 0.001953,\r\n    \"margin33\": 0.019531,\r\n    \"margin34\": 0.001953,\r\n    \"margin35\": 0.007812,\r\n    \"margin36\": 0.021484,\r\n    \"margin37\": 0.048828,\r\n    \"margin38\": 0.041016,\r\n    \"margin39\": 0.015625,\r\n    \"margin40\": 0.003906,\r\n    \"margin41\": 0.001953,\r\n    \"margin42\": 0.003906,\r\n    \"margin43\": 0.029297,\r\n    \"margin44\": 0.015625,\r\n    \"margin45\": 0.019531,\r\n    \"margin46\": 0,\r\n    \"margin47\": 0.023438,\r\n    \"margin48\": 0.042969,\r\n    \"margin49\": 0.001953,\r\n    \"margin50\": 0.007812,\r\n    \"margin51\": 0.021484,\r\n    \"margin52\": 0.003906,\r\n    \"margin53\": 0.015625,\r\n    \"margin54\": 0,\r\n    \"margin55\": 0,\r\n    \"margin56\": 0,\r\n    \"margin57\": 0.013672,\r\n    \"margin58\": 0.005859,\r\n    \"margin59\": 0.046875,\r\n    \"margin60\": 0.003906,\r\n    \"margin61\": 0.001953,\r\n    \"margin62\": 0,\r\n    \"margin63\": 0.083984,\r\n    \"margin64\": 0,\r\n    \"shape1\": 0.00078531,\r\n    \"shape2\": 0.00082262,\r\n    \"shape3\": 0.00086174,\r\n    \"shape4\": 0.00090976,\r\n    \"shape5\": 0.0009755,\r\n    \"shape6\": 0.0010432,\r\n    \"shape7\": 0.0011149,\r\n    \"shape8\": 0.001185,\r\n    \"shape9\": 0.0012599,\r\n    \"shape10\": 0.0013556,\r\n    \"shape11\": 0.0014533,\r\n    \"shape12\": 0.0015664,\r\n    \"shape13\": 0.0016829,\r\n    \"shape14\": 0.0017986,\r\n    \"shape15\": 0.0017609,\r\n    \"shape16\": 0.0016574,\r\n    \"shape17\": 0.0015645,\r\n    \"shape18\": 0.0014693,\r\n    \"shape19\": 0.0013817,\r\n    \"shape20\": 0.0013045,\r\n    \"shape21\": 0.0012292,\r\n    \"shape22\": 0.0011582,\r\n    \"shape23\": 0.0010914,\r\n    \"shape24\": 0.0010228,\r\n    \"shape25\": 0.00096848,\r\n    \"shape26\": 0.00091231,\r\n    \"shape27\": 0.00086042,\r\n    \"shape28\": 0.00081342,\r\n    \"shape29\": 0.00078973,\r\n    \"shape30\": 0.00076275,\r\n    \"shape31\": 0.00074395,\r\n    \"shape32\": 0.00073447,\r\n    \"shape33\": 0.00074776,\r\n    \"shape34\": 0.00077213,\r\n    \"shape35\": 0.00079939,\r\n    \"shape36\": 0.00084869,\r\n    \"shape37\": 0.00090547,\r\n    \"shape38\": 0.00096943,\r\n    \"shape39\": 0.0010476,\r\n    \"shape40\": 0.00113,\r\n    \"shape41\": 0.0012115,\r\n    \"shape42\": 0.0013001,\r\n    \"shape43\": 0.0013841,\r\n    \"shape44\": 0.001469,\r\n    \"shape45\": 0.0015291,\r\n    \"shape46\": 0.0015733,\r\n    \"shape47\": 0.0015853,\r\n    \"shape48\": 0.0015657,\r\n    \"shape49\": 0.0014989,\r\n    \"shape50\": 0.0014133,\r\n    \"shape51\": 0.0013348,\r\n    \"shape52\": 0.0012569,\r\n    \"shape53\": 0.001182,\r\n    \"shape54\": 0.00111,\r\n    \"shape55\": 0.0010406,\r\n    \"shape56\": 0.00097389,\r\n    \"shape57\": 0.00091469,\r\n    \"shape58\": 0.00086025,\r\n    \"shape59\": 0.00081792,\r\n    \"shape60\": 0.00077405,\r\n    \"shape61\": 0.00075294,\r\n    \"shape62\": 0.00074036,\r\n    \"shape63\": 0.00073703,\r\n    \"shape64\": 0.00075878,\r\n    \"texture1\": 0.058594,\r\n    \"texture2\": 0.016602,\r\n    \"texture3\": 0.017578,\r\n    \"texture4\": 0.000977,\r\n    \"texture5\": 0.030273,\r\n    \"texture6\": 0.003906,\r\n    \"texture7\": 0.035156,\r\n    \"texture8\": 0.045898,\r\n    \"texture9\": 0.017578,\r\n    \"texture10\": 0.00293,\r\n    \"texture11\": 0.001953,\r\n    \"texture12\": 0,\r\n    \"texture13\": 0.013672,\r\n    \"texture14\": 0.022461,\r\n    \"texture15\": 0,\r\n    \"texture16\": 0.005859,\r\n    \"texture17\": 0.015625,\r\n    \"texture18\": 0.001953,\r\n    \"texture19\": 0.004883,\r\n    \"texture20\": 0.027344,\r\n    \"texture21\": 0,\r\n    \"texture22\": 0.010742,\r\n    \"texture23\": 0.021484,\r\n    \"texture24\": 0,\r\n    \"texture25\": 0.004883,\r\n    \"texture26\": 0.00293,\r\n    \"texture27\": 0.005859,\r\n    \"texture28\": 0.005859,\r\n    \"texture29\": 0.041016,\r\n    \"texture30\": 0.015625,\r\n    \"texture31\": 0.045898,\r\n    \"texture32\": 0,\r\n    \"texture33\": 0,\r\n    \"texture34\": 0.087891,\r\n    \"texture35\": 0.016602,\r\n    \"texture36\": 0,\r\n    \"texture37\": 0,\r\n    \"texture38\": 0.066406,\r\n    \"texture39\": 0.008789,\r\n    \"texture40\": 0.012695,\r\n    \"texture41\": 0,\r\n    \"texture42\": 0.004883,\r\n    \"texture43\": 0.013672,\r\n    \"texture44\": 0.007812,\r\n    \"texture45\": 0.019531,\r\n    \"texture46\": 0.080078,\r\n    \"texture47\": 0.023438,\r\n    \"texture48\": 0,\r\n    \"texture49\": 0.010742,\r\n    \"texture50\": 0.032227,\r\n    \"texture51\": 0,\r\n    \"texture52\": 0.008789,\r\n    \"texture53\": 0,\r\n    \"texture54\": 0.014648,\r\n    \"texture55\": 0.008789,\r\n    \"texture56\": 0,\r\n    \"texture57\": 0.020508,\r\n    \"texture58\": 0,\r\n    \"texture59\": 0.019531,\r\n    \"texture60\": 0,\r\n    \"texture61\": 0,\r\n    \"texture62\": 0.004883,\r\n    \"texture63\": 0.013672,\r\n    \"texture64\": 0.046875\r\n  }\r\n"));
        trainSamples.add(new TrainSample("Sample 3", "{\r\n    \"id\": 401,\r\n    \"species\": \"\",\r\n    \"margin1\": 0.011719,\r\n    \"margin2\": 0.011719,\r\n    \"margin3\": 0.044922,\r\n    \"margin4\": 0.003906,\r\n    \"margin5\": 0.021484,\r\n    \"margin6\": 0.033203,\r\n    \"margin7\": 0.019531,\r\n    \"margin8\": 0.005859,\r\n    \"margin9\": 0.011719,\r\n    \"margin10\": 0.021484,\r\n    \"margin11\": 0.019531,\r\n    \"margin12\": 0.029297,\r\n    \"margin13\": 0.009766,\r\n    \"margin14\": 0.007812,\r\n    \"margin15\": 0.037109,\r\n    \"margin16\": 0,\r\n    \"margin17\": 0.007812,\r\n    \"margin18\": 0.001953,\r\n    \"margin19\": 0.005859,\r\n    \"margin20\": 0.017578,\r\n    \"margin21\": 0.033203,\r\n    \"margin22\": 0.009766,\r\n    \"margin23\": 0.007812,\r\n    \"margin24\": 0,\r\n    \"margin25\": 0.019531,\r\n    \"margin26\": 0.023438,\r\n    \"margin27\": 0.009766,\r\n    \"margin28\": 0.021484,\r\n    \"margin29\": 0.017578,\r\n    \"margin30\": 0.019531,\r\n    \"margin31\": 0.017578,\r\n    \"margin32\": 0.019531,\r\n    \"margin33\": 0.003906,\r\n    \"margin34\": 0,\r\n    \"margin35\": 0.007812,\r\n    \"margin36\": 0.011719,\r\n    \"margin37\": 0.021484,\r\n    \"margin38\": 0.054688,\r\n    \"margin39\": 0.029297,\r\n    \"margin40\": 0.017578,\r\n    \"margin41\": 0.009766,\r\n    \"margin42\": 0.009766,\r\n    \"margin43\": 0.001953,\r\n    \"margin44\": 0.023438,\r\n    \"margin45\": 0,\r\n    \"margin46\": 0.011719,\r\n    \"margin47\": 0.050781,\r\n    \"margin48\": 0.039062,\r\n    \"margin49\": 0.013672,\r\n    \"margin50\": 0.003906,\r\n    \"margin51\": 0.019531,\r\n    \"margin52\": 0.001953,\r\n    \"margin53\": 0.023438,\r\n    \"margin54\": 0.023438,\r\n    \"margin55\": 0.007812,\r\n    \"margin56\": 0.017578,\r\n    \"margin57\": 0.011719,\r\n    \"margin58\": 0.007812,\r\n    \"margin59\": 0.017578,\r\n    \"margin60\": 0,\r\n    \"margin61\": 0.003906,\r\n    \"margin62\": 0.009766,\r\n    \"margin63\": 0.009766,\r\n    \"margin64\": 0.013672,\r\n    \"shape1\": 0.0008672,\r\n    \"shape2\": 0.00092197,\r\n    \"shape3\": 0.00087727,\r\n    \"shape4\": 0.00086181,\r\n    \"shape5\": 0.00081791,\r\n    \"shape6\": 0.00083806,\r\n    \"shape7\": 0.00077073,\r\n    \"shape8\": 0.00073611,\r\n    \"shape9\": 0.00075534,\r\n    \"shape10\": 0.0007024,\r\n    \"shape11\": 0.00066977,\r\n    \"shape12\": 0.00064355,\r\n    \"shape13\": 0.0006624,\r\n    \"shape14\": 0.0006081,\r\n    \"shape15\": 0.00058335,\r\n    \"shape16\": 0.00056095,\r\n    \"shape17\": 0.00054771,\r\n    \"shape18\": 0.0005443,\r\n    \"shape19\": 0.00054423,\r\n    \"shape20\": 0.00054761,\r\n    \"shape21\": 0.00055786,\r\n    \"shape22\": 0.00057322,\r\n    \"shape23\": 0.00059362,\r\n    \"shape24\": 0.00061345,\r\n    \"shape25\": 0.00064483,\r\n    \"shape26\": 0.00068166,\r\n    \"shape27\": 0.00072645,\r\n    \"shape28\": 0.00077111,\r\n    \"shape29\": 0.00081105,\r\n    \"shape30\": 0.00084752,\r\n    \"shape31\": 0.00090974,\r\n    \"shape32\": 0.00097093,\r\n    \"shape33\": 0.0010263,\r\n    \"shape34\": 0.00096549,\r\n    \"shape35\": 0.00090796,\r\n    \"shape36\": 0.00088777,\r\n    \"shape37\": 0.00085363,\r\n    \"shape38\": 0.00080199,\r\n    \"shape39\": 0.00076692,\r\n    \"shape40\": 0.0007175,\r\n    \"shape41\": 0.000671,\r\n    \"shape42\": 0.00065768,\r\n    \"shape43\": 0.00063506,\r\n    \"shape44\": 0.0006189,\r\n    \"shape45\": 0.00061031,\r\n    \"shape46\": 0.00058908,\r\n    \"shape47\": 0.00058015,\r\n    \"shape48\": 0.00056777,\r\n    \"shape49\": 0.0005649,\r\n    \"shape50\": 0.00054496,\r\n    \"shape51\": 0.00053887,\r\n    \"shape52\": 0.00056226,\r\n    \"shape53\": 0.00059694,\r\n    \"shape54\": 0.0006163,\r\n    \"shape55\": 0.00063214,\r\n    \"shape56\": 0.00067149,\r\n    \"shape57\": 0.00072891,\r\n    \"shape58\": 0.00073806,\r\n    \"shape59\": 0.00074061,\r\n    \"shape60\": 0.00078598,\r\n    \"shape61\": 0.00079137,\r\n    \"shape62\": 0.00078904,\r\n    \"shape63\": 0.00083741,\r\n    \"shape64\": 0.00084693,\r\n    \"texture1\": 0,\r\n    \"texture2\": 0.03418,\r\n    \"texture3\": 0.036133,\r\n    \"texture4\": 0.000977,\r\n    \"texture5\": 0.051758,\r\n    \"texture6\": 0.001953,\r\n    \"texture7\": 0,\r\n    \"texture8\": 0.11914,\r\n    \"texture9\": 0.026367,\r\n    \"texture10\": 0,\r\n    \"texture11\": 0,\r\n    \"texture12\": 0.001953,\r\n    \"texture13\": 0.004883,\r\n    \"texture14\": 0.019531,\r\n    \"texture15\": 0,\r\n    \"texture16\": 0,\r\n    \"texture17\": 0.026367,\r\n    \"texture18\": 0.000977,\r\n    \"texture19\": 0,\r\n    \"texture20\": 0.044922,\r\n    \"texture21\": 0,\r\n    \"texture22\": 0.017578,\r\n    \"texture23\": 0.098633,\r\n    \"texture24\": 0.001953,\r\n    \"texture25\": 0.00293,\r\n    \"texture26\": 0.001953,\r\n    \"texture27\": 0,\r\n    \"texture28\": 0.019531,\r\n    \"texture29\": 0.064453,\r\n    \"texture30\": 0.007812,\r\n    \"texture31\": 0.045898,\r\n    \"texture32\": 0,\r\n    \"texture33\": 0,\r\n    \"texture34\": 0.00293,\r\n    \"texture35\": 0.011719,\r\n    \"texture36\": 0,\r\n    \"texture37\": 0,\r\n    \"texture38\": 0.033203,\r\n    \"texture39\": 0,\r\n    \"texture40\": 0,\r\n    \"texture41\": 0,\r\n    \"texture42\": 0.000977,\r\n    \"texture43\": 0.000977,\r\n    \"texture44\": 0.000977,\r\n    \"texture45\": 0.042969,\r\n    \"texture46\": 0.058594,\r\n    \"texture47\": 0.040039,\r\n    \"texture48\": 0.016602,\r\n    \"texture49\": 0.004883,\r\n    \"texture50\": 0.058594,\r\n    \"texture51\": 0,\r\n    \"texture52\": 0,\r\n    \"texture53\": 0.00293,\r\n    \"texture54\": 0,\r\n    \"texture55\": 0.016602,\r\n    \"texture56\": 0,\r\n    \"texture57\": 0.041016,\r\n    \"texture58\": 0,\r\n    \"texture59\": 0.021484,\r\n    \"texture60\": 0,\r\n    \"texture61\": 0,\r\n    \"texture62\": 0,\r\n    \"texture63\": 0,\r\n    \"texture64\": 0.015625\r\n  }\r\n"));
        trainSamples.add(new TrainSample("Sample 4", "{\r\n    \"id\": 880,\r\n    \"species\": \"\",\r\n    \"margin1\": 0.011719,\r\n    \"margin2\": 0.003906,\r\n    \"margin3\": 0.041016,\r\n    \"margin4\": 0.003906,\r\n    \"margin5\": 0.039062,\r\n    \"margin6\": 0.001953,\r\n    \"margin7\": 0.041016,\r\n    \"margin8\": 0,\r\n    \"margin9\": 0.005859,\r\n    \"margin10\": 0.015625,\r\n    \"margin11\": 0.001953,\r\n    \"margin12\": 0.025391,\r\n    \"margin13\": 0.021484,\r\n    \"margin14\": 0.001953,\r\n    \"margin15\": 0.037109,\r\n    \"margin16\": 0,\r\n    \"margin17\": 0.001953,\r\n    \"margin18\": 0.007812,\r\n    \"margin19\": 0.001953,\r\n    \"margin20\": 0.007812,\r\n    \"margin21\": 0.009766,\r\n    \"margin22\": 0.003906,\r\n    \"margin23\": 0,\r\n    \"margin24\": 0.009766,\r\n    \"margin25\": 0,\r\n    \"margin26\": 0.013672,\r\n    \"margin27\": 0.005859,\r\n    \"margin28\": 0.042969,\r\n    \"margin29\": 0.013672,\r\n    \"margin30\": 0.019531,\r\n    \"margin31\": 0.035156,\r\n    \"margin32\": 0.003906,\r\n    \"margin33\": 0.015625,\r\n    \"margin34\": 0.001953,\r\n    \"margin35\": 0.015625,\r\n    \"margin36\": 0.003906,\r\n    \"margin37\": 0.009766,\r\n    \"margin38\": 0.058594,\r\n    \"margin39\": 0.019531,\r\n    \"margin40\": 0.023438,\r\n    \"margin41\": 0.017578,\r\n    \"margin42\": 0.003906,\r\n    \"margin43\": 0.001953,\r\n    \"margin44\": 0.041016,\r\n    \"margin45\": 0.005859,\r\n    \"margin46\": 0.033203,\r\n    \"margin47\": 0.037109,\r\n    \"margin48\": 0.037109,\r\n    \"margin49\": 0.017578,\r\n    \"margin50\": 0.001953,\r\n    \"margin51\": 0.009766,\r\n    \"margin52\": 0,\r\n    \"margin53\": 0.03125,\r\n    \"margin54\": 0.023438,\r\n    \"margin55\": 0.072266,\r\n    \"margin56\": 0.003906,\r\n    \"margin57\": 0.005859,\r\n    \"margin58\": 0.003906,\r\n    \"margin59\": 0.017578,\r\n    \"margin60\": 0.013672,\r\n    \"margin61\": 0,\r\n    \"margin62\": 0.005859,\r\n    \"margin63\": 0.03125,\r\n    \"margin64\": 0.005859,\r\n    \"shape1\": 0.0012469,\r\n    \"shape2\": 0.001169,\r\n    \"shape3\": 0.0011231,\r\n    \"shape4\": 0.0010733,\r\n    \"shape5\": 0.0010108,\r\n    \"shape6\": 0.00094378,\r\n    \"shape7\": 0.00086349,\r\n    \"shape8\": 0.00077377,\r\n    \"shape9\": 0.0006939,\r\n    \"shape10\": 0.00061119,\r\n    \"shape11\": 0.00054211,\r\n    \"shape12\": 0.00047256,\r\n    \"shape13\": 0.00041328,\r\n    \"shape14\": 0.0003738,\r\n    \"shape15\": 0.0003437,\r\n    \"shape16\": 0.00033033,\r\n    \"shape17\": 0.00035541,\r\n    \"shape18\": 0.00039535,\r\n    \"shape19\": 0.00044582,\r\n    \"shape20\": 0.00050367,\r\n    \"shape21\": 0.00056673,\r\n    \"shape22\": 0.00063656,\r\n    \"shape23\": 0.00071123,\r\n    \"shape24\": 0.00077593,\r\n    \"shape25\": 0.00084619,\r\n    \"shape26\": 0.00092104,\r\n    \"shape27\": 0.00099803,\r\n    \"shape28\": 0.0010692,\r\n    \"shape29\": 0.0011365,\r\n    \"shape30\": 0.0012015,\r\n    \"shape31\": 0.0012613,\r\n    \"shape32\": 0.0013164,\r\n    \"shape33\": 0.0012915,\r\n    \"shape34\": 0.001212,\r\n    \"shape35\": 0.0011474,\r\n    \"shape36\": 0.001077,\r\n    \"shape37\": 0.0010035,\r\n    \"shape38\": 0.00094192,\r\n    \"shape39\": 0.0008708,\r\n    \"shape40\": 0.00080142,\r\n    \"shape41\": 0.00072659,\r\n    \"shape42\": 0.00065235,\r\n    \"shape43\": 0.00057316,\r\n    \"shape44\": 0.00049429,\r\n    \"shape45\": 0.00044063,\r\n    \"shape46\": 0.00040334,\r\n    \"shape47\": 0.00037888,\r\n    \"shape48\": 0.00036527,\r\n    \"shape49\": 0.00037147,\r\n    \"shape50\": 0.00039366,\r\n    \"shape51\": 0.0004296,\r\n    \"shape52\": 0.00047068,\r\n    \"shape53\": 0.00052002,\r\n    \"shape54\": 0.00059234,\r\n    \"shape55\": 0.00067312,\r\n    \"shape56\": 0.00075387,\r\n    \"shape57\": 0.00083459,\r\n    \"shape58\": 0.00090915,\r\n    \"shape59\": 0.00098785,\r\n    \"shape60\": 0.0010643,\r\n    \"shape61\": 0.0011449,\r\n    \"shape62\": 0.0012218,\r\n    \"shape63\": 0.0013017,\r\n    \"shape64\": 0.0012895,\r\n    \"texture1\": 0.015625,\r\n    \"texture2\": 0.006836,\r\n    \"texture3\": 0.011719,\r\n    \"texture4\": 0.016602,\r\n    \"texture5\": 0.000977,\r\n    \"texture6\": 0,\r\n    \"texture7\": 0.010742,\r\n    \"texture8\": 0.013672,\r\n    \"texture9\": 0.021484,\r\n    \"texture10\": 0.035156,\r\n    \"texture11\": 0.035156,\r\n    \"texture12\": 0,\r\n    \"texture13\": 0.00293,\r\n    \"texture14\": 0.038086,\r\n    \"texture15\": 0,\r\n    \"texture16\": 0,\r\n    \"texture17\": 0.001953,\r\n    \"texture18\": 0,\r\n    \"texture19\": 0.008789,\r\n    \"texture20\": 0.033203,\r\n    \"texture21\": 0,\r\n    \"texture22\": 0.12402,\r\n    \"texture23\": 0.001953,\r\n    \"texture24\": 0,\r\n    \"texture25\": 0.014648,\r\n    \"texture26\": 0,\r\n    \"texture27\": 0.004883,\r\n    \"texture28\": 0.004883,\r\n    \"texture29\": 0.030273,\r\n    \"texture30\": 0.013672,\r\n    \"texture31\": 0.020508,\r\n    \"texture32\": 0.023438,\r\n    \"texture33\": 0.000977,\r\n    \"texture34\": 0.006836,\r\n    \"texture35\": 0.011719,\r\n    \"texture36\": 0,\r\n    \"texture37\": 0,\r\n    \"texture38\": 0.011719,\r\n    \"texture39\": 0.038086,\r\n    \"texture40\": 0.010742,\r\n    \"texture41\": 0.005859,\r\n    \"texture42\": 0.000977,\r\n    \"texture43\": 0.019531,\r\n    \"texture44\": 0.011719,\r\n    \"texture45\": 0.00293,\r\n    \"texture46\": 0.024414,\r\n    \"texture47\": 0.054688,\r\n    \"texture48\": 0,\r\n    \"texture49\": 0.018555,\r\n    \"texture50\": 0.042969,\r\n    \"texture51\": 0.045898,\r\n    \"texture52\": 0.021484,\r\n    \"texture53\": 0.006836,\r\n    \"texture54\": 0.016602,\r\n    \"texture55\": 0,\r\n    \"texture56\": 0.012695,\r\n    \"texture57\": 0.004883,\r\n    \"texture58\": 0.003906,\r\n    \"texture59\": 0.055664,\r\n    \"texture60\": 0,\r\n    \"texture61\": 0,\r\n    \"texture62\": 0.001953,\r\n    \"texture63\": 0.000977,\r\n    \"texture64\": 0.076172\r\n  }\r\n"));
        trainSamples.add(new TrainSample("Sample 5","{\r\n    \"id\": 1259,\r\n    \"species\": \"\",\r\n    \"margin1\": 0.013672,\r\n    \"margin2\": 0.007812,\r\n    \"margin3\": 0.017578,\r\n    \"margin4\": 0.03125,\r\n    \"margin5\": 0.009766,\r\n    \"margin6\": 0.005859,\r\n    \"margin7\": 0.021484,\r\n    \"margin8\": 0,\r\n    \"margin9\": 0.005859,\r\n    \"margin10\": 0.005859,\r\n    \"margin11\": 0.025391,\r\n    \"margin12\": 0.011719,\r\n    \"margin13\": 0.019531,\r\n    \"margin14\": 0,\r\n    \"margin15\": 0.019531,\r\n    \"margin16\": 0,\r\n    \"margin17\": 0.042969,\r\n    \"margin18\": 0.03125,\r\n    \"margin19\": 0.021484,\r\n    \"margin20\": 0.015625,\r\n    \"margin21\": 0.011719,\r\n    \"margin22\": 0.003906,\r\n    \"margin23\": 0,\r\n    \"margin24\": 0.025391,\r\n    \"margin25\": 0.001953,\r\n    \"margin26\": 0.041016,\r\n    \"margin27\": 0.001953,\r\n    \"margin28\": 0.013672,\r\n    \"margin29\": 0.013672,\r\n    \"margin30\": 0.007812,\r\n    \"margin31\": 0.003906,\r\n    \"margin32\": 0,\r\n    \"margin33\": 0.015625,\r\n    \"margin34\": 0,\r\n    \"margin35\": 0.015625,\r\n    \"margin36\": 0.029297,\r\n    \"margin37\": 0.013672,\r\n    \"margin38\": 0.025391,\r\n    \"margin39\": 0.015625,\r\n    \"margin40\": 0.001953,\r\n    \"margin41\": 0.001953,\r\n    \"margin42\": 0.025391,\r\n    \"margin43\": 0.021484,\r\n    \"margin44\": 0.003906,\r\n    \"margin45\": 0.046875,\r\n    \"margin46\": 0.007812,\r\n    \"margin47\": 0.039062,\r\n    \"margin48\": 0.050781,\r\n    \"margin49\": 0,\r\n    \"margin50\": 0.019531,\r\n    \"margin51\": 0.021484,\r\n    \"margin52\": 0,\r\n    \"margin53\": 0.058594,\r\n    \"margin54\": 0.001953,\r\n    \"margin55\": 0.03125,\r\n    \"margin56\": 0,\r\n    \"margin57\": 0.019531,\r\n    \"margin58\": 0.021484,\r\n    \"margin59\": 0.039062,\r\n    \"margin60\": 0.021484,\r\n    \"margin61\": 0,\r\n    \"margin62\": 0,\r\n    \"margin63\": 0.017578,\r\n    \"margin64\": 0.001953,\r\n    \"shape1\": 0.00083496,\r\n    \"shape2\": 0.00082182,\r\n    \"shape3\": 0.00082384,\r\n    \"shape4\": 0.00084092,\r\n    \"shape5\": 0.00086441,\r\n    \"shape6\": 0.0009011,\r\n    \"shape7\": 0.00094228,\r\n    \"shape8\": 0.00098738,\r\n    \"shape9\": 0.0010361,\r\n    \"shape10\": 0.0010882,\r\n    \"shape11\": 0.0011385,\r\n    \"shape12\": 0.0011887,\r\n    \"shape13\": 0.0012329,\r\n    \"shape14\": 0.0012597,\r\n    \"shape15\": 0.0012711,\r\n    \"shape16\": 0.001279,\r\n    \"shape17\": 0.001283,\r\n    \"shape18\": 0.0013679,\r\n    \"shape19\": 0.0014775,\r\n    \"shape20\": 0.0015384,\r\n    \"shape21\": 0.0014754,\r\n    \"shape22\": 0.001365,\r\n    \"shape23\": 0.0012747,\r\n    \"shape24\": 0.0012509,\r\n    \"shape25\": 0.0012456,\r\n    \"shape26\": 0.0012361,\r\n    \"shape27\": 0.0012103,\r\n    \"shape28\": 0.0011644,\r\n    \"shape29\": 0.0011096,\r\n    \"shape30\": 0.0010551,\r\n    \"shape31\": 0.0010053,\r\n    \"shape32\": 0.00096002,\r\n    \"shape33\": 0.00092291,\r\n    \"shape34\": 0.00088627,\r\n    \"shape35\": 0.00085479,\r\n    \"shape36\": 0.00083692,\r\n    \"shape37\": 0.00083355,\r\n    \"shape38\": 0.00084485,\r\n    \"shape39\": 0.0008604,\r\n    \"shape40\": 0.00089089,\r\n    \"shape41\": 0.0009337,\r\n    \"shape42\": 0.00097443,\r\n    \"shape43\": 0.0010261,\r\n    \"shape44\": 0.0010766,\r\n    \"shape45\": 0.0011275,\r\n    \"shape46\": 0.0011704,\r\n    \"shape47\": 0.0012264,\r\n    \"shape48\": 0.0012941,\r\n    \"shape49\": 0.0013691,\r\n    \"shape50\": 0.0014651,\r\n    \"shape51\": 0.0015225,\r\n    \"shape52\": 0.0014577,\r\n    \"shape53\": 0.001401,\r\n    \"shape54\": 0.0013533,\r\n    \"shape55\": 0.0013156,\r\n    \"shape56\": 0.0012887,\r\n    \"shape57\": 0.0012441,\r\n    \"shape58\": 0.0011821,\r\n    \"shape59\": 0.0011207,\r\n    \"shape60\": 0.0010558,\r\n    \"shape61\": 0.000995,\r\n    \"shape62\": 0.00093825,\r\n    \"shape63\": 0.00089238,\r\n    \"shape64\": 0.00085199,\r\n    \"texture1\": 0.075195,\r\n    \"texture2\": 0.003906,\r\n    \"texture3\": 0.007812,\r\n    \"texture4\": 0.027344,\r\n    \"texture5\": 0.000977,\r\n    \"texture6\": 0,\r\n    \"texture7\": 0.032227,\r\n    \"texture8\": 0.005859,\r\n    \"texture9\": 0.032227,\r\n    \"texture10\": 0.020508,\r\n    \"texture11\": 0.004883,\r\n    \"texture12\": 0,\r\n    \"texture13\": 0,\r\n    \"texture14\": 0.012695,\r\n    \"texture15\": 0,\r\n    \"texture16\": 0,\r\n    \"texture17\": 0.007812,\r\n    \"texture18\": 0,\r\n    \"texture19\": 0.032227,\r\n    \"texture20\": 0.010742,\r\n    \"texture21\": 0,\r\n    \"texture22\": 0.009766,\r\n    \"texture23\": 0.004883,\r\n    \"texture24\": 0.006836,\r\n    \"texture25\": 0.004883,\r\n    \"texture26\": 0.033203,\r\n    \"texture27\": 0.038086,\r\n    \"texture28\": 0.000977,\r\n    \"texture29\": 0.022461,\r\n    \"texture30\": 0.005859,\r\n    \"texture31\": 0.058594,\r\n    \"texture32\": 0,\r\n    \"texture33\": 0.001953,\r\n    \"texture34\": 0.006836,\r\n    \"texture35\": 0.014648,\r\n    \"texture36\": 0,\r\n    \"texture37\": 0.011719,\r\n    \"texture38\": 0.027344,\r\n    \"texture39\": 0.083984,\r\n    \"texture40\": 0.020508,\r\n    \"texture41\": 0.005859,\r\n    \"texture42\": 0,\r\n    \"texture43\": 0.038086,\r\n    \"texture44\": 0.03125,\r\n    \"texture45\": 0.000977,\r\n    \"texture46\": 0.009766,\r\n    \"texture47\": 0.030273,\r\n    \"texture48\": 0,\r\n    \"texture49\": 0.003906,\r\n    \"texture50\": 0.036133,\r\n    \"texture51\": 0,\r\n    \"texture52\": 0,\r\n    \"texture53\": 0.015625,\r\n    \"texture54\": 0.051758,\r\n    \"texture55\": 0,\r\n    \"texture56\": 0,\r\n    \"texture57\": 0.007812,\r\n    \"texture58\": 0.010742,\r\n    \"texture59\": 0.035156,\r\n    \"texture60\": 0.001953,\r\n    \"texture61\": 0,\r\n    \"texture62\": 0.02832,\r\n    \"texture63\": 0,\r\n    \"texture64\": 0.06543\r\n  }\r\n"));
        trainSamples.add(new TrainSample("Sample 6","{\r\n    \"id\": 1487,\r\n    \"species\": \"\",\r\n    \"margin1\": 0.007812,\r\n    \"margin2\": 0.007812,\r\n    \"margin3\": 0.041016,\r\n    \"margin4\": 0.015625,\r\n    \"margin5\": 0.005859,\r\n    \"margin6\": 0.017578,\r\n    \"margin7\": 0.025391,\r\n    \"margin8\": 0,\r\n    \"margin9\": 0.011719,\r\n    \"margin10\": 0.029297,\r\n    \"margin11\": 0.027344,\r\n    \"margin12\": 0.001953,\r\n    \"margin13\": 0.041016,\r\n    \"margin14\": 0.001953,\r\n    \"margin15\": 0.005859,\r\n    \"margin16\": 0,\r\n    \"margin17\": 0.023438,\r\n    \"margin18\": 0.042969,\r\n    \"margin19\": 0.042969,\r\n    \"margin20\": 0.011719,\r\n    \"margin21\": 0.005859,\r\n    \"margin22\": 0,\r\n    \"margin23\": 0,\r\n    \"margin24\": 0,\r\n    \"margin25\": 0.001953,\r\n    \"margin26\": 0.021484,\r\n    \"margin27\": 0,\r\n    \"margin28\": 0.015625,\r\n    \"margin29\": 0.058594,\r\n    \"margin30\": 0.015625,\r\n    \"margin31\": 0.001953,\r\n    \"margin32\": 0,\r\n    \"margin33\": 0.025391,\r\n    \"margin34\": 0,\r\n    \"margin35\": 0.007812,\r\n    \"margin36\": 0.029297,\r\n    \"margin37\": 0.021484,\r\n    \"margin38\": 0.015625,\r\n    \"margin39\": 0.009766,\r\n    \"margin40\": 0.001953,\r\n    \"margin41\": 0.011719,\r\n    \"margin42\": 0.044922,\r\n    \"margin43\": 0.001953,\r\n    \"margin44\": 0.007812,\r\n    \"margin45\": 0.042969,\r\n    \"margin46\": 0,\r\n    \"margin47\": 0.015625,\r\n    \"margin48\": 0,\r\n    \"margin49\": 0,\r\n    \"margin50\": 0,\r\n    \"margin51\": 0.039062,\r\n    \"margin52\": 0,\r\n    \"margin53\": 0.027344,\r\n    \"margin54\": 0.001953,\r\n    \"margin55\": 0.019531,\r\n    \"margin56\": 0,\r\n    \"margin57\": 0.015625,\r\n    \"margin58\": 0.035156,\r\n    \"margin59\": 0.068359,\r\n    \"margin60\": 0.048828,\r\n    \"margin61\": 0,\r\n    \"margin62\": 0,\r\n    \"margin63\": 0.025391,\r\n    \"margin64\": 0,\r\n    \"shape1\": 0.00055917,\r\n    \"shape2\": 0.00053992,\r\n    \"shape3\": 0.00051784,\r\n    \"shape4\": 0.00048242,\r\n    \"shape5\": 0.00047484,\r\n    \"shape6\": 0.00048329,\r\n    \"shape7\": 0.00049849,\r\n    \"shape8\": 0.00052262,\r\n    \"shape9\": 0.00056093,\r\n    \"shape10\": 0.00061339,\r\n    \"shape11\": 0.00066706,\r\n    \"shape12\": 0.00071924,\r\n    \"shape13\": 0.00076485,\r\n    \"shape14\": 0.00082718,\r\n    \"shape15\": 0.00087934,\r\n    \"shape16\": 0.00088901,\r\n    \"shape17\": 0.00091227,\r\n    \"shape18\": 0.00096762,\r\n    \"shape19\": 0.0010186,\r\n    \"shape20\": 0.00096448,\r\n    \"shape21\": 0.00090055,\r\n    \"shape22\": 0.0008783,\r\n    \"shape23\": 0.00084615,\r\n    \"shape24\": 0.00079518,\r\n    \"shape25\": 0.00074053,\r\n    \"shape26\": 0.00068829,\r\n    \"shape27\": 0.00063553,\r\n    \"shape28\": 0.00059459,\r\n    \"shape29\": 0.00055071,\r\n    \"shape30\": 0.00051744,\r\n    \"shape31\": 0.000481,\r\n    \"shape32\": 0.00046652,\r\n    \"shape33\": 0.00045294,\r\n    \"shape34\": 0.00046561,\r\n    \"shape35\": 0.00048254,\r\n    \"shape36\": 0.00050573,\r\n    \"shape37\": 0.00053817,\r\n    \"shape38\": 0.00055931,\r\n    \"shape39\": 0.00057693,\r\n    \"shape40\": 0.00055119,\r\n    \"shape41\": 0.00061119,\r\n    \"shape42\": 0.00065202,\r\n    \"shape43\": 0.00069078,\r\n    \"shape44\": 0.00071922,\r\n    \"shape45\": 0.00069725,\r\n    \"shape46\": 0.00070957,\r\n    \"shape47\": 0.00074181,\r\n    \"shape48\": 0.00072504,\r\n    \"shape49\": 0.00076846,\r\n    \"shape50\": 0.00079851,\r\n    \"shape51\": 0.0007915,\r\n    \"shape52\": 0.00081743,\r\n    \"shape53\": 0.00078058,\r\n    \"shape54\": 0.00078557,\r\n    \"shape55\": 0.0007535,\r\n    \"shape56\": 0.00071342,\r\n    \"shape57\": 0.00071535,\r\n    \"shape58\": 0.00071941,\r\n    \"shape59\": 0.00068973,\r\n    \"shape60\": 0.00066478,\r\n    \"shape61\": 0.0006265,\r\n    \"shape62\": 0.00058297,\r\n    \"shape63\": 0.00057517,\r\n    \"shape64\": 0.00056592,\r\n    \"texture1\": 0,\r\n    \"texture2\": 0.003906,\r\n    \"texture3\": 0,\r\n    \"texture4\": 0.004883,\r\n    \"texture5\": 0,\r\n    \"texture6\": 0,\r\n    \"texture7\": 0,\r\n    \"texture8\": 0,\r\n    \"texture9\": 0.001953,\r\n    \"texture10\": 0,\r\n    \"texture11\": 0.057617,\r\n    \"texture12\": 0,\r\n    \"texture13\": 0.006836,\r\n    \"texture14\": 0.003906,\r\n    \"texture15\": 0.004883,\r\n    \"texture16\": 0.013672,\r\n    \"texture17\": 0.00293,\r\n    \"texture18\": 0,\r\n    \"texture19\": 0.021484,\r\n    \"texture20\": 0.000977,\r\n    \"texture21\": 0.016602,\r\n    \"texture22\": 0.027344,\r\n    \"texture23\": 0.016602,\r\n    \"texture24\": 0.02832,\r\n    \"texture25\": 0,\r\n    \"texture26\": 0.041992,\r\n    \"texture27\": 0,\r\n    \"texture28\": 0,\r\n    \"texture29\": 0.020508,\r\n    \"texture30\": 0.003906,\r\n    \"texture31\": 0.052734,\r\n    \"texture32\": 0.078125,\r\n    \"texture33\": 0,\r\n    \"texture34\": 0,\r\n    \"texture35\": 0.000977,\r\n    \"texture36\": 0.046875,\r\n    \"texture37\": 0.000977,\r\n    \"texture38\": 0,\r\n    \"texture39\": 0,\r\n    \"texture40\": 0.000977,\r\n    \"texture41\": 0,\r\n    \"texture42\": 0.015625,\r\n    \"texture43\": 0,\r\n    \"texture44\": 0,\r\n    \"texture45\": 0.016602,\r\n    \"texture46\": 0,\r\n    \"texture47\": 0.003906,\r\n    \"texture48\": 0,\r\n    \"texture49\": 0.026367,\r\n    \"texture50\": 0.008789,\r\n    \"texture51\": 0.10254,\r\n    \"texture52\": 0.016602,\r\n    \"texture53\": 0.007812,\r\n    \"texture54\": 0.081055,\r\n    \"texture55\": 0,\r\n    \"texture56\": 0.1582,\r\n    \"texture57\": 0,\r\n    \"texture58\": 0.020508,\r\n    \"texture59\": 0.010742,\r\n    \"texture60\": 0,\r\n    \"texture61\": 0.009766,\r\n    \"texture62\": 0.056641,\r\n    \"texture63\": 0.005859,\r\n    \"texture64\": 0\r\n  }\r\n "));

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



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        selectedSample = trainSamples.get(position);
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
        }else if(scoredLabels.equals("Phildelphus")) {
            return output.getScoredProbabilitiesForClassPhildelphus();
        }else if(scoredLabels.equals("Populus_Adenopoda")) {
            return output.getScoredProbabilitiesForClassPopulusAdenopoda();
        }else if(scoredLabels.equals("Populus_Grandidentata")) {
            return output.getScoredProbabilitiesForClassPopulusGrandidentata();
        }else if(scoredLabels.equals("Populus_Nigra")) {
            return output.getScoredProbabilitiesForClassPopulusNigra();
        }else if(scoredLabels.equals("Prunus_Avium")) {
            return output.getScoredProbabilitiesForClassPrunusAvium();
        }else if(scoredLabels.equals("Prunus_X_Shmittii")) {
            return output.getScoredProbabilitiesForClassPrunusXShmittii();
        }else if(scoredLabels.equals("Pterocarya_Stenoptera")) {
            return output.getScoredProbabilitiesForClassPterocaryaStenoptera();
        }else if(scoredLabels.equals("Quercus_Afares")) {
            return output.getScoredProbabilitiesForClassQuercusAfares();
        }else if(scoredLabels.equals("Quercus_Agrifolia")) {
            return output.getScoredProbabilitiesForClassQuercusAgrifolia();
        }else if(scoredLabels.equals("Quercus_Alnifolia")) {
            return output.getScoredProbabilitiesForClassQuercusAlnifolia();
        }else if(scoredLabels.equals("Quercus_Brantii")) {
            return output.getScoredProbabilitiesForClassQuercusBrantii();
        }else if(scoredLabels.equals("Quercus_Canariensis")) {
            return output.getScoredProbabilitiesForClassQuercusCanariensis();
        }else if(scoredLabels.equals("Quercus_Castaneifolia")) {
            return output.getScoredProbabilitiesForClassQuercusCastaneifolia();
        }else if(scoredLabels.equals("Quercus_Cerris")) {
            return output.getScoredProbabilitiesForClassQuercusCerris();
        }else if(scoredLabels.equals("Quercus_Chrysolepis")) {
            return output.getScoredProbabilitiesForClassQuercusChrysolepis();
        }else if(scoredLabels.equals("Quercus_Coccifera")) {
            return output.getScoredProbabilitiesForClassQuercusCoccifera();
        }else if(scoredLabels.equals("Quercus_Coccinea")) {
            return output.getScoredProbabilitiesForClassQuercusCoccinea();
        }else if(scoredLabels.equals("Quercus_Crassifolia")) {
            return output.getScoredProbabilitiesForClassQuercusCrassifolia();
        }else if(scoredLabels.equals("Quercus_Crassipes")) {
            return output.getScoredProbabilitiesForClassQuercusCrassipes();
        }else if(scoredLabels.equals("Quercus_Dolicholepis")) {
            return output.getScoredProbabilitiesForClassQuercusDolicholepis();
        }else if(scoredLabels.equals("Quercus_Ellipsoidalis")) {
            return output.getScoredProbabilitiesForClassQuercusEllipsoidalis();
        }else if(scoredLabels.equals("Quercus_Greggii")) {
            return output.getScoredProbabilitiesForClassQuercusGreggii();
        }else if(scoredLabels.equals("Quercus_Hartwissiana")) {
            return output.getScoredProbabilitiesForClassQuercusHartwissiana();
        }else if(scoredLabels.equals("Quercus_Ilex")) {
            return output.getScoredProbabilitiesForClassQuercusIlex();
        }else if(scoredLabels.equals("Quercus_Imbricaria")) {
            return output.getScoredProbabilitiesForClassQuercusImbricaria();
        }else if(scoredLabels.equals("Quercus_Infectoria_Sub")) {
            return output.getScoredProbabilitiesForClassQuercusInfectoriaSub();
        }else if(scoredLabels.equals("Quercus_Kewensis")) {
            return output.getScoredProbabilitiesForClassQuercusKewensis();
        }else if(scoredLabels.equals("Quercus_Nigra")) {
            return output.getScoredProbabilitiesForClassQuercusNigra();
        }else if(scoredLabels.equals("Quercus_Palustris")) {
            return output.getScoredProbabilitiesForClassQuercusPalustris();
        }else if(scoredLabels.equals("Quercus_Phellos")) {
            return output.getScoredProbabilitiesForClassQuercusPhellos();
        }else if(scoredLabels.equals("Quercus_Phillyraeoides")) {
            return output.getScoredProbabilitiesForClassQuercusPhillyraeoides();
        }else if(scoredLabels.equals("Quercus_Pontica")) {
            return output.getScoredProbabilitiesForClassQuercusPontica();
        }else if(scoredLabels.equals("uercus_Pubescens")) {
            return output.getScoredProbabilitiesForClassQuercusPubescens();
        }else if(scoredLabels.equals("Quercus_Pyrenaica")) {
            return output.getScoredProbabilitiesForClassQuercusPyrenaica();
        }else if(scoredLabels.equals("Quercus_Rhysophylla")) {
            return output.getScoredProbabilitiesForClassQuercusRhysophylla();
        }else if(scoredLabels.equals("Quercus_Rubra")) {
            return output.getScoredProbabilitiesForClassQuercusRubra();
        }else if(scoredLabels.equals("Quercus_Semecarpifolia")) {
            return output.getScoredProbabilitiesForClassQuercusSemecarpifolia();
        }else if(scoredLabels.equals("Quercus_Shumardii")) {
            return output.getScoredProbabilitiesForClassQuercusShumardii();
        }else if(scoredLabels.equals("Quercus_Suber")) {
            return output.getScoredProbabilitiesForClassQuercusSuber();
        }else if(scoredLabels.equals("Quercus_Texana")) {
            return output.getScoredProbabilitiesForClassQuercusTexana();
        }else if(scoredLabels.equals("Quercus_Trojana")) {
            return output.getScoredProbabilitiesForClassQuercusTrojana();
        }else if(scoredLabels.equals("Quercus_Variabilis")) {
            return output.getScoredProbabilitiesForClassQuercusVariabilis();
        }else if(scoredLabels.equals("Quercus_Vulcanica")) {
            return output.getScoredProbabilitiesForClassQuercusVulcanica();
        }else if(scoredLabels.equals("Quercus_x_Hispanica")) {
            return output.getScoredProbabilitiesForClassQuercusXHispanica();
        }else if(scoredLabels.equals("Quercus_x_Turneri")) {
            return output.getScoredProbabilitiesForClassQuercusXTurneri();
        }else if(scoredLabels.equals("Rhododendron_x_Russellianum")) {
            return output.getScoredProbabilitiesForClassRhododendronXRussellianum();
        }else if(scoredLabels.equals("Salix_Fragilis")) {
            return output.getScoredProbabilitiesForClassSalixFragilis();
        }else if(scoredLabels.equals("Salix_Intergra")) {
            return output.getScoredProbabilitiesForClassSalixIntergra();
        }else if(scoredLabels.equals("Sorbus_Aria")) {
            return output.getScoredProbabilitiesForClassSorbusAria();
        }else if(scoredLabels.equals("Tilia_Oliveri")) {
            return output.getScoredProbabilitiesForClassTiliaOliveri();
        }else if(scoredLabels.equals("Tilia_Platyphyllos")) {
            return output.getScoredProbabilitiesForClassTiliaPlatyphyllos();
        }else if(scoredLabels.equals("Tilia_Tomentosa")) {
            return output.getScoredProbabilitiesForClassTiliaTomentosa();
        }else if(scoredLabels.equals("Ulmus_Bergmanniana")) {
            return output.getScoredProbabilitiesForClassUlmusBergmanniana();
        }else if(scoredLabels.equals("Viburnum_Tinus")) {
            return output.getScoredProbabilitiesForClassViburnumTinus();
        }else if(scoredLabels.equals("Viburnum_x_Rhytidophylloides")) {
            return output.getScoredProbabilitiesForClassViburnumXRhytidophylloides();
        }else if(scoredLabels.equals("Zelkova_Serrata")) {
            return output.getScoredProbabilitiesForClassZelkovaSerrata();
        }
        return "0.0";
    }
}