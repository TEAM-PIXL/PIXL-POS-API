package pos.api.teampixl.org.models.logs.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeviceInfoRetriever {
    public static String getDeviceModel() {
        String os = System.getProperty("os.name").toLowerCase();
        String model = null;
        try {
            Process process;
            if (os.contains("mac")) {
                process = new ProcessBuilder("system_profiler", "SPHardwareDataType").start();
            } else if (os.contains("win")) {
                process = new ProcessBuilder("wmic", "computersystem", "get", "model").start();
            } else if (os.contains("nux") || os.contains("nix")) {
                process = new ProcessBuilder("cat", "/sys/devices/virtual/dmi/id/product_name").start();
            } else {
                return "Unsupported operating system.";
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    if (os.contains("win")) {
                        if (!line.toLowerCase().contains("model")) {
                            model = line;
                            break;
                        }
                    } else if (os.contains("mac")) {
                        if (line.startsWith("Model Identifier:")) {
                            model = line.split(":")[1].trim();
                            break;
                        }
                    } else {
                        model = line;
                        break;
                    }
                }
            }
            return (model != null) ? model : "Device model not found.";
        } catch (IOException e) {
            return "Error retrieving device model: " + e.getMessage();
        }
    }
}