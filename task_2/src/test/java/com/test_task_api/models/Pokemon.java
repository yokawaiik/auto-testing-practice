package com.test_task_api.models;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class Pokemon {

    @SerializedName("name")
    public String name;

    @SerializedName("weight")
    public Double weight;

    // ? info: it needs to parse: abilities[i].[ability][name]
    static class AbilityDeserializer implements JsonDeserializer<List<Ability>> {
        @Override
        public List<Ability> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            final var abilitiesRaw = json.getAsJsonArray();

            List<Ability> abilitiesList = new ArrayList<Ability>();
            for (int index = 0; index < abilitiesRaw.size(); index++) {
                final var abilityRaw = abilitiesRaw.get(index).getAsJsonObject();
                final var name = abilityRaw.get("ability").getAsJsonObject().get("name").getAsString();
                final var ability = new Ability(name);
                abilitiesList.add(ability);
            }

            return abilitiesList;
        }
    }

    @SerializedName("abilities")
    @JsonAdapter(AbilityDeserializer.class)
    public List<Ability> abilities;

    @Override
    public String toString() {
        return Arrays.asList(name, weight, abilities.toString()).toString();
    }

}
