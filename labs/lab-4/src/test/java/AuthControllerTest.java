import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import ru.controller.AuthController;
import ru.controller.OwnerController;
import ru.controller.PetController;
import ru.security.SecurityConfig;
import ru.security.jwt.JwtAuthenticationFilter;
import ru.security.jwt.JwtTokenProvider;
import ru.service.OwnerDetailService;
import ru.service.OwnerService;
import ru.service.PetService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@WebMvcTest(controllers = {AuthController.class, OwnerController.class, PetController.class})
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})
@EnableMethodSecurity
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OwnerService ownerService;

    @MockitoBean
    private PetService petService;

    @MockitoBean
    private OwnerDetailService ownerDetailService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void testLoginAdmin() throws Exception {
        String email = "admin@example.com";
        String password = "123";
        String token = "jwt-admin-token";

        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtTokenProvider.generateToken(any())).thenReturn(token);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "admin@example.com",
                            "password": "123"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    @WithMockUser(username = "admin@email.com", roles = {"ADMIN"})
    void testCreateOwnerAsAdmin() throws Exception {
        mockMvc.perform(post("/api/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "email":  "newuser@example.com",
                  "password": "123",
                  "firstName": "Иван",
                  "lastName": "Иванов"
                }
                """))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginNewUser() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken("new@user.com", "123456")
        );
        when(jwtTokenProvider.generateToken(any())).thenReturn("jwt-user-token");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "new@user.com",
                        "password": "123456"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-user-token"));
    }


    @Test
    @WithMockUser(username = "new@user.com", roles = {"USER"})
    void testCreatePetAsUserWithMockUser() throws Exception {
        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "Мурзик",
                        "type": "CAT",
                        "age": 5,
                        "ownerId": 2
                    }
                    """))
                .andExpect(status().isOk());
    }

}
