package harmony

class HomeController {


    def index(){
        render view: "index", model: [questionTypes: QuestionTypeEnum.values()]
    }

}
