package org.sfsi.termina.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Smule on 3/5/17.
 */

@JsonObject
public class CopyResponse {
    @JsonField
    public OptionDetails medication;

    @JsonField
    public OptionDetails surgical;

    @JsonField
    public OptionDetails surgical_travel;

    @JsonField
    public OptionDetails later_care;

    @JsonField
    public OptionDetails parenthood;

    @JsonField
    public OptionDetails adoption;

}
