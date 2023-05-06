package py.com.demo.authorizer.repository.implementation;

import java.sql.CallableStatement;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;
import py.com.demo.authorizer.entity.reponse.LocalidadResponse;
import py.com.demo.authorizer.repository.query.CreditCardQuery;
import py.com.demo.authorizer.utils.MSException;
import py.com.demo.authorizer.entity.QueryExampleResponse;
import py.com.demo.authorizer.entity.request.AuthorizationRequest;
import py.com.demo.authorizer.exceptions.AlfaMsException;
import py.com.demo.authorizer.exceptions.AlfaMsTypeException;
import py.com.demo.authorizer.repository.AuthorizerRepository;
import py.com.demo.authorizer.rowmapper.QueryExampleRowMapper;

@Repository
@Log4j2
public class AuthorizerRepositoryImpl extends JdbcDaoSupport implements AuthorizerRepository {

    @Autowired
    public AuthorizerRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        setDataSource(dataSource);
        setJdbcTemplate(jdbcTemplate);
    }

    private JdbcTemplate getCheckedJdbcTemplate() throws MSException {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new MSException("No se pudo crear la conexión a la base de datos.", 001);
        }
        return jdbcTemplate;
    }

    public String spExample() throws MSException {
        log.info("[spExample] - {} " +
                        "\n {}: {}",
                CreditCardQuery.GP_CALL_EXAMPLE,
                "tablename", "0");
        try {
            return getCheckedJdbcTemplate().execute(connection -> {
                CallableStatement statement = connection.prepareCall(CreditCardQuery.GP_CALL_EXAMPLE);
                statement.setString("tablename", "0");
                statement.registerOutParameter("param_name", Types.NUMERIC);
                statement.registerOutParameter("param_name", Types.NUMERIC);
                statement.registerOutParameter("param_name", Types.NUMERIC);
                statement.registerOutParameter("param_name", Types.VARCHAR);

                return statement;

            }, (CallableStatementCallback<String>) callableStatement -> {
                callableStatement.execute();

                return callableStatement.getString("p_men_error");
            });

        } catch (Exception e) {
            log.error("Error al llamar al procedimiento", e);
            throw new MSException("DataBaseExcepcion", 002, "Ocurrio un error con la base de datos");
        }
    }

    public QueryExampleResponse queryExample() throws MSException {
        log.info("[queryExample] - pkQuery: {}", "el o los parametros key");
        try {
            //en caso de que tenga parametros
            //int[] types = {Types.VARCHAR};
            //Object[] params = {personCode};

            return getCheckedJdbcTemplate().query(CreditCardQuery.QUERY_CALL_EXAMPLE/*, params, types*/, new QueryExampleRowMapper()).get(0);
        } catch (DataAccessException e) {
            log.error("Ocurrio un error con la BD", e);
            throw new MSException("DataBaseExcepcion", 002, "Ocurrio un error con la base de datos");
        }
    }

    public String createAuthorization(AuthorizationRequest request) throws AlfaMsException {
        log.info("[callProcedure] - {} " +
                        "\n {}: {}",
                CreditCardQuery.UP_CREATE_AUTHORIZATION,
                "p_card_token", request.getToken());
        try {
            return getCheckedJdbcTemplate().execute(connection -> {
                CallableStatement statement = connection.prepareCall(CreditCardQuery.GP_CALL_EXAMPLE);
                statement.setString("tablename", "0");
                statement.registerOutParameter("param_name", Types.NUMERIC);
                statement.registerOutParameter("param_name", Types.NUMERIC);
                statement.registerOutParameter("param_name", Types.NUMERIC);
                statement.registerOutParameter("param_name", Types.VARCHAR);

                return statement;

            }, (CallableStatementCallback<String>) callableStatement -> {
                callableStatement.execute();
                return callableStatement.getString("p_men_error");
            });

        } catch (Exception e) {
            log.error("Error al llamar al ", e);
            throw new AlfaMsException(AlfaMsTypeException.APPLICATION, "4052", "Ocurrió un problema al procesar la transacción");
        }
    }

    public LocalidadResponse getAllData() throws AlfaMsException {
        log.info("[getAllData]");
        try {

        }catch (Exception e){
            log.error("Error al llamar al ", e);
            throw new AlfaMsException(AlfaMsTypeException.APPLICATION, "4052", "Ocurrió un problema al procesar la transacción");
        }
        return null;
    }
}
