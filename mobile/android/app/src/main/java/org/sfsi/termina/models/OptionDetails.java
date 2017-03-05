package org.sfsi.termina.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Smule on 3/5/17.
 */

@JsonObject
public class OptionDetails {
    @JsonField
    public String type;

    @JsonField
    public String description;

    @JsonField
    public String cost;

    @JsonField
    public String common;

    @JsonField
    public String info_link;
}
