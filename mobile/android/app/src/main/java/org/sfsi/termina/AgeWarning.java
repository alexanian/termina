package org.sfsi.termina;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Smule on 3/4/17.
 */

@JsonObject
public class AgeWarning {
    @JsonField
    String type;

    @JsonField
    String display_text;
}
