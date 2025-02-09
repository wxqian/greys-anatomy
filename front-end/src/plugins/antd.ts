import type { App } from 'vue'

import { AButton } from '@/components/button/index'

import {
  Modal,
  Table,
  Menu,
  Input,
  Form,
  Card,
  Checkbox,
  Radio,
  Col,
  Row,
  Select,
  DatePicker,
  TreeSelect
} from 'ant-design-vue'

import 'ant-design-vue/dist/antd.css'

export function setupAntd(app: App<Element>) {
  app.component('AButton', AButton)

  app
    .use(Form)
    .use(Input)
    .use(Modal)
    .use(Table)
    .use(Menu)
    .use(Card)
    .use(Checkbox)
    .use(Radio)
    .use(Col)
    .use(Row)
    .use(Select)
    .use(DatePicker)
    .use(TreeSelect)
}
