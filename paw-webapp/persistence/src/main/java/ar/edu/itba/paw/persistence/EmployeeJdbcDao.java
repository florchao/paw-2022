package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
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
            new Employee(rs.getString("name"), rs.getString("location"), rs.getLong("employeeID"), rs.getString("availability"));

    @Autowired
    public EmployeeJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("Employee")
                .usingGeneratedKeyColumns("employeeId");
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
    public Employee create(String name, String location, String availability) {
       final Map<String, Object> employeeData = new HashMap<>();
       employeeData.put("name", name);
       employeeData.put("location", location);
       employeeData.put("availability", availability);

       int employeeId = jdbcInsert.execute(employeeData);
       return new Employee(name, location, employeeId, availability);
    }
}
