package com.test_task_api.models;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.List;

public class LimitedPokemonList {

  @SerializedName("count")
  public int count;

  @SerializedName("results")
  public List<PreviewPokemon> limitedPokemonList;

  @Override
  public String toString() {
    return Arrays.asList(count, limitedPokemonList.toString()).toString();
  }
}
