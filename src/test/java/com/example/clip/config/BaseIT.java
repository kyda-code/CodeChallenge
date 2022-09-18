package com.example.clip.config;

import com.example.clip.ClipApplication;
import com.example.clip.repos.PaymentRepository;
import com.example.clip.repos.SecurityRepository;
import java.nio.charset.Charset;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context, with all data
 * wiped out before each test.
 */
@SpringBootTest(
        classes = ClipApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Sql({"/data/clearAll.sql", "/data/securityData.sql"})
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    public SecurityRepository securityRepository;

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), Charset.forName("UTF-8"));
    }

    public String bearerToken() {
        // user bootify, expires 2040-01-01
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJib290aWZ5IiwiZXhwIjoyMjA4OTg4ODAwfQ." +
                "aK_vLd7zKm6xOJPpllSZHGRRpG-U_QUo3Jo3WI4VPOD_NUzFo8sRHl-AIKNRLv_WFhpjTKUdy9s2Un0JZimrcQ";
    }

}
