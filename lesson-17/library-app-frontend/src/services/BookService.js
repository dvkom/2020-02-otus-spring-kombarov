export function getAllBooks() {
  return fetch('/api/books/preview')
      .then(checkStatus)
      .catch(console.log);
}

export function getBook(id) {
  return fetch('/api/book/' + id)
      .then(checkStatus)
      .catch(console.log);
}

export function updateBook(book) {
  return fetch('/api/book', {
    method: 'put',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(book)
  })
      .then(checkStatus);
}

export function addBook(book) {
  return fetch('/api/book', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(book)
  })
      .then(checkStatus);
}

export function deleteBook(id) {
  return fetch('/api/book/' + id, {
    method: 'delete'
  })
      .then(checkStatus);
}

function checkStatus(response) {
  if (!response.ok) {
    throw Error(response.statusText);
  }

  return response.json();
}