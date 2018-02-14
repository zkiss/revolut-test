package com.revolut.tests.zkiss.transfersvc.resources;

import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.jdbi.v3.core.Jdbi;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TransferResourceIT {

    private static Jdbi dbi = mock(Jdbi.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TransferResource(dbi))
            .build();


    @Test
    public void name() {
        Response response = resources.target("/transfers")
                .request()
                .post(Entity.entity(ImmutableMap.of(
                        "from", ImmutableMap.of(
                                "sortCode", "asd",
                                "accountNumber", "asd"
                        ),
                        "to", ImmutableMap.of(
                                "sortCode", "asd",
                                "accountNumber", "asd"
                        ),
                        "amount", new BigDecimal("12")
                        ),
                        MediaType.APPLICATION_JSON));

        String responseJson = response.readEntity(String.class);
        assertThat((String) JsonPath.read(responseJson, "$.transferred")).isEqualTo("false");
    }
}