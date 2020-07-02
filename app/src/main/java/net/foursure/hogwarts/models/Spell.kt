package net.foursure.hogwarts.models

class Spell(){
    // Global variables
    var _id:String?=null
    var spell:String?=null
    var type:String?=null
    var effect:String?=null

    constructor(_id:String, spell:String, type:String, effect:String):this(){
        this._id = _id
        this.spell = spell
        this.type = type
        this.effect = effect
    }
}