import React from 'react';
import {
  Route,
  Switch
} from 'react-router-dom';
import Header from './Header';
import Login from './Login';
import Signup from './Signup';
import {isAuthenticated} from '../services/ApiService';
import {ACCESS_TOKEN} from '../services/ApiService';
import PrivateRoute from './PrivateRoute';
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import './App.css';
import Upload from "./Upload";

export default class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      authenticated: false,
      currentUser: null
    };

    this.updateAuthStatus = this.updateAuthStatus.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
  }

  updateAuthStatus() {
    this.setState({
      authenticated: isAuthenticated()
    });
  };

  handleLogout() {
    localStorage.removeItem(ACCESS_TOKEN);
    this.setState({
      authenticated: false,
      currentUser: null
    });
  }

  componentDidMount() {
    this.updateAuthStatus();
  }

  render() {

    return (
        <div className="app">
          <div className="app-top-box">
            <Header authenticated={this.state.authenticated} onLogout={this.handleLogout}/>
          </div>
          <div className="app-body">
            <Switch>
              <PrivateRoute exact path="/" authenticated={this.state.authenticated} component={Upload}/>
              <Route exact path="/login"
                     render={(props) => <Login updateAuthStatus={this.updateAuthStatus}
                                               authenticated={this.state.authenticated} {...props} />}/>
              <Route exact path="/signup"
                     render={(props) => <Signup authenticated={this.state.authenticated} {...props} />}/>
            </Switch>
          </div>
          <Alert stack={{limit: 3}}
                 timeout={3000}
                 position='top-right' effect='slide' offset={65}/>
        </div>
    );
  }
};