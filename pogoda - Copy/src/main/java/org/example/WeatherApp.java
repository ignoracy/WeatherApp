package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;


public class WeatherApp {


    public static final String API_KEY = "93eb2da764c8ae5cfdb8c36efa5b00d7";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        Map<String, Coordinates> cities = new HashMap<>();
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get("cityList.json")));
            JSONObject cityList = new JSONObject(jsonContent);
            Iterator<String> keys = cityList.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                Double lat = cityList.getJSONObject(key).getDouble("lat");
                Double lon = cityList.getJSONObject(key).getDouble("lon");
                Coordinates coordinates1 = new Coordinates(lat, lon);
                cities.put(key, coordinates1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = new JSONObject();


        while (true) {


            System.out.println("P-Podaj miasto, Z-Zakończ");
            String input = scanner.nextLine();

            if ("Z".equalsIgnoreCase(input)) {
                break;
            } else if ("P".equalsIgnoreCase(input)) {
                System.out.print(">>");
                String cityName = scanner.nextLine();
                Coordinates coordinates = cities.get(cityName);

                if (coordinates != null) {

                    WeatherData weatherData = getWeatherData(GetFromUrl.GetFromUrl(GenerateUrl(coordinates)));
                    System.out.println("Temp: " + weatherData.getTemperature() + " Wilgotność: " + weatherData.getHumidity() + " Ciśnienie: " + weatherData.getPressure());

                    saveToFile(cityName, weatherData, scanner);
                } else {
                    System.out.println("Nieznane miasto");
                }
            }
        }
    }

    public static String GenerateUrl(Coordinates coordinates) {
        String result = "https://api.openweathermap.org/data/2.5/weather" + "?lat=" + coordinates.getLatitude() + "&lon=" + coordinates.getLongitude() + "&appid=" + API_KEY;
        return result;
    }

    private static WeatherData getWeatherData(JSONObject jsonObject) {


        WeatherData weatherData1 = new WeatherData();
        weatherData1.temperature = jsonObject.getJSONObject("main").getDouble("temp");
        weatherData1.humidity = jsonObject.getJSONObject("main").getDouble("humidity");
        weatherData1.pressure = jsonObject.getJSONObject("main").getDouble("pressure");

        return weatherData1;


    }

    private static void saveToFile(String cityName, WeatherData weatherData, Scanner scanner) {
        System.out.println("P-PDF J-JSON X-XML");
        System.out.print(">>");
        String fileType = scanner.nextLine();

        try {
            switch (fileType.toUpperCase()) {
                case "P":
                    saveToPdf(cityName, weatherData);
                    break;
                case "J":
                    saveToJson(cityName, weatherData);
                    break;
                case "X":
                    saveToXml(cityName, weatherData);
                    break;
                default:
                    System.out.println("Nieprawidłowy format pliku");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveToPdf(String cityName, WeatherData weatherData) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(cityName + "_weather.pdf"));
            document.open();
            document.add(new Paragraph(cityName + " - Pogoda"));
            document.add(new Paragraph("Temperatura: " + weatherData.getTemperature()));
            document.add(new Paragraph("Wilgotnosc: " + weatherData.getHumidity()));
            document.add(new Paragraph("Cisnienie: " + weatherData.getPressure()));
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }


    private static void saveToJson(String cityName, WeatherData weatherData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new FileOutputStream(cityName + "_weather.json"), weatherData);
    }

    private static void saveToXml(String cityName, WeatherData weatherData) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new FileOutputStream(cityName + "_weather.xml"), weatherData);
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherData {
    public double temperature;
    public double humidity;
    public double pressure;

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }
}
