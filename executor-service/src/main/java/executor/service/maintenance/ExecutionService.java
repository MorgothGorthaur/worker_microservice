package executor.service.maintenance;

import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(WebDriver webDriver, ScenarioSourceListener scenarioSourceListener,
                 ScenarioExecutor scenarioExecutor);
}
