<template>
  <a-modal
    v-model:visible="visible"
    :width="600"
    :title="Number.isInteger(fields.id) ? '编辑权限' : '新增权限'"
    :confirm-loading="confirmLoading"
    :afterClose="remove"
    @ok="handleOk"
  >
    <a-form ref="formRef" :model="modelRef" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="权限名称" :rules="rules.name" name="name">
        <a-input
          v-model:value="modelRef.name"
          :disabled="Number.isInteger(fields.id)"
          placeholder="请输入权限名称"
        />
      </a-form-item>
      <a-form-item label="权限描述" name="desc">
        <a-input v-model:value="modelRef.desc" placeholder="请输入权限描述" />
      </a-form-item>
      <a-form-item label="父权限" name="parentId">
        <a-tree-select
          v-model:value="modelRef.parentId"
          tree-data-simple-mode
          style="width: 100%"
          :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
          :tree-data="moduleList"
          placeholder="请选择父权限"
        />
      </a-form-item>
      <a-form-item
        label="文件路径"
        name="viewPath"
        :rules="[
          {
            required: modelRef.parentId != undefined,
            message: '请输入页面对应的文件路径'
          }
        ]"
      >
        <a-select v-model:value="modelRef.viewPath" placeholder="请选择页面对应的文件路径">
          <template v-for="(comp, path) in constantRouterComponents" :key="path">
            <a-select-option :value="path"> {{ path }} </a-select-option>
          </template>
        </a-select>
      </a-form-item>
      <a-form-item label="路径" :rules="rules.url" name="url">
        <a-input v-model:value="modelRef.url" placeholder="请输入路径" />
      </a-form-item>
      <a-form-item label="小图标">
        <a-input v-model:value="modelRef.icon" placeholder="小图标" />
        <a :href="`${prefix}#/icons`" target="_blank">可选图标</a>
      </a-form-item>
      <a-form-item label="排序">
        <a-input-number v-model:value="modelRef.sorts" :min="1" placeholder="排序" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, reactive, toRefs, onMounted, ref } from 'vue'
import { Modal, Form, InputNumber, Input, Select, TreeSelect } from 'ant-design-vue'
import { constantRouterComponents } from '@/router/modules'
import { ModuleItem } from '@/api/system/access/AccessModel'
import { postAdminAccess, getAdminAccessModule, patchAdminAccess } from '@/api/system/access'
const prefix = process.env.BASE_URL

interface IState {
  visible: boolean
  confirmLoading: boolean
  moduleList: ModuleItem[]
}

export default defineComponent({
  name: 'OperateModal',
  components: {
    [Modal.name]: Modal,
    [Form.name]: Form,
    [Form.Item.name]: Form.Item,
    [InputNumber.name]: InputNumber,
    [Input.name]: Input,
    [Select.name]: Select,
    ASelectOption: Select.Option,
    [TreeSelect.name]: TreeSelect
  },
  props: {
    remove: {
      // 移除模态框
      type: Function
    },
    fields: {
      type: Object,
      default: () => ({})
    },
    callback: {
      type: Function
    }
  },
  setup(props) {
    const formRef = ref<any>(null)

    const state: IState = reactive({
      visible: true,
      confirmLoading: false,
      moduleList: []
    })
    const modelRef = reactive({
      id: undefined,
      name: '',
      desc: '',
      viewPath: '',
      parentId: undefined,
      url: '',
      icon: '',
      sorts: 1
    })

    const rules = {
      name: [
        {
          required: true,
          message: '权限名称不能为空'
        }
      ],
      url: [
        {
          required: true,
          message: '请输入url地址'
        }
      ]
    }

    onMounted(async () => {
      // 获取模块列表
      state.moduleList = await getAdminAccessModule()
    })

    // 如果有moduleId,则为编辑操作
    if (props.fields.id) {
      Object.keys(modelRef).forEach((key) => (modelRef[key] = props.fields[key]))
      if (modelRef.parentId == -1) {
        modelRef.parentId = undefined
      }
    }

    const handleOk = async (e) => {
      e.preventDefault()
      state.confirmLoading = true
      try {
        await formRef.value.validate()
        const id = props.fields.id
        const params = { ...modelRef }
        params.viewPath ??= ''
        id && Reflect.deleteProperty(params, 'type')
        await (id ? patchAdminAccess(id, params) : postAdminAccess(params)).finally(
          () => (state.confirmLoading = false)
        )
        state.visible = false
        props?.callback?.()
      } catch (error) {
        console.log('error', error)
        state.confirmLoading = false
      }
    }

    return {
      ...toRefs(state),
      formRef,
      rules,
      prefix,
      labelCol: { span: 4 },
      wrapperCol: { span: 20 },
      modelRef,
      constantRouterComponents,
      handleOk
    }
  }
})
</script>

<style scoped></style>
