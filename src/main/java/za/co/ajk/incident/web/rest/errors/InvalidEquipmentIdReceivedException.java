package za.co.ajk.incident.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;


public class InvalidEquipmentIdReceivedException extends AbstractThrowableProblem {
    
    public InvalidEquipmentIdReceivedException() {
        super(ErrorConstants.CONSTRAINT_VIOLATION_TYPE, "Invalid Equipment ID received", Status.BAD_REQUEST);
    }
}
//        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login already in use", "userManagement", "userexists");
