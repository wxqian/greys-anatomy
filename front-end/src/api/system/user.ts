import http from '@/utils/http/axios'
import { BasicResponseModel } from '@/api/BasicResponseModel'
import { LoginParams, LoginResultModel } from './model/userModel'
import { ContentTypeEnum } from '@/enums/httpEnum'

enum Api {
  login = '/system/login',
  logout = '/system/logout'
}

/**
 * @description: 获取用户信息
 */
export function getUserInfo() {
  return http.request(
    {
      url: Api.login,
      method: 'POST'
    },
    {
      isTransformRequestResult: false
    }
  )
}

/**
 * @description: 用户登录
 */
export function login(params: LoginParams) {
  return http.request<BasicResponseModel<LoginResultModel>>(
    {
      url: Api.login,
      method: 'POST',
      headers: {
        'Content-Type': ContentTypeEnum.JSON
      },
      params
    },
    {
      isTransformRequestResult: false
    }
  )
}

/**
 * @description: 用户修改密码
 */
export function changePassword(params, uid) {
  return http.request(
    {
      url: `/user/u${uid}/changepw`,
      method: 'POST',
      params
    },
    {
      isTransformRequestResult: false
    }
  )
}

/**
 * @description: 用户登出
 */
export function logout(params) {
  return http.request({
    url: Api.logout,
    method: 'POST',
    params
  })
}
