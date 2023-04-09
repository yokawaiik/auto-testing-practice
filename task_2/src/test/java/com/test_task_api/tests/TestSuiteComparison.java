package com.test_task_api.tests;

import com.test_task_api.configuration.JUnitTestBase;
import com.test_task_api.constants.AbilitiesNameConstants;
import com.test_task_api.constants.PokemonsNamesConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
@Epic("Test Suite: Pokemon comparison")
public class TestSuiteComparison extends JUnitTestBase {

  @Test
  @Tag("#TC001")
  @Description(
    "Test Case #TC001: Check rattata, unlike pokemon pidgeotto, has less weight and has skill (ability) escape (run away)."
  )
  @Story("User decided to compare two different pokemons.")
  public void compareTwoPokemons() {
    final var rattata = getApiClient()
      .getPokemon(PokemonsNamesConstants.RATTATA);
    final var pidgeotto = getApiClient()
      .getPokemon(PokemonsNamesConstants.PIDGEOTTO);

    Asserts.check(
      rattata.name.equalsIgnoreCase(PokemonsNamesConstants.RATTATA),
      "Was not a rattata."
    );

    Asserts.check(
      pidgeotto.name.equalsIgnoreCase(PokemonsNamesConstants.PIDGEOTTO),
      "Was not a pidgeotto."
    );

    Asserts.check(
      rattata.weight < pidgeotto.weight,
      "Rattata has less weight."
    );

    Asserts.check(
      rattata.abilities
        .stream()
        .anyMatch(obj -> obj.name.equals(AbilitiesNameConstants.RUN_AWAY)),
      "Rattata doesn't have the ability \"run-away\"."
    );
  }
}
