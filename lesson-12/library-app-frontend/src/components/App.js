import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';

import AuthenticatedRoute from './AuthenticatedRoute';
import Root from './Root';
import LoginComponent from './LoginComponent';

export default class App extends React.Component {
    render() {
        return (
            <div className="jumbotron">
                <div className="container">
                    <div className="col-sm-8 col-sm-offset-2">
                        <Router>
                            <div>
                                <Route path="/" exact component={LoginComponent} />
                                <Route path="/login" exact component={LoginComponent} />
                                <AuthenticatedRoute exact path="/app" component={Root} />
                                <AuthenticatedRoute path="/logout" exact component={LoginComponent} />
                            </div>
                        </Router>
                    </div>
                </div>
            </div>
        );
    }
};