package net.foursure.hogwarts.models

class Member() {
    var _id:String?=null
    var name:String?=null

    constructor(_id:String, name:String):this(){
        this._id = _id
        this.name = name
    }
}