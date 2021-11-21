import { BasicPageParams } from '@/api/BasicResponseModel'

export interface AccessItem {
  id: number
  status: number
  createTime: number
  modifyTime: number
  name: string
  icon: string
  url: string
  parentId: number
  sorts: number
  desc: string | null
  children?: boolean
}

export interface AccessResultModel extends BasicPageParams {
  data: AccessItem[]
}

export interface ModuleItem {
  id: number
  value: string
  pId: string
  title: string
}
