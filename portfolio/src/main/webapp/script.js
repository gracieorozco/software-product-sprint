// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random fact to the page.
 */
function addRandomFact() {
  const facts = [
    'I have been to over 30 states in the United States.',
    'I am the oldest of two children.', 'I can speak Spanish.'
  ];

  // Pick a random fact.
  const fact = facts[Math.floor(Math.random() * facts.length)];
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fact;
}

/**
 * Uses fetch() to obtain data from DataServlet.java
 */
function obtainFetchData() {
  document.getElementById('fetch-div').innerText = '';
  fetch('/data')
      .then(response => response.json())
      .then(text_data => {
          console.log(text_data)
        for (var i = 0; i < text_data.length; i++) {
            // Debug message below was requested by the tutorial
            console.log("Message of \'" + text_data[i].content + "\' = " + text_data[i].score_message);
            p = document.createElement('p');
            p.innerText = text_data[i].content;
            document.getElementById('fetch-div').appendChild(p);
        }
      })
      .catch(() => {
        document.getElementById('fetch-div').innerText = 'Error!';
      })
}

function obtainComment() {
  fetch('/data').then(response => response.json()).then(comment => {
    p = document.createElement('p');
    p.innerText = 'Comment: ' + comment;
    comment - div.appendChild(p);
  })
}
