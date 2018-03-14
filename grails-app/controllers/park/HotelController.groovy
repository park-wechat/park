package park

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class HotelController {
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort= 'id'
        params.order='desc'
        respond Hotel.list(), model:[hotelInstanceCount: Hotel.count()]
    }

    def create(){
        respond new Hotel();
    }

    def edit(Hotel hotel){
        [hotel:hotel]
    }

    @Transactional
    def save(Hotel hotel){
        if (hotel == null) {
            notFound()
            return
        }

        if (hotel.hasErrors()) {
            respond hotel.errors, view: 'create'
            return
        }
        hotel.createDate = new Date();
        hotel.save flush: true

        if(session.getAttribute("hotel") != null){
            session.removeAttribute("hotel")
        }
        session.setAttribute("hotel",hotel)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'newsNotice.label', default: ''), hotel.hotelName])
                redirect action: "index", method: "GET"
            }
            '*' { respond hotel, [status: CREATED] }
        }
    }

    @Transactional
    def delete(Hotel hotel){
        if (hotel == null) {
            notFound()
            return
        }

        List<Room> rooms = Room.findAllByHotel_id(hotel.id);
        for(Room room : rooms){
            def uploader = grailsApplication.config.uploadr.defaultUploadPath
            def path = new File("${uploader}/rooms/"+ room.roomUrl);
            if(path.exists()){
                if(!path.deleteDir()){
                    System.out.println("can not delete file, path=["+path+"]")
                }
            }
        }
        Menu.executeUpdate("delete Room r where r.hotel_id= :hotel_id"
                ,[hotel_id:hotel.id])

        hotel.delete flush: true
        if(session.getAttribute("hotel") != null){
            session.removeAttribute("hotel")
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message',
                        args: [message(code: 'NewsNotice.label', default: ''), hotel.hotelName])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Transactional
    def update(Hotel hotel){
        if (hotel == null) {
            notFound()
            return
        }

        if (hotel.hasErrors()) {
            respond hotel.errors, view: 'edit'
            return
        }
        hotel.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message',
                        args: [message(code: 'NewsNotice.label', default: ''), hotel.hotelName])
                redirect action: "index", method: "GET"
            }
            '*' { respond hotel, [status: OK] }
        }
    }

    def listRoom(Hotel hotel){
        if(hotel == null){
            hotel = (Hotel)session.getAttribute("hotel")
        }

        if(session.getAttribute("hotel") == null){
            session.setAttribute("hotel",hotel)
        }

        params.max = 10
        params.sort= 'id'
        params.order='desc'
        List<Room> rooms = Room.findAllByHotel_id(hotel.id);
        if(null == rooms){
            notFound();
            return;
        }
        respond rooms, model:[roomInstanceCount: rooms.size()]
    }

    def createRoom(Hotel hotel){
        if(hotel == null){
            hotel = (Hotel)session.getAttribute("hotel")
        }
        Room room= new Room()
        respond room, model:[hotel:hotel];
    }

    @Transactional
    def saveRoom(Room room){
        Hotel hotel = (Hotel)session.getAttribute("hotel")
        if (room == null) {
            notFound()
            return
        }

        if (room.hasErrors()) {
            respond room.errors, view: 'createRoom'
            return
        }

        List<Room> tmp = Room.findAllByRoomName(room.roomName);
        if(tmp !=0 && tmp.size() > 0){
            respond "${room.roomName} existed", view: 'createRoom'
            return
        }

        room.hotel_id= hotel.id;
        room.save flush: true

        def uploader = grailsApplication.config.uploadr.defaultUploadPath
        def path = new File("${uploader}/rooms/"+ room.roomUrl)
        boolean mainPicture = false;
        for(File file : path.listFiles()){
            RoomUrls roomUrls = new RoomUrls();
            roomUrls.fileName = file.name
            roomUrls.roomUrl = room.roomUrl
            roomUrls.roomid = room.id;
            Long pictureId = UUID.randomUUID().leastSignificantBits;
            roomUrls.pirtureId = pictureId;
            def pictureUrl = createLink(controller: 'image',action: 'thumbnail',params: pictureId)
            roomUrls.url = pictureUrl + "?pictureId="+pictureId
            if(!mainPicture){
                room.roomPicture =  roomUrls.url;
                mainPicture = true;
            }
            roomUrls.save(flush: true)
        }
        room.save flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'newsNotice.label', default: ''), room.roomName])
                redirect action: "listRoom", method: "GET"
            }
            '*' { respond room, [status: CREATED] }
        }
    }

    def editRoom(Room room){
        respond room, model:[room:room];
    }

    @Transactional
    def updateRoom(Room room){
        if (room == null) {
            notFound()
            return
        }

        if (room.hasErrors()) {
            respond room.errors, view: 'editRoom'
            return
        }

        List<Room> tmp = Room.findAllByRoomName(room.roomName);
        if(tmp !=null && tmp.size() == 0){
            respond "not ${room.roomName} existed", view: 'listRoom'
            return
        }
        Room saveRoom = tmp.get(0);
        saveRoom.roomName = room.roomName;
        saveRoom.price = room.price;

        saveRoom.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message',
                        args: [message(code: 'NewsNotice.label', default: ''), room.roomName])
                redirect action: "listRoom", method: "GET"
            }
            '*' { respond room, [status: OK] }
        }
    }

    @Transactional
    def deleteRoom(Room room){
        if (room == null) {
            notFound()
            return
        }

        def uploader = grailsApplication.config.uploadr.defaultUploadPath
        def path = new File("${uploader}/rooms/"+ room.roomUrl);
        if(path.exists()){
            if(!path.deleteDir()){
                System.out.println("can not delete file, path=["+path+"]")
            }
        }
        Menu.executeUpdate("delete Room r where r.roomName = :roomName"
                ,[roomName:room.roomName])
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message',
                        args: [message(code: 'NewsNotice.label', default: ''), room.roomName])
                redirect action: "listRoom", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message',
                        args: [message(code: 'newsNotice.label', default: 'error'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
