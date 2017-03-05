package org.sfsi.termina;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smule on 3/4/17.
 */

@JsonObject
public class OptionsResponse {
    @JsonField
    ArrayList<Option> options;

    @JsonField
    AgeWarning age_warning;

}
