package py.com.demo.authorizer.repository.query;

public class CreditCardQuery {
    
    public static final String GP_CALL_EXAMPLE = "{call schema.table(?,?,?,?,?)}";

    public static final String QUERY_CALL_EXAMPLE = "SELECT " +
            "schema.table('EXT_INF_REL_SOCIO',NULL,NULL,NULL,NULL) info_cliente FROM DUAL ";

    public static final String UP_CREATE_AUTHORIZATION = "{call schema.table(?,?,?,?,?)}";
}
