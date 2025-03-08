package pos.api.teampixl.org.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import pos.api.teampixl.org.models.Template;
import pos.api.teampixl.org.models.TemplateDTO;
import pos.api.teampixl.org.services.TemplateService;

/**
 * TemplateController
 *
 * <p>This class is the controller for the Template API.
 */
@RestController
@RequestMapping("/api/v1/template")
@Tag(name = "Template", description = "The Template API")
public class TemplateController {

  private final TemplateService templateService;

  /**
   * Constructor
   *
   * @param templateService The service to use for the controller
   */
  public TemplateController(TemplateService templateService) {
    this.templateService = templateService;
  }

  /**
   * Create a new template
   *
   * @param templateDTO The template to create
   * @return The created template
   */
  @PostMapping
  @Operation(summary = "Create a new template")
  public ResponseEntity<Map<String, Object>> createTemplate(@RequestBody TemplateDTO templateDTO) {
    Template template = new Template(templateDTO.getName(), templateDTO.getDescription());
    Template createdTemplate = templateService.createTemplate(template);
    return ResponseEntity.ok(createdTemplate.toMap());
  }

  /**
   * Get a template by id
   *
   * @param id The id of the template to get
   * @return The template
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a template by id")
  public ResponseEntity<Map<String, Object>> getTemplate(@PathVariable String id) {
    return ResponseEntity.ok(templateService.getTemplate(id).toMap());
  }

  /**
   * Get all templates
   *
   * @return All templates
   */
  @GetMapping
  @Operation(summary = "Get all templates")
  public ResponseEntity<Collection<ResponseEntity<Map<String, Object>>>> getTemplates() {
    Collection<Template> templates = templateService.getTemplates();
    return ResponseEntity.ok(templates.stream()
                                      .map(template -> ResponseEntity.ok(template.toMap()))
                                      .toList());
  }

  /**
   * Update a template by id
   *
   * @param id The id of the template to update
   * @param templateDTO The template to update
   * @return The updated template
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update a template by id")
  public ResponseEntity<Map<String, Object>> updateTemplate(
      @PathVariable String id, @RequestBody TemplateDTO templateDTO) {
    return ResponseEntity.ok(templateService.updateTemplate(id, templateDTO).toMap());
  }

  /**
   * Patch a template by id
   *
   * @param id The id of the template to patch
   * @param templateDTO The template to patch
   * @return The patched template
   */
  @PatchMapping("/{id}")
  @Operation(summary = "Patch a template by id")
  public ResponseEntity<Map<String, Object>> patchTemplate(
      @PathVariable String id, @RequestBody TemplateDTO templateDTO) {
    return ResponseEntity.ok(templateService.patchTemplate(id, templateDTO).toMap());
  }

  /**
   * Delete a template by id
   *
   * @param id The id of the template to delete
   * @return The response entity
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a template by id")
  public ResponseEntity<Void> deleteTemplate(@PathVariable String id) {
    templateService.deleteTemplate(id);
    return ResponseEntity.noContent().build();
  }
}
