package pos.api.teampixl.org.models.logs;

import pos.api.teampixl.org.common.logger.Logger;
import pos.api.teampixl.org.models.logs.definitions.Action;
import pos.api.teampixl.org.models.logs.definitions.Category;
import pos.api.teampixl.org.models.logs.definitions.Priority;
import pos.api.teampixl.org.models.logs.definitions.Status;
import pos.api.teampixl.org.models.logs.definitions.Type;

public class LogTask implements Runnable {
    private final Action ACTION;
    private final Status LOG_STATUS;
    private final Type LOG_TYPE;
    private final Category LOG_CATEGORY;
    private final Priority LOG_PRIORITY;
    private final String ID = "SYSTEM";

    public LogTask(Action action, Status logStatus, Type logType, Category logCategory, Priority logPriority) {
        this.ACTION = action;
        this.LOG_STATUS = logStatus;
        this.LOG_TYPE = logType;
        this.LOG_CATEGORY = logCategory;
        this.LOG_PRIORITY = logPriority;
    }

    @Override
    public void run() {
        try {
            Logs logSet = new Logs(ACTION, LOG_STATUS, LOG_TYPE, LOG_CATEGORY, LOG_PRIORITY, ID);
            System.out.println("The log has the contents: " + logSet);
        } catch (Exception e) {
            Logger.getLogger(LogTask.class.getName()).error("Error while logging", e);
        }
    }
}
