
package local.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

//동기호출
//@FeignClient(name="delivery", url="http://localhost:8082")
@FeignClient(name="delivery", url="${api.url.delivery}")
public interface CancellationService {

    @RequestMapping(method= RequestMethod.POST, path="/cancellations")
    public void cancel(@RequestBody Cancellation cancellation);

}