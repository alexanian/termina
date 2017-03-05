package org.sfsi.termina.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

/**
 * Created by Smule on 3/4/17.
 */

@JsonObject
public class OptionsResponse {
    @JsonField
    public ArrayList<Option> options;

    @JsonField
    public AgeWarning age_warning;

}
