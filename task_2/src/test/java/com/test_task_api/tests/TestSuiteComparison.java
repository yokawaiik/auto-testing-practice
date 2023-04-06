package com.test_task_api.tests;

import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import com.test_task_api.configuration.JUnitTestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Execution(ExecutionMode.CONCURRENT)
@Epic("Test Suite: Pokemon comparison")
public class TestSuiteComparison extends JUnitTestBase {

  @Test
  @Tag("#TC001")
  @Description("Test Case #TC001: Check rattata, unlike pokemon pidgeotto, has less weight and has skill (ability) escape (run away).")
  @Story("User decided to compare two different pokemons.")
  public void compareTwoPokemons() {

    final var rattata = getApiClient().getPokemon("rattata");

    final var pidgeotto = getApiClient().getPokemon("pidgeotto");

    System.out.println(rattata.toString());

    Asserts.check(rattata.name.equalsIgnoreCase("rattata"), "Was not a rattata.");

    Asserts.check(pidgeotto.name.equalsIgnoreCase("pidgeotto"), "Was not a pidgeotto.");

    Asserts.check(rattata.weight < pidgeotto.weight, "Rattata has less weight.");

    Asserts.check(rattata.abilities.stream().anyMatch(obj -> obj.name.equals("run-away")),
        "Rattata doesn't have the ability \"run-away\".");

  }

}
