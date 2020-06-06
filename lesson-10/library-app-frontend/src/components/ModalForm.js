import React, {Component} from 'react'
import {Button, Modal, ModalHeader, ModalBody} from 'reactstrap'
import AddEditViewForm from './AddEditVewForm'

class ModalForm extends Component {

  state = {
    isOpen: false,
    modalType: 'view'
  };

  toggle = () => {
    this.setState(prevState => ({
      isOpen: !prevState.isOpen
    }))
  };

  render() {
    const closeBtn = <button className="close" onClick={this.toggle}>&times;</button>;

    const label = this.props.buttonLabel;

    let button = '';
    let title = '';


    switch (label) {
      case 'Редактировать':
        button = <Button
            color="primary"
            onClick={this.toggle}
            style={{float: "left", marginRight: "10px"}}>{label}
        </Button>;
        title = 'Редактирование книги';
        break;
      case 'Добавить':
        button = <Button
            color="success"
            onClick={this.toggle}
            style={{float: "left", marginRight: "10px"}}>{label}
        </Button>;
        title = 'Добавление новой книги';
        break;
      case 'Просмотр':
        button = <Button
            color="info"
            onClick={this.toggle}
            style={{float: "left", marginRight: "10px"}}>{label}
        </Button>;
        title = 'Просмотр книги';
        break;
      default:
        console.error('Не поддерживаемый тип операции');
    }

    return (
        <div>
          {button}
          <Modal isOpen={this.state.isOpen} toggle={this.toggle} className={this.props.className}>
            <ModalHeader toggle={this.toggle} close={closeBtn}>{title}</ModalHeader>
            <ModalBody>
              <AddEditViewForm
                  addBookToState={this.props.addBookToState}
                  updateState={this.props.updateState}
                  toggle={this.toggle}
                  book={this.props.book}
                  readOnly={this.props.buttonLabel === 'Просмотр'}/>
            </ModalBody>
          </Modal>
        </div>
    )
  }
}

export default ModalForm