import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.Coordinates;
import org.example.GetFromUrl;
import org.example.WeatherApp;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import static org.example.WeatherApp.API_KEY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherTest {
    String jsonContent = "{\"coord\":{\"lon\":16.9252,\"lat\":52.4064},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":272.76,\"feels_like\":268.3,\"temp_min\":272.23,\"temp_max\":273.74,\"pressure\":1009,\"humidity\":89},\"visibility\":10000,\"wind\":{\"speed\":4.12,\"deg\":280},\"clouds\":{\"all\":0},\"dt\":1700996124,\"sys\":{\"type\":2,\"id\":19661,\"country\":\"PL\",\"sunrise\":1700980303,\"sunset\":1701010055},\"timezone\":3600,\"id\":3088171,\"name\":\"Poznań\",\"cod\":200}09,\"humidity\":89},\"visibility\":10000,\"wind\":{\"speed\":4.12,\"deg\":280},\"clouds\":{\"all\":0},\"dt\":1700996124,\"sys\":{\"type\":2,\"id\":19661,\"country\":\"PL\",\"sunrise\":1700980303,\"sunset\":1701010055},\"timezone\":3600,\"id\":3088171,\"name\":\"Poznań\",\"cod\":200}";


    @Before
public void setUp(){
    GetFromUrl getfrom = mock(GetFromUrl.class);
}


@Test
public void getFromUrlTest() {
        JSONObject testJSONObject = GetFromUrl.GetFromUrl("https://api.openweathermap.org/data/2.5/weather" +
                "?lat=" + "52.4064" +
                "&lon=" + "16.9252" +
                "&appid=" + API_KEY);
    assert testJSONObject != null;
    assertTrue(testJSONObject.toString().length()>400);
    }

//@Test
//    public void MockTest1(){
//    GetFromUrl.GetFromUrl() getfrom = mock(GetFromUrl.class);
//    JSONObject jsonObject1 = new JSONObject(jsonContent);
//    when(getfrom(anyString())).thenReturn(jsonObject1);
//
//}

    @Test
public void generateUrlTest(){

    assertEquals("https://api.openweathermap.org/data/2.5/weather" +
            "?lat=" + "52.4064" +
            "&lon=" + "16.9252" +
            "&appid=" + API_KEY, WeatherApp.GenerateUrl(new Coordinates(52.4064, 16.9252)));
}

}
