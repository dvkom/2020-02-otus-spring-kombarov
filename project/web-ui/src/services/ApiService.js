export const ACCESS_TOKEN = 'accessToken';

const request = (options, initHeaders) => {
  const headers = new Headers(initHeaders);

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
  }

  const defaults = {headers: headers};
  options = Object.assign({}, defaults, options);

  return fetch(options.url, options)
      .then(checkStatus);
};

function checkStatus(response) {
  if (!response.ok) {
    throw Error(response.statusText);
  }

  return response.json();
}

export function isAuthenticated() {
  return localStorage.getItem(ACCESS_TOKEN);
}

export function uploadFile(formData) {
  return request({
    url: 'api/upload',
    method: 'post',
    body: formData
  }, {});
}

export function login(loginRequest) {
  return request({
    url: "/auth/login",
    method: 'POST',
    body: JSON.stringify(loginRequest)
  }, {
    'Content-Type': 'application/json'
  });
}

export function signup(signupRequest) {
  return request({
    url: "/auth/signup",
    method: 'POST',
    body: JSON.stringify(signupRequest)
  }, {
    'Content-Type': 'application/json'
  });
}