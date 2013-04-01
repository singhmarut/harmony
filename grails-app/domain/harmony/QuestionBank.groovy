package harmony

import com.harmony.graph.Question

class QuestionBank {

    String name
    static constraints = {
        name(unique: true)
    }
}
