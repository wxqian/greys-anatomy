<template>
  <dynamic-table
    ref="tableRef"
    :columns="columns"
    :get-list-func="getApps"
    rowKey="id"
    :row-selection="rowSelection"
    @expand="expand"
  />
</template>

<script lang="ts">
import { defineComponent, reactive, toRefs, createVNode, computed, ref } from 'vue'
import { Modal } from 'ant-design-vue'
import { QuestionCircleOutlined } from '@ant-design/icons-vue'
import { DynamicTable } from '@/components/dynamic-table'
import { getApps } from '@/api/app/term'
import OperateModal from './term-modal.vue'
import { columns } from './columns'
import { useExpandLoading } from '@/components/dynamic-table/hooks'
import { useCreateModal } from '@/hooks'

export default defineComponent({
  name: 'AppTerms',
  components: {
    DynamicTable
  },
  setup() {
    const tableRef = ref<InstanceType<typeof DynamicTable>>()
    const itemRefs = ref({})

    const state = reactive({
      expandedRowKeys: [] as string[],
      tableLoading: false,
      rowSelection: {
        onChange: (selectedRowKeys, selectedRows) => {
          state.rowSelection.selectedRowKeys = selectedRowKeys
        },
        selectedRowKeys: []
      }
    })

    // 删除多项
    const deleteItems = () => {
      Modal.confirm({
        title: '提示',
        icon: createVNode(QuestionCircleOutlined),
        content: '您确定要删除所有选中吗？',
        onOk: async () => {
          state.rowSelection.selectedRowKeys = []
        }
      })
    }
    // 添加资源
    const addItem = () => {
      useCreateModal(OperateModal, {
        callback: () => {
          tableRef.value?.refreshTableData()
        }
      })
    }

    // 是否禁用批量删除按钮
    const isDisabled = computed(() => state.rowSelection.selectedRowKeys.length == 0)

    // 点击展开图标
    const expand = async (expanded, record) => {
      const expandItemEl = itemRefs.value[record.id]
      // 点击展开图标loading
      const result = await useExpandLoading({
        expanded,
        record,
        expandItemEl,
        asyncFunc: getApps,
        params: { id: record.id, limit: 100 }
      })
      if (result?.data) {
        record.children = result.data
      }
    }

    return {
      ...toRefs(state),
      columns,
      itemRefs,
      tableRef,
      isDisabled,
      expand,
      getApps,
      addItem,
      deleteItems
    }
  }
})
</script>

<style lang="scss">
.loading-icon {
  border: none;

  &.ant-table-row-expanded::after {
    content: none;
  }
}
</style>
