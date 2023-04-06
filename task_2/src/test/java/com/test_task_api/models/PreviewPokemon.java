package com.test_task_api.models;

import java.util.Arrays;
import com.google.gson.annotations.SerializedName;

public class PreviewPokemon {

    @SerializedName("name")
    public String name;

    @SerializedName("url")
    public String url;

    @Override
    public String toString() {
        return Arrays.asList(name, url).toString();
    }

}
