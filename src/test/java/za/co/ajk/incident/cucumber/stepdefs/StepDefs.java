package za.co.ajk.incident.cucumber.stepdefs;

import za.co.ajk.incident.IncidentModuleApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = IncidentModuleApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
