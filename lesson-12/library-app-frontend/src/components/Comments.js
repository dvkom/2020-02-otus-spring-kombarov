import React from 'react';
import {addComment, getComments} from "../services/CommentService";
import {Button, Card, Input, CardBody, Label} from "reactstrap";

class Comments extends React.Component {

  state = {
    comments: [],
    newComment: ''
  };

  componentDidMount() {
    if (this.props.id) {
      getComments(this.props.id)
          .then(data => this.setState({comments: data}))
    }
  }

  onChange = event => {
    this.setState({newComment: event.target.value})
  };

  handleAddComment() {
    addComment({id: 0, text:this.state.newComment, bookId: this.props.id})
        .then(comment => {
          this.addCommentToState(comment);
        })
        .catch(console.log)
  };

  addCommentToState = comment => {
    this.setState(prevState => ({
      comments: [...prevState.comments, comment],
      newComment: ''
    }))
  };

  render() {
    const comments = this.state.comments ?
        this.state.comments.map(comment => {
          return (
              <Card key={comment.id} outline color="info">
                <CardBody style={{fontStyle: 'italic'}}>
                  {comment.text === null ? '' : comment.text}
                </CardBody>
              </Card>
          )
        }) : null;

    const newComment = this.state.newComment;
    return (
        <div>
          <Label>Комментарии</Label>
          {comments}
          <Label for={newComment} style={{marginTop: '20px'}}>Ваш комментарий</Label>
          <Input type="text" name="newComment" id="newComment" onChange={this.onChange}
                 value={newComment === null ? '' : newComment}/>
          <Button onClick={this.handleAddComment.bind(this)} style={{marginTop: '10px'}} color="success">
            Добавить комментарий
          </Button>
        </div>
    )
  }
}

export default Comments