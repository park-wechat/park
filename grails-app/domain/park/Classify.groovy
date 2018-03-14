package park

class Classify {
    String classifyName;
    String classifyUrl;
    String classifyPath
    String fileName;
    Long pictureId
    static constraints = {
        pictureId nullable:true
        fileName nullable: true
        classifyUrl nullable: true
        classifyPath nullable: true
    }
}
