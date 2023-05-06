package py.com.demo.authorizer.client.impl;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import py.com.demo.authorizer.entity.SystemInfoResponse;

@FeignClient(name = "GlobalCreditClient", url = "${global.credit.url}")
public interface GlobalCreditClientImpl {
    @GetMapping(value = "/system-info", consumes = "application/json")
    SystemInfoResponse getSystemInfo();
}
