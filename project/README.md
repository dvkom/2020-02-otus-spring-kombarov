# Exploit Finder
This is an application to search for existing exploits and match them with CVE numbers from security reports.
- Spring: Data, Security, Web, Retry, Actuator;
- DB: PostgreSQL;
- parsing: apache.tika;
- deploy: docker
<p align="center">
  <img src="https://raw.githubusercontent.com/dvkom/2020-02-otus-spring-kombarov/tree/master/img/architecture.png">
</p>

Quickstart:
- build Exploit-Source and Report-Analyzer modules with Maven;
- run docker-compose up;
- open http://localhost:3000/ in your browser;
- sign up and then sign in;
- load a report.
<p align="center">
  <img src="https://raw.githubusercontent.com/dvkom/2020-02-otus-spring-kombarov/tree/master/img/exploit-finder-ui.png">
</p>
