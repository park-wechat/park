package park

class Room {
    String roomName
    double price;
    String roomUrl;
    Long hotel_id;
    String roomPicture;  //酒店主图片
    String roomLevel
    String roomInformation
    String mark;
    static mapping = {
//        roomName unique: true
        version false
    }

    static constraints = {
        roomPicture nullable: true
        hotel_id nullable: true
    }
}
