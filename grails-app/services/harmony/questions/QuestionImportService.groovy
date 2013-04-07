package harmony.questions

import org.springframework.web.multipart.MultipartFile
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.hssf.util.CellReference
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory

import com.harmony.graph.Question

class QuestionImportService {

    def grailsApplication
    def hyperService

    public String uploadFile(MultipartFile file){
        String excelFilePath = "";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String filePath = grailsApplication.config.adminUploads + "/questionsList/"
        try{
        if (file.getSize() > 0) {
            filePath = filePath
            inputStream = file.getInputStream();
            excelFilePath = filePath  + file.getOriginalFilename();
            File excelFile = new File(excelFilePath);
            excelFile.getParentFile().mkdirs();
            outputStream = new FileOutputStream(excelFilePath);

            int readBytes = 0;
            byte[] buffer = new byte[10000];
            while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
                outputStream.write(buffer, 0, readBytes);
              }
            }
        }catch (Exception ex){
            log.error("Unable to upload Questions", ex)
        }finally{
            if (outputStream)
                outputStream.close();
            if (inputStream)
                inputStream.close();
        }
        return excelFilePath;
    }

    def uploadQuestions(MultipartFile file) {

        String uploadedFile = uploadFile(file)

        Workbook wb = WorkbookFactory.create(new FileInputStream(new File(uploadedFile)))
        Sheet sheet = wb.getSheetAt(0);
        int count = 0;
        List<Question> questions = new ArrayList<Question>()
        Row topRow
        for (Row row : sheet) {
            //Skip the first row
            if (count == 0){
                topRow = row
                ++count
                continue
            }
            Question question = new Question();
            questions.add(question)
            for (Cell cell : row) {
                String columnName = topRow.getCell(cell.getColumnIndex()).getStringCellValue();

                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        setQuestionProperty(question, columnName, cell.getStringCellValue())
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        setQuestionProperty(question, columnName, cell.getNumericCellValue().toString())
                        if (DateUtil.isCellDateFormatted(cell)) {
                            //System.out.println(cell.getDateCellValue());
                        } else {
                            //System.out.println(cell.getNumericCellValue());
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        setQuestionProperty(question, columnName, cell.getBooleanCellValue().toString())
                        //System.out.println(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        //System.out.println(cell.getCellFormula());
                        break;
                    default:
                        break;
                        //System.out.println();
                }
            }
        }
        long companyId = 1
        //Ok all questions are read..just commit them to Graph
        hyperService.addQuestions(questions, companyId)

    }

    private void setQuestionProperty(Question question, String propName, String propValue){ \
        if (!question.tags){
            question.tags = new ArrayList<String>()
        }

        if(propName.equalsIgnoreCase("text")){
            question.text = propValue
        }else if(propName.equalsIgnoreCase("option1")){
            question.option1 = propValue
        }else if(propName.equalsIgnoreCase("option2")){
            question.option2 = propValue
        }else if(propName.equalsIgnoreCase("option3")){
            question.option3 = propValue
        }else if(propName.equalsIgnoreCase("option4")){
            question.option4 = propValue
        }else if(propName.equalsIgnoreCase("option5")){
            question.option5 = propValue
        }else if(propName.equalsIgnoreCase("questionType")){
            question.questionType = propValue
        }else if(propName.equalsIgnoreCase("tags")){
            question.tags = propValue.split(",").toList()
        }else if(propName.equalsIgnoreCase("choice1")){
            question.choice1 = Boolean.parseBoolean(propValue)
        }else if(propName.equalsIgnoreCase("choice2")){
            question.choice2 = Boolean.parseBoolean(propValue)
        }else if(propName.equalsIgnoreCase("choice3")){
            question.choice3 = Boolean.parseBoolean(propValue)
        }else if(propName.equalsIgnoreCase("choice4")){
            question.choice4 = Boolean.parseBoolean(propValue)
        }else if(propName.equalsIgnoreCase("choice5")){
            question.choice5 = Boolean.parseBoolean(propValue)
        }else if(propName.equalsIgnoreCase("questionType")){
            question.questionType = propValue
        }else if(propName.equalsIgnoreCase("tags")){
            question.tags = propValue.split(",").toList()
        }else if(propName.contains("marks")){
            question.marks = (int)Double.parseDouble(propValue)
        }else if(propName.contains("difficulty")){
            question.difficultyLevel = (int)Double.parseDouble(propValue)
        }
    }
}
