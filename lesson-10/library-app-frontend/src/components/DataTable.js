import React, {Component} from 'react'
import {Table, Button} from 'reactstrap';
import ModalForm from './ModalForm';
import {deleteBook} from "../services/BookService";

class DataTable extends Component {

  deleteItem = id => {
    let confirmDelete = window.confirm('Вы действительно хотите удалить книгу?');
    if (confirmDelete) {
      deleteBook(id)
          .then(id => {
            this.props.deleteBookFromState(id)
          })
          .catch(console.log)
    }
  };

  render() {

    const books = this.props.books.map(book => {
      return (
          <tr key={book.id}>
            <th scope="row">{book.id}</th>
            <td>{book.title}</td>
            <td>{book.pageCount}</td>
            <td>{book.authorName}</td>
            <td>
              <div style={{width: "260px"}}>
                <ModalForm buttonLabel="Просмотр" book={book} updateState={this.props.updateState}/>
                <ModalForm buttonLabel="Редактировать" book={book} updateState={this.props.updateState}/>
                {' '}
                <Button color="danger" onClick={() => this.deleteItem(book.id)}>Удалить</Button>
              </div>
            </td>
          </tr>
      )
    });

    return (
        <Table hover>
          <thead>
          <tr>
            <th>ID</th>
            <th>Наименование</th>
            <th>Количество страниц</th>
            <th>Автор</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
          {books}
          </tbody>
        </Table>
    )
  }
}

export default DataTable