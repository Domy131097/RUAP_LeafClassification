package hr.unios.ferit.leafclassification.Model;

import com.google.gson.annotations.SerializedName;

public class Output {
    @SerializedName("Scored Labels")
    public String ScoredLabels;
    @SerializedName("Scored Probabilities")
    public String ScoredProbabilities;
}
