package org.sfsi.termina.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Smule on 3/4/17.
 */

@JsonObject
public class AgeWarning {
    @JsonField
    public String type;

    @JsonField
    public String display_text;
}
