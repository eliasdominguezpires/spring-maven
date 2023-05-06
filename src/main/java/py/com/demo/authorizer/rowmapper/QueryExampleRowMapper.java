package py.com.demo.authorizer.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import py.com.demo.authorizer.entity.QueryExampleResponse;


public class QueryExampleRowMapper implements RowMapper<QueryExampleResponse>{

    @Override
    public QueryExampleResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new QueryExampleResponse(rs.getString("info_cliente"));
    }
    
}
