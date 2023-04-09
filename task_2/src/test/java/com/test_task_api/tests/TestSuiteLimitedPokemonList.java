package com.test_task_api.tests;

import com.test_task_api.configuration.JUnitTestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
@Epic("Test Suite: Check pokemon list limit")
public class TestSuiteLimitedPokemonList extends JUnitTestBase {

  @Test
  @Tag("#TC002")
  @Description(
    "Test Case #TC002: The number of Pokémon in the request is equal to the number of Pokémon in the response."
  )
  public void limitOfThePokemonShortList() {
    final var queryLimit = 100;

    final var limitedPokemonList = getApiClient()
      .getLimitedPokemonList(queryLimit);

    Asserts.check(
      limitedPokemonList.limitedPokemonList.size() == queryLimit,
      "Count in the request must be equal in the response."
    );
  }

  @Test
  @Tag("#TC003")
  @Description("Test Case #TC003: Request over the limit Pokemons.")
  public void overTheLimitOfThePokemonShortList() {
    final var queryLimit = 10000;

    final var limitedPokemonList = getApiClient()
      .getLimitedPokemonList(queryLimit);

    Asserts.check(
      limitedPokemonList.limitedPokemonList.size() != queryLimit,
      "The number in the answer is limited by the maximum number of Pokémons."
    );
  }

  @Test
  @Tag("#TC004")
  @Description("Test Case #TC004: Request minimum the limit Pokemons.")
  public void minimumTheLimitOfThePokemonShortList() {
    final var queryLimit = 1;

    final var limitedPokemonList = getApiClient()
      .getLimitedPokemonList(queryLimit);

    Asserts.check(
      limitedPokemonList.limitedPokemonList.size() == queryLimit,
      "The request must provide a minimum number of Pokémons."
    );
  }

  @Test
  @Tag("#TC005")
  @Description("Test Case #TC005: Request maximum the limit Pokemons.")
  public void maximumTheLimitOfThePokemonShortList() {
    final var queryLimit = getApiClient().getLimitedPokemonList(1).count; // 1279

    final var limitedPokemonList = getApiClient()
      .getLimitedPokemonList(queryLimit);

    Asserts.check(
      limitedPokemonList.count == queryLimit &&
      limitedPokemonList.limitedPokemonList.size() == queryLimit,
      "The request must provide a maximum number of Pokémons."
    );
  }

  @Test
  @Tag("#TC006")
  @Description(
    "Test Case #TC006: Each pokemon has in limited list has a name (name)."
  )
  public void eachPokemonInLimitedListHasName() {
    final var queryLimit = getApiClient().getLimitedPokemonList(1).count; // 1279

    final var limitedPokemonList = getApiClient()
      .getLimitedPokemonList(queryLimit);

    Asserts.check(
      limitedPokemonList.limitedPokemonList
        .stream()
        .anyMatch(previewPokemon -> previewPokemon.name.length() != 0),
      "Each pokemon in the limited list must have a name."
    );
  }
}
