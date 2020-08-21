import React from "react";
import {Form, Tab, Row, Col, Nav, Card} from 'react-bootstrap';
import {uploadFile} from "../services/ApiService";
import './Upload.css';
import Alert from "react-s-alert";

export default class Upload extends React.Component {

  state = {
    vulners: []
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
              Alert.error("No exploits found in the uploaded report");
            }
          })
          .catch(err => {
            Alert.error("File parsing error: " + err.message);
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
                            <Card className="cve-cards" key={exploit.toString()}>
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

    return (<div className={"file-input-div"}>
          <Form onChange={this.onFileChangeHandler} className="file-form">
            <div className="mb-3">
              <Form.File id="formcheck-api-custom" custom>
                <Form.File.Input/>
                <Form.File.Label data-browse="Select file">
                  Please select a report file to upload
                </Form.File.Label>
              </Form.File>
            </div>
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