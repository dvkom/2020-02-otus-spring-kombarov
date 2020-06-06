export function getComments(id) {
  return fetch('/api/comments/' + id)
      .then(checkStatus)
      .catch(console.log);
}

export function addComment(commentDto) {
  return fetch('/api/comment', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(commentDto)
  })
      .then(checkStatus)
      .catch(console.log);
}

function checkStatus(response) {
  if (!response.ok) {
    throw Error(response.statusText);
  }

  return response.json();
}