import React from "react";
import {Form, Tab, Row, Col, Nav, Card} from 'react-bootstrap';
import {Alert} from "reactstrap";
import {uploadFile} from "../services/FileService";

export default class UploadForm extends React.Component {

  state = {
    vulners: [],
    visible: false,
    errorMessage: ''
  };

  onShowAlert = () => {
    this.setState({visible: true}, () => {
      window.setTimeout(() => {
        this.setState({visible: false})
      }, 5000)
    });
  };

  onFileChangeHandler = (e) => {
    e.preventDefault();

    if (e.target.files[0] != null) {
      const formData = new FormData();
      formData.append('file', e.target.files[0]);

      uploadFile(formData)
          .then(data => {
            this.setState({vulners: data});
            if (data != null && data.length === 0) {
              this.setState({errorMessage: "No exploits found in the uploaded report"});
              this.onShowAlert();
            }
          })
          .catch(err => {
            this.setState({errorMessage: "File parsing error: " + err.message});
            this.onShowAlert();
          });
    }
  };

  render() {
    const vulnerTabs = this.state.vulners ?
        this.state.vulners.map(vulner => {
          return (
              <Nav.Item key={vulner.cve}>
                <Nav.Link eventKey={vulner.cve}>{vulner.cve === null ? '' : vulner.cve}</Nav.Link>
              </Nav.Item>
          )
        }) : null;

    const vulnerTabsContent = this.state.vulners ?
        this.state.vulners.map(vulner => {
          return (
              <Tab.Pane key={vulner.cve} eventKey={vulner.cve}>
                <p className="font-weight-bold">{vulner.cve === null ? '' : vulner.cve}</p>
                {
                  vulner.exploits === null ? '' :
                      vulner.exploits.map(exploit => {
                        return (
                            <Card key={exploit.toString()} style={{width: '18rem'}}>
                              <Card.Body>
                                <Card.Text>
                                  {exploit.description === null ? '' : exploit.description}
                                </Card.Text>
                                <Card.Link href={exploit.link === null ? '#' : exploit.link}>
                                  Download from source
                                </Card.Link>
                              </Card.Body>
                            </Card>
                        )
                      })
                }
              </Tab.Pane>
          )
        }) : null;

    return (<div style={{display: 'flex', justifyContent: 'center'}}>
          <Form onChange={this.onFileChangeHandler} style={{width: 800, marginTop: 40}}>
            <div className="mb-3">
              <Form.File id="formcheck-api-custom" custom>
                <Form.File.Input isValid/>
                <Form.File.Label data-browse="Select file">
                  Please select a report file to upload
                </Form.File.Label>
              </Form.File>
            </div>
            <Alert color="danger" isOpen={this.state.visible}>
              {this.state.errorMessage}
            </Alert>
            <Tab.Container id="left-tabs-example" defaultActiveKey="first">
              <Row>
                <Col sm={3}>
                  <Nav variant="pills" className="flex-column">
                    {vulnerTabs}
                  </Nav>
                </Col>
                <Col sm={3}>
                  <Tab.Content>
                    {vulnerTabsContent}
                  </Tab.Content>
                </Col>
              </Row>
            </Tab.Container>
          </Form>
        </div>
    )
  }
};