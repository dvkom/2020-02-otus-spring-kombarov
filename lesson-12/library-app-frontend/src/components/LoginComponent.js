import React from 'react';
import AuthService from '../services/AuthService';

export default class LoginComponent extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: '',
      hasLoginFailed: false
    };

    this.handleChange = this.handleChange.bind(this);
    this.loginClicked = this.loginClicked.bind(this)
  }

  componentDidMount() {
    if (AuthService.isUserLoggedIn())
      this.props.history.push(`/app`);
  }

  handleChange(event) {
    this.setState(
        {
          [event.target.name]: event.target.value
        }
    )
  }

  loginClicked() {
    AuthService
        .executeBasicAuthenticationService(this.state.username, this.state.password)
        .then(() => {
          AuthService.registerSuccessfulLogin(this.state.username, this.state.password)
          this.props.history.push(`/app`)
        }).catch(() => {
      this.setState({hasLoginFailed: true})
    })
  }

  render() {
    return (
        <div>
          <h2>Для входа в систему введите логин и пароль</h2>
          <div className="container" >
            {this.state.hasLoginFailed && <div className="alert alert-warning">Неверные логин и (или) пароль</div>}
            Логин: <input type="text" name="username" value={this.state.username}
                          style={{margin: "10px"}} onChange={this.handleChange}/>
            Пароль: <input type="password" name="password" value={this.state.password}
                           style={{margin: "10px"}} onChange={this.handleChange}/>
            <button className="btn btn-success" style={{margin: "5px"}} onClick={this.loginClicked}>Войти</button>
          </div>
          <div style={{color: 'orange'}}>
          <label style={{marginTop: "40px"}}>Подсказка: </label><br/>
          <label>Bob password1 </label><br/>
          <label>Alice password2 </label><br/>
          <label>Eve password3 </label><br/>
          </div>
        </div>
    )
  }
};