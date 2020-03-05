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

package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    public ArrayList<String> comment_list = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJsonUsingGson(comment_list);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  public String convertToJsonUsingGson(ArrayList<String> message_arraylist) {
      Gson gson = new Gson();
      String json = gson.toJson(message_arraylist);
      return json;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = request.getParameter("text-input");
    comment_list.add(text);

    // Respond with the result.
    response.setContentType("text/html;");
    response.getWriter().println("New Comment: " + text + "\n");
    for (int i = 1; i < comment_list.size(); i++) {
        response.getWriter().println("Comment: " + comment_list.get(i) + '\n');
    }
  }
}
