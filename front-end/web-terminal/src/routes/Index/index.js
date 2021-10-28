import React from 'react'

class Index extends React.Component{
  state = {
    collapsed: false
  }

  toggle = () => {
    // console.log(this)  状态提升后，到底是谁调用的它
    this.setState({
      collapsed: !this.state.collapsed
    })
  }
  render() {
    // 设置Sider的minHeight可以使左右自适应对齐
    return (
      <div id='page'>
        page
      </div>
    );
  }
}
export default Index