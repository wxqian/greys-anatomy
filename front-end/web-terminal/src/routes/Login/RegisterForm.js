import React from 'react'
import { Form, Input, message } from 'antd'
import { inject, observer } from 'mobx-react'
import { calculateWidth } from '../../utils/utils'
// import PromptBox from '../../components/PromptBox'


@inject('appStore')
class RegisterForm extends React.Component {
  state = {
    focusItem: -1
  }
  registerSubmit = (e) => {
    e.preventDefault()
    this.setState({
      focusItem: -1
    })
    this.props.form.validateFields((err, values) => {
      if (!err) {
        const users = this.props.appStore.users
        // 检测用户名是否存在
        const result = users.find(item => item.username === values.registerUsername)
        if (result) {
          this.props.form.setFields({
            registerUsername: {
              value: values.registerUsername,
              errors: [new Error('用户名已存在')]
            }
          })
          return
        }

        const obj = [...this.props.appStore.users, {
          username: values.registerUsername,
          password: values.registerPassword
        }]
        localStorage.setItem('users', JSON.stringify(obj))
        this.props.appStore.initUsers()
        message.success('注册成功')
      }
    })
  }
  gobackLogin = () => {
    this.props.switchShowBox('login')
    setTimeout(() => this.props.form.resetFields(), 500)
  }

  render () {
    const {focusItem} = this.state
    return (
      <div className={this.props.className}>
        <h3 className='title'>管理员注册</h3>
        <Form onSubmit={this.registerSubmit}>
          <Form.Item  rules={[
            {
              required: true,
              message: '请输入用户名',
            },
          ]}>
            {(
              <Input
                onFocus={() => this.setState({focusItem: 0})}
                onBlur={() => this.setState({focusItem: -1})}
                maxLength={16}
                placeholder='用户名'
                addonBefore={<span className='iconfont icon-User' style={focusItem === 0 ? styles.focus : {}}/>}/>
            )}
          </Form.Item>
          <Form.Item rules={[
            {
              required: true,
              message: '请输入用户名',
            },
          ]}>
            {(
              <Input
                onFocus={() => this.setState({focusItem: 1})}
                onBlur={() => this.setState({focusItem: -1})}
                type='password'
                maxLength={16}
                placeholder='密码'
                addonBefore={<span className='iconfont icon-suo1' style={focusItem === 1 ? styles.focus : {}}/>}/>
            )}
          </Form.Item>
          <Form.Item  rules={[
            {
              required: true,
              message: '请输入用户名',
            },
          ]}>
            {(
              <Input
                onFocus={() => this.setState({focusItem: 2})}
                onBlur={() => this.setState({focusItem: -1})}
                type='password'
                maxLength={16}
                placeholder='确认密码'
                addonBefore={<span className='iconfont icon-suo1' style={focusItem === 2 ? styles.focus : {}}/>}/>
            )}
          </Form.Item>
          <div className='bottom'>
            <input className='loginBtn' type="submit" value='注册'/>
            <span className='registerBtn' onClick={this.gobackLogin}>返回登录</span>
          </div>
        </Form>
        <div className='footer'>
          <div>欢迎登陆后台管理系统</div>
        </div>
      </div>
    )
  }
}

const styles = {
  focus: {
    width: '20px',
    opacity: 1
  },
}

export default RegisterForm