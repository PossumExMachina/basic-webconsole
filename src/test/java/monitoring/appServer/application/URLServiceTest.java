package monitoring.appServer.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class URLServiceTest {

    URLService urlService =  new URLService();

    @Test
    void testCreateURL(){
        String applicationName = "application1";
        String result = urlService.createURL(applicationName);
        assertEquals("http://localhost:8080/application1/actuator/health", result);
    }

//
//    @Test
//    @Disabled
//    public void testMakeHttpRequest_Success() throws IOException {
//        String urlString = "http://example.com";
//        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
//        BufferedReader mockReader = mock(BufferedReader.class);
//
//        when(yourTestClass.createHttpURLConnection(urlString)).thenReturn(mockConnection);
//        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
//        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream("{'key':'value'}".getBytes()));
//        when(mockConnection.disconnect()).thenCallRealMethod();
//        when(mockReader.lines()).thenReturn(new BufferedReader(new StringReader("{'key':'value'}")).lines());
//
//        // Act
//        JsonNode result = yourTestClass.makeHttpRequest(urlString);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("value", result.get("key").asText());
//    }
//
//    private HttpURLConnection mock(Class<BufferedReader> bufferedReaderClass) {
//    }
//
//    @Test
//    @Disabled
//    public void testMakeHttpRequest_EmptyResponse() throws IOException, IOException {
//        // Arrange
//        String urlString = "http://example.com";
//        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
//        BufferedReader mockReader = mock(BufferedReader.class);
//
//        when(yourTestClass.createHttpURLConnection(urlString)).thenReturn(mockConnection);
//        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
//        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes()));
//        when(mockConnection.disconnect()).thenCallRealMethod();
//
//        // Act & Assert
//        assertThrows(IOException.class, () -> yourTestClass.makeHttpRequest(urlString));
//    }
//
//    @Test
//    @Disabled
//    public void testMakeHttpRequest_InvalidResponseCode() throws IOException {
//        // Arrange
//        String urlString = "http://example.com";
//        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
//
//        when(yourTestClass.createHttpURLConnection(urlString)).thenReturn(mockConnection);
//        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
//        when(mockConnection.disconnect()).thenCallRealMethod();
//
//        // Act & Assert
//        assertThrows(IOException.class, () -> yourTestClass.makeHttpRequest(urlString));
//    }

    @Test
    public void testCreateHttpURLConnection() throws IOException {
        String urlString = "http://example.com";
        HttpURLConnection connection = urlService.createHttpURLConnection(urlString);
        assertNotNull(connection);
    }

}