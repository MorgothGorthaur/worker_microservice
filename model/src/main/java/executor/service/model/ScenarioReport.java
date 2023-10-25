package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioReport {
    private Scenario scenario;
    private ProxyConfigHolder proxy;
    private Set<StepReport> stepReports;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
