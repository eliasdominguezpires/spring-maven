package py.com.demo.authorizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoResponse {

    private String profile;
    private String dataBaseUrl;
    private String databaseUsername;
    private String gpUrl;
    
}
