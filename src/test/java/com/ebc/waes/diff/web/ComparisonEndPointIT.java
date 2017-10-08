package com.ebc.waes.diff.web;

import com.ebc.waes.diff.test.TextContent;
import com.ebc.waes.diff.web.interceptor.ExceptionHandler;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.MediaType;
import java.io.StringReader;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
@RunWith(Arquillian.class)
public class ComparisonEndPointIT {

    public static final String PATH_DIFF_LEFT = "/diff/24/left";
    public static final String PATH_DIFF_RIGHT = "/diff/24/right";
    public static final String PATH_DIFF_RESULT = "/diff/24";
    public static final String JSON_LEFT = "{\"content\": \"" + TextContent.SIMPLE_TEXT_LEFT_CONTENT_BASE64 + "\"}";
    public static final String JSON_RIGHT = "{\"content\": \"" + TextContent.SIMPLE_TEXT_RIGHT_CONTENT_BASE64 + "\"}";

    @Inject
    private ComparisonEndPoint endPoint;

    private Dispatcher dispatcher;

    @Deployment
    public static Archive createDeployment(){
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "diff.war")
                .addPackages(true, "com.ebc.waes.diff")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("diff.properties", "diff.properties")
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile());
        return webArchive;
    }

    @Before
    public void setup(){
        dispatcher = createDispatcher();
        dispatcher.getRegistry().addSingletonResource(endPoint);
    }

    @Test
    public void testAddDiffLeftSide() throws Exception{
        //given an correct json
        //when call the left endpoint
        MockHttpResponse response = makePostRequest(PATH_DIFF_LEFT, JSON_LEFT);
        //than should accept the content
        assertThat(response.getStatus(), equalTo(204));
    }


    @Test
    public void testAddDiffRightSide() throws Exception{
        //given an correct json
        //when call the right endpoint
        MockHttpResponse response = makePostRequest(PATH_DIFF_RIGHT, JSON_RIGHT);
        ///than should accept the content
        assertThat(response.getStatus(), equalTo(204));
    }

    @Test
    public void testProvideDiffResults() throws Exception{
        //given an correct left and right side content
        MockHttpResponse response = makePostRequest(PATH_DIFF_LEFT, JSON_LEFT);
        assertThat(response.getStatus(), equalTo(204));
        response = makePostRequest(PATH_DIFF_RIGHT, JSON_RIGHT);
        assertThat(response.getStatus(), equalTo(204));
        //when call diff endpoint
        response = makeGetRequest(PATH_DIFF_RESULT);
        //then should return a json with the content of comparison
        assertThat(response.getStatus(), equalTo(200));
        String jsonString = response.getContentAsString();
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();
        assertThat(jsonObject.getString("left"), equalTo(TextContent.SIMPLE_TEXT_LEFT_CONTENT_PLAN));
        assertThat(jsonObject.getString("right"), equalTo(TextContent.SIMPLE_TEXT_RIGHT_CONTENT_PLAN));
        assertThat(jsonObject.getString("diffs"), equalTo(TextContent.DIFF_LEFT_TO_RIGHT));
        assertThat(jsonObject.getInt("modifications"), equalTo(TextContent.MODIFICATIONS_LEFT_TO_RIGHT));
    }

    private Dispatcher createDispatcher() {
        Dispatcher dispatcher = new SynchronousDispatcher(new ResteasyProviderFactory());
        ResteasyProviderFactory.setInstance(dispatcher.getProviderFactory());
        RegisterBuiltin.register(dispatcher.getProviderFactory());
        dispatcher.getProviderFactory().registerProvider(ExceptionHandler.class);
        return dispatcher;
    }

    protected MockHttpResponse makeGetRequest(String path) throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get(path);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        return response;
    }

    private MockHttpResponse makePostRequest(String path, String json) throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.post(path);

        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(json.getBytes());

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        return response;
    }


}
