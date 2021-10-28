import React,{Component} from 'react';
import './App.css';
import {Route,Switch} from 'react-router-dom'
import './assets/font/iconfont.css'
import Login from './routes/Login/index'
import PrivateRoute from './routes/PrivateRoute';
import Index from './routes/Index';

class App extends Component{
  render(){
    return (
      <Switch>
        <Route path='/login' component={Login}/>
        {<PrivateRoute path='/' component={Index}/>}
      </Switch>
    )
  }
}

export default App;