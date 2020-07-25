import React from 'react';
import {getAllBooks} from '../services/BookService';
import {Button, Col, Container, Row} from "reactstrap";
import DataTable from "./DataTable";
import ModalForm from "./ModalForm";
import AuthService from "../services/AuthService";

export default class Root extends React.Component {

  // TODO
  //  1. Переход на redux
  //  2. Обработка ошибок react через BoundaryComponent и componentDidCatch
  //  3. Защита от NPE при получении данных и рендеринге
  //  4. linter
  //  5. fetch или axios

  state = {
    books: []
  };

  addBookToState = (book) => {
    this.setState(prevState => ({
      books: [...prevState.books, book]
    }))
  };

  updateState = (book) => {
    if (book) {
      const bookIndex = this.state.books.findIndex(data => data.id === book.id);
      const newArray = [...this.state.books.slice(0, bookIndex), book, ...this.state.books.slice(bookIndex + 1)];
      this.setState({books: newArray})
    }
  };

  deleteBookFromState = (id) => {
    const updatedItems = this.state.books.filter(book => book.id !== id);
    this.setState({books: updatedItems})
  };

  handleLogout = () => {
    AuthService.logout();
    this.props.history.push(`/login`);
  };

  componentDidMount() {
    getAllBooks()
        .then(data => this.setState({books: data}))
  }

  render() {
    return (
        <Container className="RootPage">
          <Row>
            <Col>
              <h1 style={{textAlign: "center"}}>Библиотека</h1>
            </Col>
          </Row>
          <Row>
            <Col>
              <DataTable books={this.state.books} updateState={this.updateState}
                         deleteBookFromState={this.deleteBookFromState}/>
            </Col>
          </Row>
          <Row>
            <Col>
              <ModalForm buttonLabel="Добавить" addBookToState={this.addBookToState}/>
                <Button onClick={this.handleLogout}>Завершить сеанс</Button>
            </Col>
          </Row>
        </Container>
    )
  }
};
