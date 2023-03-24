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
public class CityController {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/world-cities")
  public String WorldCityForm(Model model) {
    model.addAttribute("City", new City());
    return "WorldCitiesForm";
  }

  @PostMapping("/world-cities")
  public String continentCitySubmit(@ModelAttribute City city, Model model) {
      Integer limit = city.getLimit();
      String sql = "SELECT c.Name, c.District, c.Population, co.Name as CountryName " +
              "FROM city c " +
              "JOIN country co ON c.CountryCode = co.Code " +
              "ORDER BY c.Population DESC";
      if (limit != null && limit > 0) {
          sql += " LIMIT " + limit;
      }
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
      System.out.println(result);
      model.addAttribute("cities", result); // Change "result" to "cities"
      return "CitiesResult";
  }
  

  @GetMapping("/continent-cities")
  public String ContinentCityForm(Model model) {
    model.addAttribute("Country", new Country());
    return "ContinentCitiesForm";
  }

  @PostMapping("/continent-cities")
  public String continentSubmit(@ModelAttribute Country Country, Model model) {
      String name = Country.getName();
      Integer limit = Country.getLimit();
      String sql = "SELECT city.Name, city.District, city.Population, country.Name AS CountryName " +
              "FROM city " +
              "JOIN country ON city.CountryCode = country.Code " +
              "WHERE country.Continent = '" + name + "' " +
              "ORDER BY city.Population DESC";
      if (limit != null) {
          sql += " LIMIT " + limit;
      }
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
      System.out.println(result);
      model.addAttribute("cities", result);
      return "CitiesResult";
  }

  @GetMapping("/region-cities")
  public String RegionCityForm(Model model) {
    model.addAttribute("Country", new Country());
    return "RegionCitiesForm";
  }

  @PostMapping("/region-cities")
  public String regionSubmit(@ModelAttribute Country Country, Model model) {
      String name = Country.getName();
      Integer limit = Country.getLimit();
      String sql = "SELECT city.Name, city.District, city.Population, country.Name AS CountryName " +
              "FROM city " +
              "JOIN country ON city.CountryCode = country.Code " +
              "WHERE country.Region = '" + name + "' " +
              "ORDER BY city.Population DESC";
      if (limit != null) {
          sql += " LIMIT " + limit;
      }
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
      System.out.println(result);
      model.addAttribute("cities", result);
      return "CitiesResult";
  }

  @GetMapping("/country-cities")
  public String CountryCityForm(Model model) {
    model.addAttribute("Country", new Country());
    return "CountryCitiesForm";
  }

  @PostMapping("/country-cities")
  public String countrySubmit(@ModelAttribute Country Country, Model model) {
      String name = Country.getName();
      Integer limit = Country.getLimit();
      String sql = "SELECT city.Name, city.District, city.Population, country.Name AS CountryName " +
              "FROM city " +
              "JOIN country ON city.CountryCode = country.Code " +
              "WHERE country.Name = '" + name + "' " +
              "ORDER BY city.Population DESC";
      if (limit != null) {
          sql += " LIMIT " + limit;
      }
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
      System.out.println(result);
      model.addAttribute("cities", result);
      return "CitiesResult";
  }

  @GetMapping("/district-cities")
  public String DistrictCityForm(Model model) {
    model.addAttribute("Country", new Country());
    return "DistrictCitiesForm";
  }

  @PostMapping("/district-cities")
  public String districtSubmit(@ModelAttribute Country Country, Model model) {
      String name = Country.getName();
      Integer limit = Country.getLimit();
      String sql = "SELECT city.Name, city.District, city.Population, country.Name AS CountryName " +
              "FROM city " +
              "JOIN country ON city.CountryCode = country.Code " +
              "WHERE city.District = '" + name + "' " +
              "ORDER BY city.Population DESC";
      if (limit != null) {
          sql += " LIMIT " + limit;
      }
      List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
      System.out.println(result);
      model.addAttribute("cities", result);
      return "CitiesResult";
  }
  

}
