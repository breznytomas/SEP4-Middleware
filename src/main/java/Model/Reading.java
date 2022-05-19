package Model;

import java.util.List;

public class Reading {




    public String BoardId;

    public List<Temperature> TemperatureList;
    public List<Humidity> HumidityList;
    public List<Light> LightLists;
    public List<CarbonDioxide> CarbonDioxideList;


    public Reading(String boardId, List<Temperature> temperatureList, List<Humidity> humidityList, List<Light> lightLists, List<CarbonDioxide> carbonDioxideList) {
        BoardId = boardId;
        TemperatureList = temperatureList;
        HumidityList = humidityList;
        LightLists = lightLists;
        CarbonDioxideList = carbonDioxideList;
    }
}
