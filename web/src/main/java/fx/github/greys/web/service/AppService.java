package fx.github.greys.web.service;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.vo.AppVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AppService {

    public GreysResponse<Page<AppVo>> listApps(Pageable pageable) {
        GreysResponse<Page<AppVo>> result = GreysResponse.createSuccess();
        try {
            List<AppVo> appVos = genAppList();
            Page<AppVo> appVoPage = listConvertToPage(appVos, pageable);
            result.setResult(appVoPage);
        } catch (Exception e) {
            log.error("AppService listApps occurs exception.", e);
            result = GreysResponse.createError();
        }
        return result;
    }

    //todo app 获取修改 2021-11-24
    private List<AppVo> genAppList() {
        List<AppVo> appVos = new ArrayList<>();
        AppVo appVo = AppVo.builder()
                .id(1).ip("127.0.0.1").port(222).status(1)
                .build();
        appVos.add(appVo);
        return appVos;
    }

    public <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

}
