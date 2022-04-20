package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeJdbcDao implements EmployeeDao{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, rowNum) ->
            new Employee(rs.getString("name"),
                    rs.getString("location"),
                    rs.getLong("employeeID"),
                    rs.getString("availability"),
                    rs.getLong("experienceYears"),
                    rs.getString("abilities"));

    @Autowired
    public EmployeeJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("Employee");
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        List<Employee> query = jdbcTemplate.query("SELECT * FROM employee WHERE employeeID = ?", new Object[] {id}, EMPLOYEE_ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Optional<List<Employee>> getEmployees() {
        List<Employee> query = jdbcTemplate.query("SELECT * FROM employee", new Object[] {}, EMPLOYEE_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public Optional<List<Employee>> getFilteredEmployees(long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities) {
        System.out.println("en jdbcDao para filtered");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM employee where ");
        if (experienceYears > 0) {
            stringBuilder.append("experienceYears = " + experienceYears);
            stringBuilder.append("and ");
        }
        if (location != null) {
            stringBuilder.append("availability like \'%" + location + "%\'");
            stringBuilder.append("and");
        }
        // TODO Aca iria lo mismo pero para experienceList
        for (String av : availability) {
            stringBuilder.append("availability like \'%" + av + "%\'");
            stringBuilder.append("and ");
        }
        for (String ability : abilities) {
            stringBuilder.append("abilities like \'%" + ability + "%\'");
            stringBuilder.append("and ");
        }
        stringBuilder.setLength(stringBuilder.length() - 4);
        List<Employee> query = jdbcTemplate.query(stringBuilder.toString(), new Object[] {}, EMPLOYEE_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public Employee create(long id, String name, String location, String availability, long experienceYears, String abilities) {
       final Map<String, Object> employeeData = new HashMap<>();
       employeeData.put("employeeID", id);
       employeeData.put("name", name);
       employeeData.put("location", location);
       employeeData.put("availability", availability);
       employeeData.put("experienceYears", experienceYears);
       employeeData.put("abilities", abilities);

       jdbcInsert.execute(employeeData);
       return new Employee(name, location, id, availability, experienceYears, abilities);
    }
}
