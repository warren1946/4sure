package net.foursure.hogwarts.interfaces

import net.foursure.hogwarts.models.Character
import net.foursure.hogwarts.models.House
import net.foursure.hogwarts.models.Spell

interface HogwartsInterface {
    fun getHouseList():ArrayList<House>
    fun getHouse(_id:String):House
    fun getCharacterList():ArrayList<Character>
    fun getCharacter(_id:String):Character
    fun getSpellList():ArrayList<Spell>
}