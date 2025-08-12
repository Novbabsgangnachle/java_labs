import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.controller.OwnerController;
import ru.controller.PetController;
import ru.controller.PostController;
import ru.domain.PetType;
import ru.dto.PetDto;
import ru.dto.PostDto;
import ru.dto.OwnerDto;
import ru.security.PetSecurity;
import ru.service.PetService;
import ru.service.PostService;
import ru.service.OwnerService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = {
                PostController.class,
                PetController.class,
                OwnerController.class
        },
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        }
)
@AutoConfigureMockMvc(addFilters = false)

class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private PetService petService;

    @MockitoBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PetSecurity petSecurity;

    @Test
    @DisplayName("GET /api/posts/1 - success")
    void getPostById_ShouldReturnPostDto() throws Exception {
        PostDto dto = new PostDto();
        dto.setId(1L);
        dto.setTitle("Test Post");

        when(postService.getPostById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Post"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @DisplayName("GET /api/posts/price/below?price=1000")
    void getPostsByPriceBelow_ShouldReturnList() throws Exception {
        PostDto dto = new PostDto();
        dto.setTitle("Cheap Item");

        when(postService.getPostsWithPriceBelow(new BigDecimal("1000"))).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/posts/price/below?price=1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Cheap Item"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @DisplayName("DELETE /api/posts/1")
    void deletePost_ShouldReturnSuccess() throws Exception {
        doNothing().when(postService).deletePost(1L);

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @DisplayName("GET /api/pets/1 - success")
    void getPetById_ShouldReturnPetDto() throws Exception {
        PetDto dto = new PetDto();
        dto.setId(1L);
        dto.setName("Fluffy");

        when(petService.getPetById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Fluffy"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @DisplayName("GET /api/pets/type?typeName=DOG")
    void getPetsByType_ShouldReturnList() throws Exception {
        PetDto dto = new PetDto();
        dto.setName("Buddy");

        when(petService.getPetsByType(PetType.DOG)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/pets/type?typeName=DOG"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Buddy"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @DisplayName("DELETE /api/pets/1")
    void deletePet_ShouldReturnSuccess() throws Exception {
        doNothing().when(petService).deletePet(1L);

        mockMvc.perform(delete("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @DisplayName("GET /api/owners/1 - success")
    void getOwnerById_ShouldReturnOwnerDto() throws Exception {
        OwnerDto dto = new OwnerDto();
        dto.setId(1L);
        dto.setFirstName("John");

        when(ownerService.getOwnerDtoById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @DisplayName("GET /api/owners/email/john@example.com")
    void getOwnerByEmail_ShouldReturnOwnerDto() throws Exception {
        OwnerDto dto = new OwnerDto();
        dto.setEmail("john@example.com");

        when(ownerService.getOwnerDtoByEmail("john@example.com")).thenReturn(dto);

        mockMvc.perform(get("/api/owners/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("john@example.com"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @DisplayName("DELETE /api/owners/1")
    void deleteOwner_ShouldReturnSuccess() throws Exception {
        doNothing().when(ownerService).deleteOwner(1L);

        mockMvc.perform(delete("/api/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }
}
