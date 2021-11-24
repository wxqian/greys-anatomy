package fx.github.greys.web.ctrl.app;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.service.AppService;
import fx.github.greys.web.vo.AppVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/app")
@RestController
@Slf4j
@Api(tags = "应用管理")
public class AppTermCtrl {

    @Autowired
    private AppService appService;

    @GetMapping("apps")
    @ApiOperation(value = "应用列表", notes = "应用列表")
    public GreysResponse<Page<AppVo>> listApps(@ApiParam("每页条数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                               @ApiParam("页数") @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return appService.listApps(pageable);
    }
}
