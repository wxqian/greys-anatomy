import React from 'react'
import {BrowserRouter as Router, Route, Switch,Link } from 'react-router-dom'

import About from '../services/About'
import Inbox from '../services/Inbox'
import Message from '../services/Message'
import App from '../App'

import { Menu } from 'antd';
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons';
const { SubMenu } = Menu;

function Routes (){
        return (
            <Router>
                <SubMenu key="sub1" icon={<UserOutlined />} title="subnav 1">
                    <Menu.Item key="1"><Link to="/about">dash</Link></Menu.Item>
                    <Menu.Item key="2">option2</Menu.Item>
                    <Menu.Item key="3">option3</Menu.Item>
                    <Menu.Item key="4">option4</Menu.Item>
                </SubMenu>
                <Switch>
                    <Route exact path="/about" ><About /></Route>
                    <Route exact path="/inbox" ><Inbox /></Route>
                    <Route exact path="/message"><Message /></Route>
                </Switch>
            </Router>
            
        )
    }



export default Routes;
