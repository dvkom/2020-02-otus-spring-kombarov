import React from 'react';
import {Button, Form, FormGroup, Label, Input, Alert} from 'reactstrap';
import {addBook, getBook, updateBook} from "../services/BookService";
import Comments from "./Comments";

class AddEditViewForm extends React.Component {

  state = {
    book: {
      id: 0,
      title: '',
      pageCount: '',
      authorName: '',
      authorCountry: '',
      genreName: ''
    },
    visible: false,
    errorMessage: ''
  };

  onShowAlert = () => {
    this.setState({visible: true}, () => {
      window.setTimeout(() => {
        this.setState({visible: false})
      }, 2000)
    });
  };

  onChange = event => {
    let book = {...this.state.book};
    book[event.target.name] = event.target.value;
    this.setState({book});
  };

  submitFormAdd = event => {
    event.preventDefault();
    addBook(this.state.book)
        .then(book => {
          this.props.addBookToState(book);
          this.props.toggle()
        })
        .catch(err => {
          this.setState({errorMessage: "Ошибка добавления книги: " + err.message});
          this.onShowAlert();
        })
  };

  submitFormEdit = event => {
    event.preventDefault();
    updateBook(this.state.book)
        .then(book => {
          this.props.updateState(book);
          this.props.toggle()
        })
        .catch(err => {
          this.setState({errorMessage: "Ошибка обновления книги: " + err.message});
          this.onShowAlert();
        })
  };

  componentDidMount() {
    if (this.props.book) {
      getBook(this.props.book.id)
          .then(data => this.setState({book: data}));
    }
  }

  render() {
    const [id, title, pageCount, authorName, authorCountry, genreName] = Object.values(this.state.book);
    const readOnly = this.props.readOnly;

    let saveButton;
    let comments;
    if (!readOnly) {
      saveButton = <Button>Сохранить</Button>;
    } else {
      comments = <Comments id={this.props.book.id}/>
    }

    return (
        <Form onSubmit={this.state.book.id ? this.submitFormEdit : this.submitFormAdd}>
          <FormGroup>
            <Label for="id">Id</Label>
            <Input readOnly type="number" name="id" id="id"
                   value={id === null ? '' : id}/>
          </FormGroup>
          <FormGroup>
            <Label for="title">Название</Label>
            <Input readOnly={readOnly} type="text" name="title" id="title" onChange={this.onChange}
                   value={title === null ? '' : title}/>
          </FormGroup>
          <FormGroup>
            <Label for="pageCount">Количество страниц</Label>
            <Input readOnly={readOnly} type="number" name="pageCount" id="pageCount" onChange={this.onChange}
                   value={pageCount === null ? '' : pageCount}/>
          </FormGroup>
          <FormGroup>
            <Label for="authorName">Автор</Label>
            <Input readOnly={readOnly} type="text" name="authorName" id="authorName" onChange={this.onChange}
                   value={authorName === null ? '' : authorName}/>
          </FormGroup>
          <FormGroup>
            <Label for="authorCountry">Страна</Label>
            <Input readOnly={readOnly} type="text" name="authorCountry" id="authorCountry" onChange={this.onChange}
                   value={authorCountry === null ? '' : authorCountry}/>
          </FormGroup>
          <FormGroup>
            <Label for="genreName">Жанр</Label>
            <Input readOnly={readOnly} type="text" name="genreName" id="genreName" onChange={this.onChange}
                   value={genreName === null ? '' : genreName}/>
          </FormGroup>
          <Alert color="danger" isOpen={this.state.visible}>
            {this.state.errorMessage}
          </Alert>
          {saveButton}
          <FormGroup>
            {comments}
          </FormGroup>
        </Form>
    );
  }
}

export default AddEditViewForm