import http from '@/utils/http/axios'
import { RequestEnum, ContentTypeEnum } from '@/enums/httpEnum'
import { AppModel } from './AppModel'

enum Api {
  listApp = '/app/apps'
}

/**
 * 获取App列表
 * @param params
 */
export function getApps(params) {
  return http.request<AppModel>({
    url: Api.listApp,
    method: RequestEnum.GET,
    params
  })
}
