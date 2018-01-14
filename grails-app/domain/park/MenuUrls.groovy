package park

class MenuUrls {
    String url
    Menu menu;

    static belongsTo = [menu : Menu];

    static mapping = {
        version false
    }

    static constraints = {

    }


}
