package com.test_task_api.models;

import com.google.gson.annotations.SerializedName;

public class Ability {

  @SerializedName("name")
  public String name;

  public Ability(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
