package org.sfsi.termina;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Smule on 3/4/17.
 */

@JsonObject
public class Option {
    @JsonField
    public String type;

    @JsonField
    public int min;

    @JsonField
    public int max;

    @JsonField
    public boolean available;
}
