export function uploadFile(formData) {
  return fetch('api/upload', {
    method: 'post',
    body: formData
  })
      .then(checkStatus);
}

function checkStatus(response) {
  if (!response.ok) {
    throw Error(response.statusText);
  }

  return response.json();
}