# UI testing

## Test cases docs
https://docs.google.com/document/d/1wqJDa_Fttxmbeq8RzEGwXqAXtPZBzkhBPvlI1hyqKrU/edit?usp=sharing


## Stack
- Java
- Selenium
- Maven
- Page Object Model
- Allure


## Run tests

### Run single test
mvn test -Dtest="TheSingleTestClass"    

### Run all tests
    mvn test

### Run single test in headless mode (default)
    mvn -D"app.webdriver.mode.headless=true" test

### Run single test with specific browser version
    mvn -D"app.webdriver.chrome.version=${PASTE YOUR VERSION HERE}" test

### Run allure server
    allure serve

