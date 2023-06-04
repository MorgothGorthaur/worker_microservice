package executor.service;

import executor.service.exception.ResourceFileNotFoundException;
import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonScenarioSourceTest {
    private final String INCORRECT_FILENAME = "test-scenarios.js";

    @Test
    public void testGetScenariosIfFileOk() {
        String resourceName = "test-scenarios.json";
        JsonScenarioSource reader = new JsonScenarioSource(getAbsolutePath(resourceName));
        assertEquals(getScenariosList(), reader.getScenarios());
    }

    @Test
    public void testGetScenariosIfFileWithWrongName() {
        JsonScenarioSource reader = new JsonScenarioSource(getAbsolutePath(INCORRECT_FILENAME));

        Exception exception = assertThrows(RuntimeException.class, reader::getScenarios);
        assertEquals(getAbsolutePath(INCORRECT_FILENAME) + " (No such file or directory)", exception.getMessage());
    }

    @Test
    public void testGetScenariosIfFileWithWrongNameLocatedInResources() {
        JsonScenarioSource reader = new JsonScenarioSource(INCORRECT_FILENAME);

        Exception exception = assertThrows(ResourceFileNotFoundException.class, reader::getScenarios);
        assertEquals("File test-scenarios.js not found in \"resources\" folder", exception.getMessage());
    }

    @Test
    public void testGetScenariosIfFileIsInvalid() {
        String resourceName = "test-invalid-scenarios.json";
        JsonScenarioSource reader = new JsonScenarioSource(getAbsolutePath(resourceName));

        Exception exception = assertThrows(RuntimeException.class, reader::getScenarios);
        assertEquals("""
                Unexpected character ('"' (code 34)): was expecting comma to separate Object entries
                 at [Source: (File); line: 4, column: 6] (through reference chain: java.util.ArrayList[0])""",
                exception.getMessage());
    }

    public List<ScenarioDto> getScenariosList() {
        StepDto firstStepFirstScenario = new StepDto("clickCss", "body > ul > li:nth-child(1) > a");
        StepDto secondStepFirstScenario = new StepDto("sleep", "5:10");
        StepDto thirdStepFirstScenario = new StepDto("clickXpath", "/html/body/p");

        List<StepDto> stepsFirstScenario = List.of(firstStepFirstScenario, secondStepFirstScenario, thirdStepFirstScenario);
        ScenarioDto firstScenario = new ScenarioDto("test scenario 1", "http://info.cern.ch", stepsFirstScenario);

        StepDto firstStepSecondScenario = new StepDto("clickXpath", "/html/body/p");
        StepDto secondStepSecondScenario = new StepDto("sleep", "5:10");
        StepDto thirdStepSecondScenario = new StepDto("clickCss", "body > ul > li:nth-child(1) > a");

        List<StepDto> stepsSecondScenario = List.of(firstStepSecondScenario, secondStepSecondScenario, thirdStepSecondScenario);
        ScenarioDto secondScenario = new ScenarioDto("test scenario 2", "http://info.cern.ch", stepsSecondScenario);

        return List.of(firstScenario, secondScenario);
    }

    public String getAbsolutePath(String resourceName) {
        return new File("src/test/resources/" + resourceName).getAbsolutePath();
    }
}
