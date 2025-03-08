package pos.api.teampixl.org.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import pos.api.teampixl.org.models.data.DataManager;
import pos.api.teampixl.org.models.data.MetadataWrapper;

/** Template model class */
public class Template extends DataManager {

  /**
   * Constructor
   * 
   * @param Name       The name of the template
   * @param Description The description of the template
   */
  public Template(String Name, String Description) {
    super(initializeMetadata());

    this.data.put("name", Name);
    this.data.put("description", Description);
  }

  private static MetadataWrapper initializeMetadata() {

    Map<String, Object> metadataMap = new HashMap<>();
    metadataMap.put("id", UUID.randomUUID().toString());

    return new MetadataWrapper(metadataMap);
  }

}
