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
public class PopulationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
  
    @GetMapping("/population-by-continent")
    public String getPopulationByContinent(Model model) {
      model.addAttribute("Country", new Country());
      return "ContinentPopulationForm";
    }
  
    @PostMapping("/population-by-continent")
    public String continentCitySubmit(@ModelAttribute Country country, Model model) {
        String regionName = country.getName();
        String sql = "SELECT co.Continent, co.Region, c.Name as Country, " +
            "SUM(c.Population) as TotalPopulation, " +
            "SUM(ci.Population) as CityPopulation, " +
            "SUM(c.Population) - SUM(ci.Population) as RuralPopulation, " +
            "CONCAT(FORMAT(SUM(ci.Population) * 100 / SUM(c.Population), 2), '%') as CityPopulationPercentage, " +
            "CONCAT(FORMAT((SUM(c.Population) - SUM(ci.Population)) * 100 / SUM(c.Population), 2), '%') as RuralPopulationPercentage " +
            "FROM country c " +
            "JOIN country co ON c.Code = co.Code " +
            "LEFT JOIN city ci ON c.Capital = ci.ID " +
            "WHERE co.Continent = ? " +
            "GROUP BY co.Continent, co.Region, c.Name " +
            "ORDER BY co.Continent, co.Region, c.Name";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, regionName);
        model.addAttribute("regions", result);
        System.out.println(result);
        return "PopulationResult";
    }
}

