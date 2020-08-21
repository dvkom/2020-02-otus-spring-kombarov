import React, {Component} from 'react';
import './Login.css';
import {ACCESS_TOKEN} from '../services/ApiService';
import {login} from '../services/ApiService';
import {Link, Redirect} from 'react-router-dom'
import Alert from 'react-s-alert';

class Login extends Component {

  render() {
    if (this.props.authenticated) {
      return <Redirect
          to={{
            pathname: "/",
            state: {from: this.props.location}
          }}/>;
    }

    return (
        <div className="login-container">
          <div className="login-content">
            <h1 className="login-title">Login to Exploit Finder</h1>
            <LoginForm {...this.props} />
            <span className="signup-link">New user? <Link to="/signup">Sign up!</Link></span>
          </div>
        </div>
    );
  }
}


class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: ''
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    const inputName = target.name;
    const inputValue = target.value;

    this.setState({
      [inputName]: inputValue
    });
  }

  handleSubmit(event) {
    event.preventDefault();

    const loginRequest = Object.assign({}, this.state);

    login(loginRequest)
        .then(response => {
          localStorage.setItem(ACCESS_TOKEN, response.accessToken);
          this.props.updateAuthStatus();
          this.props.history.push("/");
        }).catch(error => {
      Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
    });
  }

  render() {
    return (
        <form onSubmit={this.handleSubmit}>
          <div className="form-item">
            <input type="email" name="email"
                   className="form-control" placeholder="Email"
                   value={this.state.email} onChange={this.handleInputChange} required/>
          </div>
          <div className="form-item">
            <input type="password" name="password"
                   className="form-control" placeholder="Password"
                   value={this.state.password} onChange={this.handleInputChange} required/>
          </div>
          <div className="form-item">
            <button type="submit" className="btn btn-block btn-primary">Login</button>
          </div>
        </form>
    );
  }
}

export default Login
