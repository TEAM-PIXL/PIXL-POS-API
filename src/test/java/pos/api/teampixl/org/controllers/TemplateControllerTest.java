package pos.api.teampixl.org.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import pos.api.teampixl.org.models.Template;
import pos.api.teampixl.org.models.TemplateDTO;
import pos.api.teampixl.org.services.TemplateService;

@WebMvcTest(controllers = TemplateController.class)
@ContextConfiguration(classes = { TemplateController.class })
@AutoConfigureMockMvc(addFilters = false)
public class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TemplateService templateService;

    private ObjectMapper mapper;

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    void createTemplate() throws Exception {
        Template mockTemplate = new Template("mockName","mockDesc");
        when(templateService.createTemplate(any(Template.class))).thenReturn(mockTemplate);

        TemplateDTO dto = new TemplateDTO();
        dto.setName("requestName");
        dto.setDescription("requestDesc");

        mockMvc.perform(
            post("/api/v1/template")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.metadata.id").exists())
        .andExpect(jsonPath("$.data.name").value("mockName"))
        .andExpect(jsonPath("$.data.description").value("mockDesc"));
    }

    @Test
    void getTemplate() throws Exception {
        Template mockTemplate = new Template("mockName","mockDesc");
        when(templateService.getTemplate(eq("123"))).thenReturn(mockTemplate);

        mockMvc.perform(get("/api/v1/template/123"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.metadata.id").exists())
        .andExpect(jsonPath("$.data.name").value("mockName"))
        .andExpect(jsonPath("$.data.description").value("mockDesc"));
    }

    @Test
    void getTemplates() throws Exception {
        Template mockOne = new Template("n1","d1");
        Template mockTwo = new Template("n2","d2");
        Collection<Template> templates = new ArrayList<>(List.of(mockOne, mockTwo));
        when(templateService.getTemplates()).thenReturn(templates);

        mockMvc.perform(get("/api/v1/template"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].body.metadata.id").exists())
        .andExpect(jsonPath("$[0].body.data.name").value("n1"))
        .andExpect(jsonPath("$[0].body.data.description").value("d1"))
        .andExpect(jsonPath("$[1].body.metadata.id").exists())
        .andExpect(jsonPath("$[1].body.data.name").value("n2"))
        .andExpect(jsonPath("$[1].body.data.description").value("d2"));
    }

    @Test
    void updateTemplate() throws Exception {
        Template updated = new Template("updatedName","updatedDesc");
        when(templateService.updateTemplate(eq("abc"), any(TemplateDTO.class))).thenReturn(updated);

        TemplateDTO dto = new TemplateDTO();
        dto.setName("reqName");
        dto.setDescription("reqDesc");

        mockMvc.perform(
            put("/api/v1/template/abc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.metadata.id").exists())
        .andExpect(jsonPath("$.data.name").value("updatedName"))
        .andExpect(jsonPath("$.data.description").value("updatedDesc"));
    }

    @Test
    void patchTemplate() throws Exception {
        Template patched = new Template("patchedName","patchedDesc");
        when(templateService.patchTemplate(eq("xyz"), any(TemplateDTO.class))).thenReturn(patched);

        TemplateDTO dto = new TemplateDTO();
        dto.setName("partialName");
        dto.setDescription("partialDesc");

        mockMvc.perform(
            patch("/api/v1/template/xyz")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.metadata.id").exists())
        .andExpect(jsonPath("$.data.name").value("patchedName"))
        .andExpect(jsonPath("$.data.description").value("patchedDesc"));
    }

    @Test
    void deleteTemplate() throws Exception {
        mockMvc.perform(delete("/api/v1/template/999"))
        .andExpect(status().isNoContent());

        Mockito.verify(templateService, Mockito.times(1)).deleteTemplate(eq("999"));
    }
}
