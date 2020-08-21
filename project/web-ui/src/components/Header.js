import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './Header.css';

class Header extends Component {
  render() {
    return (
        <header className="app-header">
          <div className="container">
              <Link to="/" className="app-title">Exploit Finder</Link>
            <div className="app-buttons">
              <nav className="app-nav">
                {this.props.authenticated ? (
                        <a onClick={this.props.onLogout}>Logout</a>
                ) : ''}
              </nav>
            </div>
          </div>
        </header>
    )
  }
}

export default Header;