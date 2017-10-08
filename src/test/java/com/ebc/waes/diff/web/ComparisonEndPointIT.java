package com.ebc.waes.diff.web;

import com.ebc.waes.diff.domain.dto.SourceDTO;
import com.ebc.waes.diff.test.TextContent;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
@Ignore
public class ComparisonEndPointIT {

    public static final String BASE_URL = "http://localhost:8080/v1";
    public static final String PATH_DIFF_LEFT = "/diff/{id}/left";
    public static final String PATH_DIFF_RIGHT = "/diff/{id}/right";
    public static final String PATH_DIFF_RESULT = "/diff/{id}";
    public static final String ID_PARAM = "id";
    public static final String ID_DIFF_VALUE = "24";
    private Client client;

    public ComparisonEndPointIT() {
        this.client = ClientBuilder.newClient().register(ComparisonEndPoint.class);
    }

    private WebTarget target(String path) {
        return this.client.target(BASE_URL + path);
    }

    @Test
    public void testAddDiffLeftSide() {
        SourceDTO source = new SourceDTO();
        source.setContent(TextContent.SIMPLE_TEXT_LEFT_CONTENT_BASE64);
        Response response = target(PATH_DIFF_LEFT)
                .resolveTemplate(ID_PARAM, ID_DIFF_VALUE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(source, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), equalTo(204));
    }


    @Test
    public void testAddDiffRightSide() {
        SourceDTO source = new SourceDTO();
        source.setContent(TextContent.SIMPLE_TEXT_RIGHT_CONTENT_BASE64);
        Response response = target(PATH_DIFF_RIGHT)
                .resolveTemplate(ID_PARAM, ID_DIFF_VALUE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(source, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), equalTo(204));

    }

    @Test
    public void testProvideDiffResults() {
        Response response = target(PATH_DIFF_RESULT)
                .resolveTemplate(ID_PARAM, ID_DIFF_VALUE)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), equalTo(200));
    }


}
