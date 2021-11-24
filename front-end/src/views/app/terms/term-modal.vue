<template>
  <a-modal
    v-model:visible="visible"
    :width="800"
    :title="fields.ip"
    :confirm-loading="confirmLoading"
    :afterClose="remove"
    :okText="关闭"
    @ok="handleOk"
  >
    <div id="xterm" class="xterm" style="min-height: 400px"></div>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, reactive, toRefs, onMounted, ref } from 'vue'
import 'xterm/css/xterm.css'
import { Terminal } from 'xterm'
import { FitAddon } from 'xterm-addon-fit'
import { AttachAddon } from 'xterm-addon-attach'

const prefix = 'ws://127.0.0.1:8085/socket'

interface IState {
  visible: boolean
  confirmLoading: boolean
}

export default defineComponent({
  name: 'OperateModal',
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

    let socket, term

    const state: IState = reactive({
      visible: true,
      confirmLoading: false
    })

    onMounted(async () => {
      initSocket(props.fields.ip)
    })

    const initTerm = () => {
      term = new Terminal({
        fontSize: 14,
        cursorBlink: true,
        cursorStyle: 'underline'
        // theme: {
        //   foreground: '#7e9192', //字体
        //   background: '#002833', //背景色
        //   cursor: 'help' //设置光标
        // }
      })
      const attachAddon = new AttachAddon(socket)
      const fitAddon = new FitAddon()
      term.loadAddon(attachAddon)
      term.loadAddon(fitAddon)
      const xtermDiv = document.getElementById('xterm') as HTMLElement
      term.open(xtermDiv)
      fitAddon.fit()
      term.focus()
      term.write('welcome!')
      prompt()
    }

    // 换行并输入起始符“$”
    const prompt = () => {
      term.write('\r\n$ ')
    }

    const initSocket = (ip: string) => {
      socket = new WebSocket([prefix, ip].join('/'))
      socketOnClose()
      socketOnOpen()
      socketOnError()
    }

    const socketOnOpen = () => {
      socket.onopen = () => {
        // 链接成功后
        initTerm()
      }
    }
    const socketOnClose = () => {
      socket.onclose = () => {
        // console.log('close socket')
      }
    }
    const socketOnError = () => {
      socket.onerror = () => {
        // console.log('socket 链接失败')
      }
    }

    const beforeDestroy = () => {
      socket.close()
      term.dispose()
    }

    const handleOk = async (e) => {
      e.preventDefault()
      state.confirmLoading = true
      try {
        beforeDestroy
        state.visible = false
        props?.callback?.()
      } catch (error) {
        console.log('error', error)
        state.confirmLoading = false
      }
    }

    return {
      ...toRefs(state),
      prefix,
      labelCol: { span: 4 },
      wrapperCol: { span: 20 },
      beforeDestroy,
      handleOk
    }
  }
})
</script>

<style scoped></style>
