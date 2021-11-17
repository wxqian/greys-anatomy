export interface BasicResponseModel<T = any> {
  code: number
  message: string
  result: T
}
export interface BasicPageParams {
  pageNum: number
  pageSize: number
  total: number
}
