package pos.api.teampixl.org.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import pos.api.teampixl.org.models.Template;
import pos.api.teampixl.org.models.TemplateDTO;

/** The template service */
@Service
public class TemplateService {

  private final Map<String, Template> templates = new HashMap<>();

  public TemplateService() {}

  /**
   * Create a new template
   *
   * @param template the template to create
   * @return the created template
   */
  public Template createTemplate(Template template) {
    String id = (String) template.getMetadataValue("id");
    templates.put(id, template);
    return template;
  }

  /**
   * Get a template by id
   *
   * @param id the id of the template
   * @return the template
   */
  public Template getTemplate(String id) {
    Template template = templates.get(id);
    if (template == null) {
      throw new IllegalArgumentException("Template not found");
    }
    return template;
  }

  /**
   * Update a template by id
   *
   * @param id the id of the template
   * @param templateDTO the updated template
   * @return the updated template
   */
  public Template updateTemplate(String id, TemplateDTO templateDTO) {
    Template template = templates.get(id);
    if (template == null) {
      throw new IllegalArgumentException("Template not found");
    }

    template.setDataValue("name", templateDTO.getName());
    template.setDataValue("description", templateDTO.getDescription());

    return template;
  }

  /**
   * Patch a template by id
   *
   * @param id the id of the template
   * @param templateDTO the updated template
   * @return the updated template
   */
  public Template patchTemplate(String id, TemplateDTO templateDTO) {
    Template template = templates.get(id);
    if (template == null) {
      throw new IllegalArgumentException("Template not found");
    }

    template.setDataValue("name", templateDTO.getName());
    template.setDataValue("description", templateDTO.getDescription());

    return template;
  }

  /**
   * Delete a template by id
   *
   * @param id the id of the template
   */
  public void deleteTemplate(String id) {
    templates.remove(id);
  }

  /**
   * Get all templates
   *
   * @return the templates
   */
  public Collection<Template> getTemplates() {
    return templates.values();
  }
}
