package ru.otus.highload.socialbackend.feature.clickhouse;


import feign.Response;
import org.springframework.web.bind.annotation.*;

/**
 * https://github.com/VKCOM/kittenhouse
 */
@RequestMapping("/")
public interface KittenhouseApiClient {

    // http://localhost:13338/?table=internal_logs_buffer(time,server,port)
    @PostMapping()
    Response insert(@RequestBody String bodyParams, @RequestParam("table") String table);

    //http://127.0.0.1:13338/?query=SELECT%20hostName()%20FROM%20system.one
    @GetMapping()
    Response select(@RequestParam("query") String query);
}
