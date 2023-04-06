package com.test_task_api.api;

import com.google.gson.Gson;
import com.test_task_api.models.LimitedPokemonList;
import com.test_task_api.models.Pokemon;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class ApiClient {
    final static Gson gson = new Gson();
    public static String BASE_URL = "https://pokeapi.co/api/v2/";

    public ApiClient() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Get pokemon by name.")
    public Pokemon getPokemon(String name) {
        try {

            final var responseJson = RestAssured.given()
                    .filter(new AllureRestAssured())
                    .pathParam("name", name)
                    .get("pokemon/{name}").getBody().asString();

            final var pokemon = gson.fromJson(responseJson, Pokemon.class);
            return pokemon;
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
            // Maybe it doesn't need to handle...
            throw e;
        }

    }

    @Step("Get limited pokemon list.")
    public LimitedPokemonList getLimitedPokemonList(int limit) {
        try {

            final var responseJson = RestAssured.given()
                    .filter(new AllureRestAssured())
                    .request().queryParam("limit", limit)
                    .get("pokemon").getBody().asString();

            final var limitedPokemonList = gson.fromJson(responseJson, LimitedPokemonList.class);

            return limitedPokemonList;

        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
            // Maybe it doesn't need to handle...
            throw e;
        }

    }

}
