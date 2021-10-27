import '@babel/polyfill'
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import { HashRouter } from 'react-router-dom'
import { Provider } from 'mobx-react'
import store from './store'

ReactDOM.render(
  <HashRouter>
    <Provider {...store}>
      <App />
    </Provider>  
  </HashRouter>,
  document.getElementById('root')
);
registerServiceWorker();