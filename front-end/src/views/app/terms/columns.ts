import { useCreateModal } from '@/hooks'
import OperateModal from './term-modal.vue'

export const columns: TableColumn[] = [
  // 资源管理
  {
    title: 'ip',
    dataIndex: 'ip'
  },
  {
    title: '端口',
    dataIndex: 'port'
  },
  {
    title: '状态',
    dataIndex: 'status',
    slots: {
      customRender: 'status'
    },
    slotsType: 'format',
    slotsFunc: (val) => {
      return val == 0 ? '未连接' : '已连接'
    } // 格式化时间
  },
  {
    title: '操作',
    dataIndex: 'action',
    width: 200,
    slots: {
      customRender: 'action'
    },
    actions: [
      {
        type: 'button', // 控制类型，默认为a,可选： select | button | text
        text: 'terminal',
        permission: {
          // 权限
          action: 'update',
          effect: 'disabled'
        },
        props: {
          type: 'warning' // 按钮类型
        },
        func: ({ record }, callback) =>
          useCreateModal(OperateModal, {
            // 点击删除的回调
            fields: record,
            callback
          })
      }
    ]
  }
]
