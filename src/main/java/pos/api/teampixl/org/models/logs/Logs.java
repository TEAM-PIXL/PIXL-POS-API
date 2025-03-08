package pos.api.teampixl.org.models.logs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import pos.api.teampixl.org.common.TimeUtil;
import pos.api.teampixl.org.models.data.DataManager;
import pos.api.teampixl.org.models.data.MetadataWrapper;
import pos.api.teampixl.org.models.logs.definitions.Action;
import pos.api.teampixl.org.models.logs.definitions.Category;
import pos.api.teampixl.org.models.logs.definitions.Priority;
import pos.api.teampixl.org.models.logs.definitions.Status;
import pos.api.teampixl.org.models.logs.definitions.Type;
import pos.api.teampixl.org.models.logs.network.Util;

/**
 * UserLogs class is a construct for creating UserLogs object. Extends DataManager.
 * <p>
 * Metadata:
 * - user_id: User ID
 * - log_id: Log ID
 * - log_timestamp: timestamp
 * - log_action: Action
 * <p>
 * Data:
 * - log_description: Description
 * - log_status: Status
 * - log_type: Type
 * - log_category: Category
 * - log_priority: Priority
 * - log_location: Location
 * - log_device: Device
 * - log_ip: IP
 * - log_mac: MAC
 * - log_os: OS
 * @see DataManager
 * @see MetadataWrapper
 */
public final class Logs extends DataManager {

    /**
     * Constructor for UserLogs object.
     *
     * @param action: Action
     * @param logStatus: Status
     * @param logType: Type
     * @param logCategory: Category
     * @param logPriority: Priority
     * @param logIp: String
     * @param logMac: String
     * @param logOs: String
     * @param logDevice: String
     * @param description: String
     * @param userId: String
     *
     *  @see DataManager
     *  @see MetadataWrapper
     */
    public Logs(Action action,
                Status logStatus,
                Type logType,
                Category logCategory,
                Priority logPriority,
                String logIp,
                String logMac,
                String logOs,
                String logDevice,
                String userId,
                String description
    ) throws Exception {
        super(initializeMetadata(action, userId));

        this.data = new HashMap<>();
        this.data.put("log_status", logStatus);
        this.data.put("log_type", logType);
        this.data.put("log_category", logCategory);
        this.data.put("log_priority", logPriority);
        this.data.put("log_location", Util.getLocation(logIp));
        this.data.put("log_device", logDevice);
        this.data.put("log_ip", logIp);
        this.data.put("log_mac", logMac);
        this.data.put("log_os", logOs);
        this.data.put("log_description", description);
    }

    /**
     * Overloaded constructor for UserLogs object.
     *
     * @param action: Action
     * @param logStatus: Status
     * @param logType: Type
     * @param logCategory: Category
     * @param logPriority: Priority
     * @param logIp: String
     * @param logMac: String
     * @param logOs: String
     * @param logDevice: String
     * @param userId: String
     *
     * @see DataManager
     * @see MetadataWrapper
     */
    public Logs(Action action,
                Status logStatus,
                Type logType,
                Category logCategory,
                Priority logPriority,
                String logIp,
                String logMac,
                String logOs,
                String logDevice,
                String userId
    ) throws Exception {
        super(initializeMetadata(action, userId));

        this.data = new HashMap<>();
        this.data.put("log_status", logStatus);
        this.data.put("log_type", logType);
        this.data.put("log_category", logCategory);
        this.data.put("log_priority", logPriority);
        this.data.put("log_location", Util.getLocation(logIp));
        this.data.put("log_device", logDevice);
        this.data.put("log_ip", logIp);
        this.data.put("log_mac", logMac);
        this.data.put("log_os", logOs);
        this.data.put("log_description", generateLogDescription());
    }

    /**
     * Overloaded constructor for UserLogs object.
     * 
     * @param action: Action
     * @param logStatus: Status
     * @param logType: Type
     * @param logCategory: Category
     * @param logPriority: Priority
     * @param userId: String
     * 
     * @see DataManager
     * @see MetadataWrapper
     */
    public Logs(Action action,
                Status logStatus,
                Type logType,
                Category logCategory,
                Priority logPriority,
                String userId
    ) throws Exception {
        super(initializeMetadata(action, userId));

        this.data = new HashMap<>();
        this.data.put("log_status", logStatus);
        this.data.put("log_type", logType);
        this.data.put("log_category", logCategory);
        this.data.put("log_priority", logPriority);
        String IP = Util.getIp();
        this.data.put("log_location", Util.getLocation(IP));
        this.data.put("log_device", Util.getDeviceInfo());
        this.data.put("log_ip", IP);
        this.data.put("log_mac", Util.getMacAddress());
        this.data.put("log_os", Util.checkOS());
        this.data.put("log_description", generateLogDescription());
    }

    private static MetadataWrapper initializeMetadata(Action action, String userId) {
        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("user_id", userId != null ? userId : "Unknown");
        metadataMap.put("log_id", UUID.randomUUID().toString());
        metadataMap.put("log_timestamp", System.currentTimeMillis());
        metadataMap.put("log_action", action != null ? action : "Unknown");
        return new MetadataWrapper(metadataMap);
    }

    /**
     * Generates a log description based on the metadata values.
     *
     * @return String
     */
    public String generateLogDescription() {
        long timestamp = (long) this.getMetadataValue("log_timestamp");
        String readableTimestamp = TimeUtil.convertUnixTimestampToReadable(timestamp);
        return "User " + this.getDataValue("user_id") + " performed the action " + this.getMetadataValue("log_action") + " at the time " + readableTimestamp;
    }
}
