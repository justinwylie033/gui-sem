package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class CityService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<City> getCities(Integer ID, String Name, String CountryCode, String District, Integer Population, Integer limit) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM city");

        List<String> whereClauses = new ArrayList<>();
        if (ID != null) {
            whereClauses.add("ID = '" + ID + "'");
        }
        if (Name != null) {
            whereClauses.add("Name = '" + Name + "'");
        }
        if (CountryCode != null) {
            whereClauses.add("CountryCode = '" + CountryCode + "'");
        }
        if (District != null) {
            whereClauses.add("District = '" + District + "'");
        }
        if (Population != null) {
            whereClauses.add("Population = " + Population);
        }

        if (!whereClauses.isEmpty()) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", whereClauses));
        }

        queryBuilder.append(" ORDER BY Population DESC");

        if (limit != null) {
            queryBuilder.append(" LIMIT " + limit);
        }

        return jdbcTemplate.query(queryBuilder.toString(), new CityRowMapper());
    }
}
