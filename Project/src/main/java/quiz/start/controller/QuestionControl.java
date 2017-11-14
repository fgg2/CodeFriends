package quiz.start.controller;

import org.springframework.web.bind.annotation.*;
import quiz.start.model.User;
import quiz.start.repository.QuestionCollection;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Hashtable;


/*
 *  Aðalsteinn Ingi Pálsson
 *  aip7@hi.is
 *
 */


/*
Controller that manages the question pages
*/


@RestController
public class QuestionControl {

    private QuestionCollection data;

    Hashtable convertedQuestion;

    public QuestionControl(){
        //this.data = new QuestionCollection("London");

    }


    /*
    * @param String
    *
    *
    * Shows a page with questions, depending on your previous answer
    * @returns String
    */


    @RequestMapping(value = "API/question", method = RequestMethod.GET)
    public Hashtable getQuestion1(String answer){

        convertedQuestion = convertQuestion(data);

        return convertedQuestion;
    }
    @RequestMapping(value = "API/userLocation", method = RequestMethod.POST)
    public void updateScore(@RequestBody String location) throws UnsupportedEncodingException {
        String result = java.net.URLDecoder.decode(location, "UTF-8");
        String finalCut = result.substring(0, result.length() - 1);
          System.out.println(finalCut);

          this.data = new QuestionCollection(finalCut);


    }

    public Hashtable convertQuestion(final QuestionCollection data) {
        Hashtable<String, String> newQuestion = new Hashtable<String, String>() {{
            put("country1", data.getData().getCountry());
            put("country2", data.getData().getCountry2());
            put("city1", data.getData().getDest1());
            put("city2", data.getData().getDest2());
            put("currentCountry", data.getData().getCurrentCountry());
            put("currentCity", data.getData().getCurrentLoc());
            put("score",data.getData().wasPreviousQuestionCorrect());
            put("correct",data.getData().getCorrectAnswer());
        }};

        return newQuestion;
    }

    @RequestMapping(value = "API/answer/question", method = RequestMethod.POST )
    public void saveReview(@RequestBody String answer) {

        String answers = answer.split(":")[1].split("}")[0];
        String finalAnswer = answers.substring(1,answers.length()-1);
        data.getData().compareDist(finalAnswer);
    }

}
