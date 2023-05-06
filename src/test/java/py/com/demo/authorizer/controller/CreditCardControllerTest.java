package py.com.demo.authorizer.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import py.com.demo.authorizer.client.impl.GlobalCreditClientImpl;
import py.com.demo.authorizer.entity.SystemInfoResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc; 
    
    @MockBean
    GlobalCreditClientImpl clientImpl;

    private final String URL_PREFIX = "/";

    @Nested
    @DisplayName("getSystemInfo")
    public class GetSystemInfo{

        @Test
        @DisplayName("Successfully call getSystemInfo")
        void getSystemInfoOk() throws Exception{
            
        SystemInfoResponse response = new SystemInfoResponse("test", "database",
         "user", "Global");
        when(clientImpl.getSystemInfo()).thenReturn(response);

        mockMvc.perform(get(URL_PREFIX + "api/001001/system-info"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profile").value("test"));
        }

        @Test
        @DisplayName("Successfully call getSystemInfo")
        void getSystemInfoServiceNotAvailable() throws Exception{
            
        SystemInfoResponse response = new SystemInfoResponse("test", "database",
         "user", "Global");
        when(clientImpl.getSystemInfo()).thenReturn(response);

        mockMvc.perform(get(URL_PREFIX + "api/001001/system-info"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profile").value("test"));
        }

    }
    
}
