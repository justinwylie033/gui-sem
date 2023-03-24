package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Map;

@Controller
public class CapitalCityController {

  @Autowired
  private JdbcTemplate jdbcTemplate;


  @GetMapping("/world-capital-cities")
  public String showWorldCapitalCitiesForm(Model model) {
  model.addAttribute("City", new City());
  return "CapitalCitiesForm";
  }
  
  @PostMapping("/world-capital-cities")
  public String processWorldCapitalCitiesForm(@ModelAttribute City city, Model model) {
      Integer limit = city.getLimit();
      String sql = "SELECT c.Name AS City, co.Name AS Country, c.Population " +
              "FROM city c " +
              "JOIN country co ON c.CountryCode = co.Code AND c.ID = co.Capital " +
              "ORDER BY c.Population DESC";
      if (limit != null && limit > 0) {
          sql += " LIMIT " + limit;
      }
      List<Map<String, Object>> cities = jdbcTemplate.queryForList(sql);
      model.addAttribute("cities", cities);
      return "CapitalCitiesResult";
  }
  
  @GetMapping("/continent-capital-cities")
public String ContinentCapitalCitiesForm(Model model) {
model.addAttribute("Country", new Country());
return "ContinentCapitalCitiesForm";
}

@PostMapping("/continent-capital-cities")
public String ContinentCapitalCitiesForm(@ModelAttribute Country country, Model model) {
Integer limit = country.getLimit();
String continentName = country.getName();
String sql = "SELECT c.Name AS City, co.Name AS Country, c.Population " +
"FROM city c " +
"JOIN country co ON c.CountryCode = co.Code AND c.ID = co.Capital " +
"WHERE co.Continent = '" + continentName + "' " +
"ORDER BY c.Population DESC";
if (limit != null && limit > 0) {
sql += " LIMIT " + limit;
}
List<Map<String, Object>> cities = jdbcTemplate.queryForList(sql);
model.addAttribute("cities", cities);
return "CapitalCitiesResult";
}

@GetMapping("/region-capital-cities")
public String RegionCapitalCitiesForm(Model model) {
    model.addAttribute("Country", new Country());
    return "RegionCapitalCitiesForm";
}

@PostMapping("/region-capital-cities")
public String RegionCapitalCitiesForm(@ModelAttribute Country country, Model model) {
    String regionName = country.getName();
    Integer limit = country.getLimit();
    String sql = "SELECT c.Name AS City, co.Name AS Country, co.Region, c.Population " +
            "FROM city c " +
            "JOIN country co ON c.CountryCode = co.Code AND c.ID = co.Capital " +
            "WHERE co.Region = '" + regionName + "' " +
            "ORDER BY c.Population DESC";
    if (limit != null && limit > 0) {
        sql += " LIMIT " + limit;
    }
    List<Map<String, Object>> cities = jdbcTemplate.queryForList(sql);
    model.addAttribute("cities", cities);
    return "CapitalCitiesResult";
}
  

}

