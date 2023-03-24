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
public class CountryController {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @GetMapping("/world-countries")
  public String WorldCountriesForm(Model model) {
    model.addAttribute("Country", new Country());
    return "WorldCountriesForm";
  }

  @PostMapping("/world-countries")
  public String continentCitySubmit(@ModelAttribute Country country, Model model) {
    Integer limit = country.getLimit();
    String sql = "SELECT c.Code, c.Name, co.Continent, co.Region, c.Population, ci.Name as Capital " +
        "FROM country c " +
        "JOIN country co ON c.Code = co.Code " +
        "LEFT JOIN city ci ON c.Capital = ci.ID " +
        "ORDER BY c.Population DESC";
    if (limit != null && limit > 0) {
      sql += " LIMIT " + limit;
    }
    List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
    System.out.println(result);
    model.addAttribute("countries", result);
    return "CountriesResult";
  }

  @GetMapping("/continent-countries")
  public String ContinentCountryForm(Model model) {
    model.addAttribute("Country", new Country());
    return "ContinentCountriesForm";
  }

  @PostMapping("/continent-countries")
  public String continentCountrySubmit(@ModelAttribute Country Country, Model model) {
    String name = Country.getName();
    Integer limit = Country.getLimit();
    String sql = "SELECT c.Code, c.Name, co.Continent, co.Region, c.Population, ci.Name as Capital " +
        "FROM country c " +
        "JOIN country co ON c.Code = co.Code " +
        "LEFT JOIN city ci ON c.Capital = ci.ID " +
        "WHERE c.Continent = '" + name + "' " +
        "ORDER BY c.Population DESC";
    if (limit != null) {
      sql += " LIMIT " + limit;
    }
    List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
    System.out.println(result);
    model.addAttribute("countries", result);
    return "CountriesResult";
  }

  @GetMapping("/region-countries")
  public String RegionCountryForm(Model model) {
    model.addAttribute("Country", new Country());
    return "RegionCountriesForm";
  }

  @PostMapping("/region-countries")
  public String regionSubmit(@ModelAttribute Country Country, Model model) {
    String name = Country.getName();
    Integer limit = Country.getLimit();
    String sql = "SELECT c.Code, c.Name, co.Continent, co.Region, c.Population, ci.Name as Capital " +
        "FROM country c " +
        "JOIN country co ON c.Code = co.Code " +
        "LEFT JOIN city ci ON c.Capital = ci.ID " +
        "WHERE c.Region = '" + name + "' " +
        "ORDER BY c.Population DESC";
    if (limit != null) {
      sql += " LIMIT " + limit;
    }
    List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
    System.out.println(result);
    model.addAttribute("countries", result);
    return "CountriesResult";
  }

}
