class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{

			constraints {

				// apply constraints here
			}
		}

        "/rest/url/question/create"(controller: "question", action: "save"){
           // controller = "question"
           // action = [POST: "listQuestions", GET: "listQuestions"]
            constraints {
                // apply constraints here
            }
        }

        "/rest/url/question/update"(controller: "question", action: "update"){
            // controller = "question"
            // action = [POST: "listQuestions", GET: "listQuestions"]
            constraints {
                // apply constraints here
            }
        }

        "/rest/url/question/delete"(controller: "question", action: "delete"){
            // controller = "question"
            // action = [POST: "listQuestions", GET: "listQuestions"]
            constraints {
                // apply constraints here
            }
        }

        "/rest/url/question/list"(controller: "question", action: "listQuestions"){
            // controller = "question"
            // action = [POST: "listQuestions", GET: "listQuestions"]
            constraints {
                // apply constraints here
            }
        }

        "/$controller/$action?"{
            constraints {
                // apply constraints here
            }
        }


		"/"(controller: "home", action: 'index')

        "/login/$action?"(controller: "login")
        "/logout/$action?"(controller: "logout")

        "500"(view:'/error')
	}
}
