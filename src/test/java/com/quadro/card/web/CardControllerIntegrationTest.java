package com.quadro.card.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quadro.card.domain.NewCardDTO;
import com.quadro.card.domain.data.Card;
import com.quadro.card.domain.data.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CardControllerIntegrationTest {

    @Autowired
    private CardRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.repository.deleteAll();
    }

    @Test
    public void shouldReturnAllCards() throws Exception {
        this.repository.save(new Card("Test 1", "Test"));
        this.repository.save(new Card("Test 2", null));

        this.mockMvc.perform(
                        get("/api/v1/cards")
                                .with(httpBasic("user", "pass"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test 1"))
                .andExpect(jsonPath("$[0].description").value("Test"))
                .andExpect(jsonPath("$[0].owner").value("test.user@test.com"))
                .andExpect(jsonPath("$[1].title").value("Test 2"))
                .andExpect(jsonPath("$[1].description").value(""))
                .andExpect(jsonPath("$[1].owner").value("test.user@test.com"));
    }

    @Test
    public void shouldCreateNewCard() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NewCardDTO newCardDTO = NewCardDTO.builder().title("New card").description("created").build();

        this.mockMvc.perform(
                        post("/api/v1/cards")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newCardDTO))
                                .with(httpBasic("user", "pass"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New card"))
                .andExpect(jsonPath("$.description").value("created"))
                .andExpect(jsonPath("$.owner").value("test.user@test.com"));
    }

    @Test
    public void denyRequestsWithoutAuthentication() throws Exception {
        this.mockMvc.perform(get("/api/v1/cards"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}